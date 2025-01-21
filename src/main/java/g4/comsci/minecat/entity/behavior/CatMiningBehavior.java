package g4.comsci.minecat.entity.behavior;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import java.util.ArrayList;
import java.util.List;

public class CatMiningBehavior {
    private final TameableEntity cat;
    private final CatBehaviorConfig config;
    private BlockPos targetBlock = null;
    private int attempts = 0;
    private long lastAttemptTime = 0;
    private static final long ATTEMPT_TIMEOUT = 60; // 3 seconds in ticks

    public CatMiningBehavior(TameableEntity cat, CatBehaviorConfig config) {
        this.cat = cat;
        this.config = config;
    }

    public void handleMining(PlayerEntity owner) {
        if (!config.isBehaviorEnabled("mining")) {
            resetForNewTarget();
            return;
        }

        World world = cat.getWorld();
        long currentTime = world.getTime();

        if (targetBlock == null) {
            attempts = 0;
        }

        if (targetBlock != null) {
            if (!isOreBlock(world.getBlockState(targetBlock).getBlock())) {
                resetForNewTarget();
                return;
            }

            // If we're close enough to the target, mine it
            if (cat.getBlockPos().isWithinDistance(targetBlock, 2.0)) {
                mineOreBlock(owner, world);
                attempts = 0; // Reset attempts after successful mining
                return;
            }

            // Handle pathfinding timeout
            if (currentTime - lastAttemptTime >= ATTEMPT_TIMEOUT) {
                attempts++;
                lastAttemptTime = currentTime;
                if (attempts >= 3) {
                    // After 3 attempts, try finding ore from player position
                    targetBlock = null;
                }
            }
        }

        // Find new target
        if (targetBlock == null) {
            if (attempts >= 3) {
                // Search expanding from player after 3 attempts
                targetBlock = findOresExpandingFromPlayer(owner, world);
            } else {
                // First 3 attempts, search nearest to cat
                targetBlock = findNearestOre(cat.getBlockPos(), owner, world);
            }

            if (targetBlock != null) {
                cat.getNavigation().startMovingTo(
                        targetBlock.getX() + 0.5,
                        targetBlock.getY(),
                        targetBlock.getZ() + 0.5,
                        1.0
                );
            }
        }
    }

    private void resetForNewTarget() {
        targetBlock = null;
        lastAttemptTime = 0;
    }

    private BlockPos findOresExpandingFromPlayer(PlayerEntity owner, World world) {
        int maxRadius = (int)config.getMaxDistanceFromOwner();
        BlockPos ownerPos = owner.getBlockPos();

        // Search in expanding circles from player position
        for (int radius = 1; radius <= maxRadius; radius++) {
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    // Only check blocks at the current radius
                    if (Math.abs(x) == radius || Math.abs(z) == radius) {
                        for (int y = -3; y <= 3; y++) {
                            BlockPos checkPos = ownerPos.add(x, y, z);
                            if (isOreBlock(world.getBlockState(checkPos).getBlock())) {
                                // Check if position is within cat's allowed range
                                double distanceToOwner = Math.sqrt(checkPos.getSquaredDistance(ownerPos));
                                if (distanceToOwner <= config.getMaxDistanceFromOwner()) {
                                    return checkPos;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private BlockPos findNearestOre(BlockPos startPos, PlayerEntity owner, World world) {
        int searchRadius = 10;
        BlockPos nearestOre = null;
        double nearestDistance = Double.MAX_VALUE;

        for (int y = -3; y <= 3; y++) {
            for (int x = -searchRadius; x <= searchRadius; x++) {
                for (int z = -searchRadius; z <= searchRadius; z++) {
                    BlockPos checkPos = startPos.add(x, y, z);
                    if (isOreBlock(world.getBlockState(checkPos).getBlock())) {
                        double distanceToOwner = Math.sqrt(checkPos.getSquaredDistance(owner.getBlockPos()));
                        double distanceToCat = Math.sqrt(checkPos.getSquaredDistance(startPos));

                        if (distanceToOwner <= config.getMaxDistanceFromOwner() && distanceToCat < nearestDistance) {
                            nearestDistance = distanceToCat;
                            nearestOre = checkPos;
                        }
                    }
                }
            }
        }
        return nearestOre;
    }

    private void mineOreBlock(PlayerEntity owner, World world) {
        BlockState oreState = world.getBlockState(targetBlock);
        world.breakBlock(targetBlock, false); // First set to air
        Block.dropStacks(oreState, world, targetBlock, null, owner, owner.getMainHandStack());
        resetForNewTarget();
    }

    private boolean isOreBlock(Block block) {
        return block.getTranslationKey().contains("ore");
    }
}