package g4.comsci.minecat.entity.custom;

import g4.comsci.minecat.entity.ModEntities;
import g4.comsci.minecat.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SphynxCatEntity extends TameableEntity {

    private static final int DETECTION_RADIUS = 20;
    private static final int MINING_COOLDOWN = 100; // ticks
    private int miningCooldown = 0;

    public SphynxCatEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (miningCooldown > 0) {
            miningCooldown--;
        } else if (this.isTamed() && this.getOwner() instanceof PlayerEntity owner && !this.isSitting()) {
            mineAndDeliverOre(owner);
        }
    }

    private void mineAndDeliverOre(PlayerEntity owner) {
        if (owner == null || !this.isTamed() || !this.isOwner(owner)) return; // Ensure the cat is tamed and the player is the owner

        BlockPos catPos = this.getBlockPos();
        Box detectionBox = new Box(catPos).expand(DETECTION_RADIUS);
        Optional<BlockPos> closestOre = BlockPos.stream(detectionBox)
                .map(BlockPos::toImmutable)
                .filter(pos -> isOreBlock(this.getWorld().getBlockState(pos).getBlock()))
                .min((pos1, pos2) -> Double.compare(pos1.getSquaredDistance(catPos), pos2.getSquaredDistance(catPos)));

        closestOre.ifPresent(orePos -> {
            if (this.getNavigation().startMovingTo(orePos.getX() + 0.5, orePos.getY() + 0.5, orePos.getZ() + 0.5, 1.0)) {
                if (this.getBlockPos().isWithinDistance(orePos, 1.5)) {
                    // Simulate mining
                    BlockState oreState = this.getWorld().getBlockState(orePos);
                    ItemStack minedOre = new ItemStack(oreState.getBlock().asItem());

                    this.getWorld().breakBlock(orePos, false);

                    // Deliver to player or drop near them if inventory is full
                    if (!owner.getInventory().insertStack(minedOre)) {
                        owner.dropItem(minedOre, false);
                    }
                    miningCooldown = MINING_COOLDOWN;
                }
            }
        });
    }

    private boolean isOreBlock(Block block) {
        // Replace with actual ore block checks
        return block.getTranslationKey().contains("ore");
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new FollowOwnerGoal(this, 1.0, 5.0F, 2.0F, false)); // Follow owner goal
        //this.goalSelector.add(3, new TemptGoal(this, 1.25D, ModItems.CATFOOD, false));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createSphynxCatAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f);
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity mate) {
        return ModEntities.CAT1.create(world);
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
