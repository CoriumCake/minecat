package g4.comsci.minecat.entity.custom;

import g4.comsci.minecat.entity.ModEntities;
import g4.comsci.minecat.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
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

public class PersianCatEntity extends TameableEntity {

    private static final Ingredient TAMING_ITEMS = Ingredient.ofItems(ModItems.CATFOOD, ModItems.CAT_TEASER);

    public PersianCatEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this,1.5));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.ofItems(ModItems.PURRIUM, ModItems.CAT_TEASER, ModItems.CATFOOD), false));
        this.goalSelector.add(4, new FollowOwnerGoal(this, 1.0, 10.0F, 5.0F, false));
        this.goalSelector.add(5, new AnimalMateGoal(this, 1.15));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 10f));
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(ModItems.CATFOOD);
    }

    public static DefaultAttributeContainer.Builder createPersianCatAttributes() {
        return TameableEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 500.0) // Reasonable high health
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_ARMOR, 1000.0f) // Moderate armor
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 800.0); // Strong attack
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.CAT3.create(world);
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
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (TAMING_ITEMS.test(itemStack)) {
            if (!this.getWorld().isClient) {
                if (!this.isTamed() && this.random.nextInt(3) == 0) {
                    this.setOwner(player);
                    this.getWorld().sendEntityStatus(this, (byte) 7); // Taming success particle
                } else {
                    this.getWorld().sendEntityStatus(this, (byte) 6); // Taming failure particle
                }
            }
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    @Override
    public EntityView method_48926() {
        return this.getWorld();
    }
}
