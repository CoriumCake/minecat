package g4.comsci.minecat.item.custom;

import g4.comsci.minecat.entity.custom.CatEntity;
import g4.comsci.minecat.sound.ModSounds;
import net.minecraft.client.sound.Sound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class CatTeaserItem extends Item {
    private static final Random RANDOM = new Random();

    public CatTeaserItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            // Play random sound
            SoundEvent[] sounds = {ModSounds.CAT_TEASER_RCLICK, SoundEvents.ENTITY_CAT_PURR, SoundEvents.ENTITY_CAT_AMBIENT};
            SoundEvent randomSound = sounds[RANDOM.nextInt(sounds.length)];
            user.getWorld().playSound(null, user.getBlockPos(), randomSound, SoundCategory.BLOCKS, 1f, 1f);

            // Define a bounding box to find nearby cats
            Box box = new Box(
                    user.getX() - 10, user.getY() - 10, user.getZ() - 10,
                    user.getX() + 10, user.getY() + 10, user.getZ() + 10
            );

            // Get all nearby cats
            List<g4.comsci.minecat.entity.custom.CatEntity> cats = world.getEntitiesByClass(g4.comsci.minecat.entity.custom.CatEntity.class, box, cat -> true);

            // Sort cats by distance to the player
            cats.sort((cat1, cat2) -> {
                double distance1 = cat1.squaredDistanceTo(user);
                double distance2 = cat2.squaredDistanceTo(user);
                return Double.compare(distance1, distance2);
            });

            for (CatEntity cat : cats) {
                // Custom logic for sorted cats
                double dx = user.getX() - cat.getX();
                double dz = user.getZ() - cat.getZ();
                double dy = (user.getY() - cat.getY()) + 0.5;

                double distance = Math.sqrt(dx * dx + dz * dz);
                if (distance > 0) {
                    double randomness = 0.1 * (RANDOM.nextDouble() - 0.5);
                    dx = (dx / distance) + randomness;
                    dz = (dz / distance) + randomness;

                    cat.addVelocity(dx * 0.4, dy * 0.4, dz * 0.4);
                    cat.velocityDirty = true;
                    cat.lookAtEntity(user, 30.0F, 30.0F);

                    // Play random ambient sound for the cat occasionally
                    if (RANDOM.nextInt(5) == 0) {
                        cat.playAmbientSound();
                    }

                    // Add custom particles at the cat's position
                    for (int i = 0; i < 5; i++) { // Spawn 5 particles
                        double offsetX = RANDOM.nextGaussian() * 0.1;
                        double offsetY = RANDOM.nextGaussian() * 0.1;
                        double offsetZ = RANDOM.nextGaussian() * 0.1;
                        world.addParticle(ParticleTypes.HEART, cat.getX(), cat.getY() + 1, cat.getZ(), offsetX, offsetY, offsetZ);
                    }
                }
            }
        }

        return TypedActionResult.success(user.getStackInHand(hand), world.isClient);
    }
}
