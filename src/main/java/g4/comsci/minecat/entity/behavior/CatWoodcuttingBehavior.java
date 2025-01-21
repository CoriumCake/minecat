package g4.comsci.minecat.entity.behavior;

import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import java.util.*;

public class CatWoodcuttingBehavior {
    private final TameableEntity cat;
    private final CatBehaviorConfig config;
    private BlockPos targetBlock = null;
    private boolean taskCompleted = false;
    private int attempts = 0;
    private long lastAttemptTime = 0;
    private static final long ATTEMPT_TIMEOUT = 60; // Timeout in ticks (3 seconds)

    public CatWoodcuttingBehavior(TameableEntity cat, CatBehaviorConfig config) {
        this.cat = cat;
        this.config = config;
    }

    public void handleWoodcutting(PlayerEntity owner) {
        if (!config.isBehaviorEnabled("woodcutting") || !cat.isTamed()) {
            targetBlock = null;
            attempts = 0;
            return;
        }

        World world = cat.getWorld();
        long currentTime = world.getTime();

        if (targetBlock == null) {
            attempts = 0;
        }

        // If no current target or previous target is completed, find a new log
        if (targetBlock == null || !world.getBlockState(targetBlock).isIn(BlockTags.LOGS)) {
            if (attempts >= 3) {
                // After 3 attempts, search from nearest to furthest from player
                targetBlock = findLogsExpandingFromPlayer(cat.getBlockPos(), world, owner);
            } else {
                // First 3 attempts, search nearest to cat
                targetBlock = findNearestLog(cat.getBlockPos(), world, owner);
            }

            if (targetBlock != null) {
                cat.getNavigation().startMovingTo(
                        targetBlock.getX() + 0.5,
                        targetBlock.getY(),
                        targetBlock.getZ() + 0.5,
                        1.0
                );
            }
            return;
        }

        // Check if cat is close enough to the target block
        if (cat.getBlockPos().isWithinDistance(targetBlock, 2.0)) {
            if (world.getBlockState(targetBlock).isIn(BlockTags.LOGS)) {
                chopTreeFromPosition(targetBlock, owner, world);
                attempts = 0; // Reset attempts after successful chop
                targetBlock = null;
            } else {
                // If block is no longer a log, increment attempts and find new target
                if (currentTime - lastAttemptTime >= ATTEMPT_TIMEOUT) {
                    attempts++;
                    lastAttemptTime = currentTime;
                    targetBlock = null;
                }
            }
        } else {
            // If cat can't reach the target after timeout, increment attempts
            if (currentTime - lastAttemptTime >= ATTEMPT_TIMEOUT) {
                attempts++;
                lastAttemptTime = currentTime;
                targetBlock = null;
            }
        }
    }

    private BlockPos findLogsExpandingFromPlayer(BlockPos startPos, World world, PlayerEntity owner) {
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
                            if (world.getBlockState(checkPos).isIn(BlockTags.LOGS)) {
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

    private BlockPos findNearestLog(BlockPos startPos, World world, PlayerEntity owner) {
        int searchRadius = 10;
        BlockPos nearestLog = null;
        double nearestDistance = Double.MAX_VALUE;

        for (int y = -3; y <= 3; y++) {
            for (int x = -searchRadius; x <= searchRadius; x++) {
                for (int z = -searchRadius; z <= searchRadius; z++) {
                    BlockPos checkPos = startPos.add(x, y, z);
                    if (world.getBlockState(checkPos).isIn(BlockTags.LOGS)) {
                        double distanceToOwner = Math.sqrt(checkPos.getSquaredDistance(owner.getBlockPos()));
                        double distanceToCat = Math.sqrt(checkPos.getSquaredDistance(startPos));

                        if (distanceToOwner <= config.getMaxDistanceFromOwner() && distanceToCat < nearestDistance) {
                            nearestDistance = distanceToCat;
                            nearestLog = checkPos;
                        }
                    }
                }
            }
        }
        return nearestLog;
    }

    private void chopTreeFromPosition(BlockPos startLog, PlayerEntity player, World world) {
        Set<BlockPos> visitedPositions = new HashSet<>();
        Queue<BlockPos> toCheck = new LinkedList<>();
        toCheck.offer(startLog);

        final int MAX_BLOCKS = 500;
        final int MAX_VERTICAL_DISTANCE = 30;
        final int MAX_HORIZONTAL_DISTANCE = 15;

        world.playSound(null, startLog, SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);

        while (!toCheck.isEmpty() && visitedPositions.size() < MAX_BLOCKS) {
            BlockPos currentPos = toCheck.poll();

            if (!visitedPositions.add(currentPos) ||
                    Math.abs(startLog.getY() - currentPos.getY()) > MAX_VERTICAL_DISTANCE ||
                    Math.max(
                            Math.abs(startLog.getX() - currentPos.getX()),
                            Math.abs(startLog.getZ() - currentPos.getZ())
                    ) > MAX_HORIZONTAL_DISTANCE) {
                continue;
            }

            BlockState blockState = world.getBlockState(currentPos);

            if (blockState.isIn(BlockTags.LOGS) || blockState.isIn(BlockTags.LEAVES)) {
                world.breakBlock(currentPos, true, player);

                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dz = -1; dz <= 1; dz++) {
                            if (dx == 0 && dy == 0 && dz == 0) continue;

                            BlockPos nextPos = currentPos.add(dx, dy, dz);
                            BlockState nextState = world.getBlockState(nextPos);

                            if ((nextState.isIn(BlockTags.LOGS) || nextState.isIn(BlockTags.LEAVES))
                                    && !visitedPositions.contains(nextPos)) {
                                toCheck.offer(nextPos);
                            }
                        }
                    }
                }
            }
        }

        taskCompleted = true;
    }

    public boolean hasCompletedTask() {
        boolean completed = taskCompleted;
        taskCompleted = false;
        return completed;
    }
}