package g4.comsci.minecat.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import java.util.*;

public class CatBehaviorManager {
    private final TameableEntity cat;
    private final Map<String, Boolean> enabledBehaviors = new HashMap<>();
    private BlockPos targetBlock = null;
    private boolean isCurrentlyWorking = false;
    private static final int DETECTION_RADIUS = 20;
    private boolean debugMode = true;

    public CatBehaviorManager(TameableEntity cat) {
        this.cat = cat;
        enabledBehaviors.put("mining", false);
        enabledBehaviors.put("woodcutting", false);
    }

    public void toggleBehavior(String behavior) {
        enabledBehaviors.put(behavior, !enabledBehaviors.getOrDefault(behavior, false));
        if (!enabledBehaviors.get(behavior)) {
            resetWorkingState();
        }
    }

    public boolean isBehaviorEnabled(String behavior) {
        return enabledBehaviors.getOrDefault(behavior, false);
    }

    private void resetWorkingState() {
        isCurrentlyWorking = false;
        targetBlock = null;
    }

    public void tick(PlayerEntity owner) {
        if (!cat.isTamed() || !cat.isOwner(owner) || cat.isSitting()) {
            return;
        }

        if (enabledBehaviors.get("mining")) {
            handleMining(owner);
        }

        if (enabledBehaviors.get("woodcutting")) {
            handleWoodcutting(owner);
        }
    }

    private void handleMining(PlayerEntity owner) {
        BlockPos catPos = cat.getBlockPos();
        Box detectionBox = new Box(catPos).expand(DETECTION_RADIUS);

        Optional<BlockPos> closestOre = BlockPos.stream(detectionBox)
                .map(BlockPos::toImmutable)
                .filter(pos -> isOreBlock(cat.getWorld().getBlockState(pos).getBlock()))
                .min((pos1, pos2) -> Double.compare(pos1.getSquaredDistance(catPos), pos2.getSquaredDistance(catPos)));

        closestOre.ifPresent(orePos -> {
            if (cat.getNavigation().startMovingTo(orePos.getX() + 0.5, orePos.getY() + 0.5, orePos.getZ() + 0.5, 1.0)) {
                if (cat.getBlockPos().isWithinDistance(orePos, 1.5)) {
                    BlockState oreState = cat.getWorld().getBlockState(orePos);
                    cat.getWorld().breakBlock(orePos, true, owner);
                }
            }
        });
    }

    private void handleWoodcutting(PlayerEntity owner) {
        if (!isCurrentlyWorking) {
            targetBlock = findNearestLog(cat.getBlockPos(), cat.getWorld());
            if (targetBlock != null) {
                cat.getNavigation().startMovingTo(
                        targetBlock.getX() + 0.5,
                        targetBlock.getY(),
                        targetBlock.getZ() + 0.5,
                        1.0
                );
            }
        }

        if (targetBlock != null && cat.getBlockPos().isWithinDistance(targetBlock, 2.0)) {
            if (cat.getWorld().getBlockState(targetBlock).isIn(BlockTags.LOGS)) {
                isCurrentlyWorking = true;
                chopTreeFromPosition(targetBlock, owner, cat.getWorld());
                resetWorkingState();
            } else {
                resetWorkingState();
            }
        }
    }

    private BlockPos findNearestLog(BlockPos startPos, World world) {
        int searchRadius = 10;
        BlockPos nearestLog = null;
        double nearestDistance = Double.MAX_VALUE;

        for (int y = -3; y <= 3; y++) {
            for (int x = -searchRadius; x <= searchRadius; x++) {
                for (int z = -searchRadius; z <= searchRadius; z++) {
                    BlockPos checkPos = startPos.add(x, y, z);
                    if (world.getBlockState(checkPos).isIn(BlockTags.LOGS)) {
                        double distance = checkPos.getSquaredDistance(startPos);
                        if (distance < nearestDistance) {
                            nearestDistance = distance;
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

        final int MAX_BLOCKS = 200;
        final int MAX_DISTANCE = 10;

        world.playSound(null, startLog, SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);

        while (!toCheck.isEmpty() && visitedPositions.size() < MAX_BLOCKS) {
            BlockPos currentPos = toCheck.poll();

            if (!visitedPositions.add(currentPos) || startLog.getManhattanDistance(currentPos) > MAX_DISTANCE) {
                continue;
            }

            BlockState blockState = world.getBlockState(currentPos);
            if (blockState.isIn(BlockTags.LOGS)) {
                world.breakBlock(currentPos, true, player);

                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = 0; dy <= 1; dy++) {
                        for (int dz = -1; dz <= 1; dz++) {
                            if (dx == 0 && dy == 0 && dz == 0) continue;
                            BlockPos nextPos = currentPos.add(dx, dy, dz);
                            if (!visitedPositions.contains(nextPos)) {
                                toCheck.offer(nextPos);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isOreBlock(Block block) {
        return block.getTranslationKey().contains("ore");
    }
}
