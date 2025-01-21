package g4.comsci.minecat.item.custom;

import g4.comsci.minecat.entity.custom.SphynxCatEntity;
import g4.comsci.minecat.sound.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CatTeaserItem extends Item {
    private static final Random RANDOM = new Random();
    private static final double HORIZONTAL_MULTIPLIER = 0.1;
    private static final double VERTICAL_MULTIPLIER = 0.05;

    public CatTeaserItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            SoundEvent[] sounds = {ModSounds.CAT_TEASER_RCLICK, SoundEvents.ENTITY_CAT_PURR, SoundEvents.ENTITY_CAT_AMBIENT};
            SoundEvent randomSound = sounds[RANDOM.nextInt(sounds.length)];
            user.getWorld().playSound(null, user.getBlockPos(), randomSound, SoundCategory.BLOCKS, 1f, 1f);

            // Define a bounding box to find nearby cats
            Box box = new Box(
                    user.getX() - 10, user.getY() - 10, user.getZ() - 10,
                    user.getX() + 10, user.getY() + 10, user.getZ() + 10
            );

            // Get all nearby custom and vanilla cats
            List<SphynxCatEntity> customCats = world.getEntitiesByClass(SphynxCatEntity.class, box, cat -> true);
            List<net.minecraft.entity.passive.CatEntity> vanillaCats = world.getEntitiesByClass(net.minecraft.entity.passive.CatEntity.class, box, cat -> true);

            // Combine both lists
            List<Object> allCats = new ArrayList<>();
            allCats.addAll(customCats);
            allCats.addAll(vanillaCats);

            for (Object cat : allCats) {
                double dx, dy, dz;

                if (cat instanceof SphynxCatEntity customCat) {
                    dx = user.getX() - customCat.getX();
                    dz = user.getZ() - customCat.getZ();
                    dy = (user.getY() - customCat.getY()) + 0.5;

                    customCat.addVelocity(
                            dx * HORIZONTAL_MULTIPLIER,
                            dy * VERTICAL_MULTIPLIER,
                            dz * HORIZONTAL_MULTIPLIER
                    );
                    customCat.velocityDirty = true;
                    customCat.lookAtEntity(user, 30.0F, 30.0F);
                } else if (cat instanceof net.minecraft.entity.passive.CatEntity vanillaCat) {
                    dx = user.getX() - vanillaCat.getX();
                    dz = user.getZ() - vanillaCat.getZ();
                    dy = (user.getY() - vanillaCat.getY()) + 0.5;

                    vanillaCat.addVelocity(
                            dx * HORIZONTAL_MULTIPLIER,
                            dy * VERTICAL_MULTIPLIER,
                            dz * HORIZONTAL_MULTIPLIER
                    );
                    vanillaCat.velocityDirty = true;
                    vanillaCat.lookAtEntity(user, 30.0F, 30.0F);
                }
            }
        } else {
            // Client-side logic: Spawn particles at the cats' positions
            spawnParticlesForCats(world, user);
        }

        return TypedActionResult.success(user.getStackInHand(hand), world.isClient);
    }

    private void spawnParticlesForCats(World world, PlayerEntity user) {
        Box box = new Box(
                user.getX() - 10, user.getY() - 10, user.getZ() - 10,
                user.getX() + 10, user.getY() + 10, user.getZ() + 10
        );

        List<SphynxCatEntity> customCats = world.getEntitiesByClass(SphynxCatEntity.class, box, cat -> true);
        List<net.minecraft.entity.passive.CatEntity> vanillaCats = world.getEntitiesByClass(net.minecraft.entity.passive.CatEntity.class, box, cat -> true);

        List<Object> allCats = new ArrayList<>();
        allCats.addAll(customCats);
        allCats.addAll(vanillaCats);

        for (Object cat : allCats) {
            if (cat instanceof SphynxCatEntity customCat) {
                spawnParticles(world, customCat.getX(), customCat.getY(), customCat.getZ());
            } else if (cat instanceof net.minecraft.entity.passive.CatEntity vanillaCat) {
                spawnParticles(world, vanillaCat.getX(), vanillaCat.getY(), vanillaCat.getZ());
            }
        }
    }

    private void spawnParticles(World world, double x, double y, double z) {
        if (!world.isClient) return;

        for (int i = 0; i < 5; i++) {
            double offsetX = RANDOM.nextGaussian() * 0.1;
            double offsetY = RANDOM.nextGaussian() * 0.1;
            double offsetZ = RANDOM.nextGaussian() * 0.1;

            world.addParticle(
                    ParticleTypes.HEART,
                    x,
                    y + 1,
                    z,
                    offsetX,
                    offsetY,
                    offsetZ
            );
        }
    }
}