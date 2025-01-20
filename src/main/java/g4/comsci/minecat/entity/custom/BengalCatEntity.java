package g4.comsci.minecat.entity.custom;

import g4.comsci.minecat.entity.CustomCatEntity;
import g4.comsci.minecat.entity.ModEntities;
import g4.comsci.minecat.entity.behavior.CatBehaviorManager;
import g4.comsci.minecat.entity.behavior.CatWorkGoal;
import g4.comsci.minecat.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BengalCatEntity extends CatEntity implements CustomCatEntity {
    private final CatBehaviorManager behaviorManager;

    public BengalCatEntity(EntityType<? extends CatEntity> entityType, World world) {
        super(entityType, world);
        this.behaviorManager = new CatBehaviorManager(this);
    }

    @Override
    public void tick() {
        super.tick();
        // If sitting, stop all movement and look straight ahead
        if (this.isInSittingPose()) {
            this.setVelocity(0, 0, 0);
            this.prevHorizontalSpeed = 0;
            this.horizontalSpeed = 0;
            this.setYaw(this.prevYaw);
            this.setPitch(this.prevPitch);
            this.setHeadYaw(this.prevHeadYaw);
            this.bodyYaw = this.headYaw;
            this.prevBodyYaw = this.bodyYaw;
        } else if (this.isTamed() && this.getOwner() instanceof PlayerEntity owner && !this.isInSittingPose()) {
            behaviorManager.tick(owner);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();

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
        } else if (this.isTamed() && this.isOwner(player)) {
            if (itemStack.isEmpty()) {
                if (!this.getWorld().isClient) {
                    this.setInSittingPose(!this.isInSittingPose());
                    this.navigation.stop();
                    this.setTarget(null);
                    if (this.isInSittingPose()) {
                        this.setVelocity(0, 0, 0);
                    }
                }
                return ActionResult.SUCCESS;
            }
        }

        return super.interactMob(player, hand);
    }

    @Override
    protected void initGoals() {
        // Basic survival and movement goals
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(3, new CatWorkGoal(this)); // Custom mining/chopping goal
        this.goalSelector.add(4, new EscapeDangerGoal(this, 1.5));

        // Vanilla cat behaviors
        this.goalSelector.add(5, new CatSitOnBlockGoal(this, 0.8));
        this.goalSelector.add(6, new TemptGoal(this, 0.6D, Ingredient.ofItems(ModItems.PURRIUM, ModItems.CAT_TEASER, ModItems.CATFOOD), true));
        this.goalSelector.add(8, new FollowOwnerGoal(this, 1.0, 10.0F, 5.0F, false));
        this.goalSelector.add(9, new PounceAtTargetGoal(this, 0.3F));
        this.goalSelector.add(10, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(11, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));

        // Target goals (what the cat attacks)
        this.targetSelector.add(1, new FleeEntityGoal<>(this, OcelotEntity.class, 6.0F, 1.0, 1.2));
        this.targetSelector.add(2, new AnimalMateGoal(this, 0.8));
    }

    @Override
    public void setInSittingPose(boolean sitting) {
        super.setInSittingPose(sitting);
        if (sitting) {
            this.navigation.stop();
            this.setVelocity(0, 0, 0);
        }
    }

    @Override
    public CatBehaviorManager getBehaviorManager() {
        return behaviorManager;
    }

    public static DefaultAttributeContainer.Builder createBengalCatAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_ARMOR, 0.0f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }

    @Override
    public @Nullable CatEntity createChild(ServerWorld world, PassiveEntity mate) {
        return ModEntities.CAT6.create(world);
    }

    @Override
    public EntityView method_48926() {
        return this.getWorld();
    }
}