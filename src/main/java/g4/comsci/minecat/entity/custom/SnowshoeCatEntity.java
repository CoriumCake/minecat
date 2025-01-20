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
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SnowshoeCatEntity extends CatEntity implements CustomCatEntity {
    private final CatBehaviorManager behaviorManager;
    private static final Ingredient TAMING_ITEMS = Ingredient.ofItems(ModItems.CATFOOD, ModItems.CAT_TEASER);

    public SnowshoeCatEntity(EntityType<? extends CatEntity> entityType, World world) {
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

        // Cat-specific behaviors
        this.goalSelector.add(5, new TemptGoal(this, 1.25D, Ingredient.ofItems(ModItems.PURRIUM, ModItems.CAT_TEASER, ModItems.CATFOOD), false));
        this.goalSelector.add(6, new FollowOwnerGoal(this, 1.0, 10.0F, 5.0F, false));
        this.goalSelector.add(7, new AnimalMateGoal(this, 1.15));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
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

    public static DefaultAttributeContainer.Builder createSnowshoeCatAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_ARMOR, 0.0f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(ModItems.CATFOOD);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CAT_AMBIENT;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CAT_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CAT_DEATH;
    }

    @Override
    public EntityView method_48926() {
        return this.getWorld();
    }
}