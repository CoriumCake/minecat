package g4.comsci.minecat.entity.custom;

import g4.comsci.minecat.entity.CustomCatEntity;
import g4.comsci.minecat.entity.behavior.CatBehaviorManager;
import g4.comsci.minecat.entity.behavior.CatWorkGoal;
import g4.comsci.minecat.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;

public abstract class AbstractCustomCat extends CatEntity implements CustomCatEntity {
    protected final CatBehaviorManager behaviorManager;
    protected boolean workEnabled;
    protected String workType;
    private boolean needsMovementUpdate;
    private int sitCooldown = 1;

    public AbstractCustomCat(EntityType<? extends CatEntity> entityType, World world) {
        super(entityType, world);
        this.behaviorManager = new CatBehaviorManager(this);
        this.workEnabled = false;
        this.workType = "";
        this.needsMovementUpdate = false;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        if (this.getOwnerUuid() != null) {
            nbt.putUuid("Owner", this.getOwnerUuid());
        }

        nbt.putBoolean("Sitting", this.isInSittingPose());
        nbt.putBoolean("NeedsMovementUpdate", this.needsMovementUpdate);
        nbt.putBoolean("WorkEnabled", this.workEnabled);
        nbt.putString("WorkType", this.workType);
        nbt.putBoolean("MiningEnabled", this.behaviorManager.isBehaviorEnabled("mining"));
        nbt.putBoolean("WoodcuttingEnabled", this.behaviorManager.isBehaviorEnabled("woodcutting"));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        if (nbt.containsUuid("Owner")) {
            this.setOwnerUuid(nbt.getUuid("Owner"));
            if (!this.getWorld().isClient()) {
                ServerWorld serverWorld = (ServerWorld) this.getWorld();
                PlayerEntity owner = serverWorld.getServer()
                        .getPlayerManager()
                        .getPlayer(this.getOwnerUuid());
                if (owner != null) {
                    this.setOwner(owner);
                }
            }
        }

        this.needsMovementUpdate = nbt.getBoolean("NeedsMovementUpdate");

        boolean sitting = nbt.getBoolean("Sitting");
        if (sitting != this.isInSittingPose()) {
            this.setInSittingPose(sitting);
        }

        // Set work states
        this.workEnabled = nbt.getBoolean("WorkEnabled");
        this.workType = nbt.getString("WorkType");

        if (nbt.getBoolean("MiningEnabled")) {
            this.behaviorManager.toggleBehavior("mining");
        }
        if (nbt.getBoolean("WoodcuttingEnabled")) {
            this.behaviorManager.toggleBehavior("woodcutting");
        }
    }

    @Override
    public void tick() {
        super.tick();

        // Update cooldown
        if (sitCooldown > 0) {
            sitCooldown--;
        }

        if (this.isInSittingPose()) {
            this.setVelocity(0, this.getVelocity().y, 0); // Keep Y velocity for proper physics
            this.prevHorizontalSpeed = 0;
            this.horizontalSpeed = 0;
            this.setYaw(this.prevYaw);
            this.setPitch(this.prevPitch);
            this.setHeadYaw(this.prevHeadYaw);
            this.bodyYaw = this.headYaw;
            this.prevBodyYaw = this.bodyYaw;

            if (!this.getWorld().isClient()) {
                this.navigation.stop();
            }
        } else if (this.needsMovementUpdate) {
            if (!this.getWorld().isClient()) {
                this.setMovementSpeed(0.3f);
                this.navigation.stop();
                this.needsMovementUpdate = false;
                this.velocityDirty = true;
            }
        }

        if (!this.getWorld().isClient() && this.isTamed() &&
                this.getOwner() instanceof PlayerEntity owner && !this.isInSittingPose()) {
            behaviorManager.tick(owner);
            if (this.workEnabled && !behaviorManager.isBehaviorEnabled(this.workType)) {
                behaviorManager.toggleBehavior(this.workType);
            }
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();

        // Handle taming with cat food
        if (item == ModItems.CATFOOD) {
            if (!this.isTamed() && !this.getWorld().isClient) {
                if (this.random.nextInt(3) == 0) {
                    this.setOwner(player);
                    this.navigation.stop();
                    this.setInSittingPose(true);
                    this.getWorld().sendEntityStatus(this, (byte) 7);
                    return ActionResult.SUCCESS;
                } else {
                    this.getWorld().sendEntityStatus(this, (byte) 6);
                }
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                return ActionResult.SUCCESS;
            }
        }
        // Handle sit/stand toggle
        else if (this.isTamed() && this.isOwner(player) && itemStack.isEmpty()) {
            // Only process on server side and respect cooldown
            if (!this.getWorld().isClient && sitCooldown <= 0) {
                sitCooldown = 10; // Set cooldown (10 ticks = 0.5 seconds)

                boolean newSittingState = !this.isInSittingPose();
                this.setInSittingPose(newSittingState);

                if (!newSittingState) {
                    this.needsMovementUpdate = true;
                }

                // Clear any active navigation and targeting
                this.navigation.stop();
                this.setTarget(null);

                // Sync to client
                this.getWorld().sendEntityStatus(this, (byte)(newSittingState ? 40 : 39));

                return ActionResult.SUCCESS;
            }
            // Always consume the interaction on client side
            return ActionResult.SUCCESS;
        }

        return super.interactMob(player, hand);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 40) {
            this.setInSittingPose(true);
        } else if (status == 39) {
            this.setInSittingPose(false);
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(3, new CatWorkGoal(this));
        this.goalSelector.add(4, new EscapeDangerGoal(this, 1.5));
        this.goalSelector.add(5, new CatSitOnBlockGoal(this, 0.8));
        this.goalSelector.add(6, new TemptGoal(this, 0.6D,
                Ingredient.ofItems(ModItems.PURRIUM, ModItems.CAT_TEASER, ModItems.CATFOOD), true));
        this.goalSelector.add(8, new FollowOwnerGoal(this, 1.0, 10.0F, 5.0F, false));
        this.goalSelector.add(9, new PounceAtTargetGoal(this, 0.3F));
        this.goalSelector.add(10, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(11, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));

        this.targetSelector.add(1, new FleeEntityGoal<>(this, OcelotEntity.class, 6.0F, 1.0, 1.2));
        this.targetSelector.add(2, new AnimalMateGoal(this, 0.8));
    }

    @Override
    public void setInSittingPose(boolean sitting) {
        super.setInSittingPose(sitting);
        if (sitting) {
            this.navigation.stop();
            this.setVelocity(0, this.getVelocity().y, 0);
        } else {
            this.needsMovementUpdate = true;
        }
        this.velocityDirty = true;
    }

    @Override
    public CatBehaviorManager getBehaviorManager() {
        return behaviorManager;
    }

    public static DefaultAttributeContainer.Builder createBaseAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_ARMOR, 0.0f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public EntityView method_48926() {
        return this.getWorld();
    }
}