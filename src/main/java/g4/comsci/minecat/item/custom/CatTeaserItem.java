package g4.comsci.minecat.item.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
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
            // Define a bounding box to find nearby cats
            Box box = new Box(
                    user.getX() - 10, user.getY() - 10, user.getZ() - 10,
                    user.getX() + 10, user.getY() + 10, user.getZ() + 10
            );

            // Get all nearby cats
            List<CatEntity> cats = world.getEntitiesByClass(CatEntity.class, box, cat -> true);

            for (CatEntity cat : cats) {
                // Calculate direction toward the player
                double dx = user.getX() - cat.getX();
                double dz = user.getZ() - cat.getZ();
                double dy = (user.getY() - cat.getY()) + 0.5; // Slightly raise to make the jump playful

                // Add randomness for playful movement
                double randomness = 0.2 * (RANDOM.nextDouble() - 0.5);

                // Normalize direction and add randomness
                double distance = Math.sqrt(dx * dx + dz * dz);
                dx = (dx / distance) + randomness;
                dz = (dz / distance) + randomness;

                // Set cat velocity for jumping
                cat.addVelocity(dx * 0.4, dy * 0.4, dz * 0.4);
                cat.velocityDirty = true; // Mark velocity as updated

                // Make the cat face the player
                cat.lookAtEntity(user, 30.0F, 30.0F);

                // Make the cat meow occasionally
                if (RANDOM.nextInt(5) == 0) {
                    cat.playAmbientSound();
                }
            }
        }

        return TypedActionResult.success(user.getStackInHand(hand), world.isClient);
    }
}
