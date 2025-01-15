package g4.comsci.minecat.entity.custom;

import g4.comsci.minecat.entity.ModEntities;
import g4.comsci.minecat.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HeavenlyCatEntity extends TameableEntity {

    public HeavenlyCatEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(1, new AnimalMateGoal(this, 1.15));
        this.goalSelector.add(2, new FarmingGoal(this, 10)); // Farming goal
        this.goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.ofItems(ModItems.PURRIUM, ModItems.CAT_TEASER, ModItems.CATFOOD), false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.15D));
        this.goalSelector.add(4, new FollowOwnerGoal(this, 1.15D, 10f, 3f, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(ModItems.CATFOOD);
    }

    public static DefaultAttributeContainer.Builder createCatAttributes() {
        return TameableEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_ARMOR, 0.0f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
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

    /* Taming logic */
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getStackInHand(hand);
        Item item = itemstack.getItem();

        if (item.isFood() && item == ModItems.CATFOOD) {
            if (!this.isTamed() && !this.getWorld().isClient) {
                this.setOwner(player);
                this.navigation.stop();
                this.setSitting(true);
                System.out.println("Heavenly Cat tamed by: " + player.getName().getString());
                return ActionResult.SUCCESS;
            }
        } else if (this.isTamed() && hand == Hand.MAIN_HAND) {
            if (!this.getWorld().isClient) {
                this.setSitting(!this.isSitting());
                this.navigation.stop();
                System.out.println("Heavenly Cat is now " + (this.isSitting() ? "sitting" : "standing"));
                return ActionResult.SUCCESS;
            }
        }

        return super.interactMob(player, hand);
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        System.out.println("A Heavenly Cat baby is created!");
        return ModEntities.CAT5.create(world);
    }

    @Override
    public EntityView method_48926() {
        return this.getWorld();
    }

    /* Custom Farming Goal */
    public static class FarmingGoal extends Goal {
        private final HeavenlyCatEntity cat;
        private BlockPos targetCrop;
        private final int range;
        private int recalculateCooldown = 0;

        public FarmingGoal(HeavenlyCatEntity cat, int range) {
            this.cat = cat;
            this.range = range;
        }

        @Override
        public boolean canStart() {
            if (!cat.isTamed() || cat.getOwner() == null || cat.isSitting()) return false;

            World world = cat.getWorld();
            BlockPos catPos = cat.getBlockPos();

            for (BlockPos pos : BlockPos.iterate(catPos.add(-range, -1, -range), catPos.add(range, 1, range))) {
                BlockState state = world.getBlockState(pos);
                if (isMatureCrop(state) && world.isAir(pos.up())) { // Ensure no obstruction
                    this.targetCrop = pos;
                    return true;
                }
            }
            return false;
        }

        private boolean isMatureCrop(BlockState state) {
            return state.getBlock() instanceof CropBlock crop && crop.isMature(state);
        }

        @Override
        public void start() {
            if (targetCrop != null) {
                cat.getNavigation().startMovingTo(targetCrop.getX() + 0.5, targetCrop.getY(), targetCrop.getZ() + 0.5, 1.0);
                System.out.println("Heavenly Cat is moving to crop at: " + targetCrop);
            }
        }

        @Override
        public void tick() {
            if (targetCrop != null && cat.squaredDistanceTo(targetCrop.getX() + 0.5, targetCrop.getY(), targetCrop.getZ() + 0.5) < 2.0) {
                World world = cat.getWorld();
                BlockState state = world.getBlockState(targetCrop);

                if (isMatureCrop(state)) {
                    List<ItemStack> drops = Block.getDroppedStacks(state, (ServerWorld) world, targetCrop, null);
                    world.breakBlock(targetCrop, false);

                    ItemStack seed = null;
                    for (ItemStack stack : drops) {
                        if (stack.getItem() instanceof net.minecraft.item.BlockItem blockItem && blockItem.getBlock() instanceof CropBlock) {
                            seed = stack;
                        } else {
                            cat.dropStack(stack);
                        }
                    }

                    if (seed != null) {
                        world.setBlockState(targetCrop, state.getBlock().getDefaultState());
                    }
                }

                System.out.println("Heavenly Cat harvested crop at: " + targetCrop);
                targetCrop = null;
            }
        }

        @Override
        public boolean shouldContinue() {
            return targetCrop != null && cat.getOwner() != null && !cat.isSitting();
        }

        @Override
        public void stop() {
            targetCrop = null;
        }
    }
}
