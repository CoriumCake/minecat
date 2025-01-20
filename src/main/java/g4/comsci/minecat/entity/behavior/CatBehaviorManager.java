package g4.comsci.minecat.entity.behavior;

import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;

public class CatBehaviorManager {
    private final TameableEntity cat;
    private final CatBehaviorConfig config;
    private final CatMiningBehavior miningBehavior;
    private final CatWoodcuttingBehavior woodcuttingBehavior;
    private boolean isReturningToOwner = false;

    public CatBehaviorManager(TameableEntity cat) {
        this.cat = cat;
        this.config = new CatBehaviorConfig();
        this.miningBehavior = new CatMiningBehavior(cat, config);
        this.woodcuttingBehavior = new CatWoodcuttingBehavior(cat, config);
    }

    public void toggleBehavior(String behavior) {
        // If enabling woodcutting, disable all other behaviors first
        if (behavior.equals("woodcutting") && !config.isBehaviorEnabled("woodcutting")) {
            config.disableAllBehaviors();
        }

        // If enabling any other behavior and woodcutting is active, don't allow it
        if (!behavior.equals("woodcutting") && config.isBehaviorEnabled("woodcutting")) {
            return;
        }

        // Toggle the requested behavior
        config.toggleBehavior(behavior);

        if (!config.isBehaviorEnabled(behavior)) {
            resetWorkingState();
        }
        if (config.isBehaviorEnabled(behavior)) {
            cat.getNavigation().stop();
        }
    }

    public boolean isBehaviorEnabled(String behavior) {
        return config.isBehaviorEnabled(behavior);
    }

    public boolean hasActiveWork() {
        return config.hasActiveWork() || isReturningToOwner;
    }

    private void resetWorkingState() {
        isReturningToOwner = false;
    }

    public void tick(PlayerEntity owner) {
        // Check if cat is not tamed, not owned by player, or sitting
        if (!cat.isTamed() || !cat.isOwner(owner) || cat.isSitting()) {
            return;
        }

        // Check if cat is too far from owner
        double distanceToOwner = cat.squaredDistanceTo(owner);
        if (distanceToOwner > config.getMaxDistanceFromOwner() * config.getMaxDistanceFromOwner()) {
            resetWorkingState();
            returnToOwner(owner);
            return;
        }

        // Only handle woodcutting if it's enabled
        if (config.isBehaviorEnabled("woodcutting")) {
            woodcuttingBehavior.handleWoodcutting(owner);
            return; // Skip all other behaviors
        }

        // Only handle mining if woodcutting is not active
        if (config.isBehaviorEnabled("mining")) {
            miningBehavior.handleMining(owner);
        }

        // Handle return-to-owner state for non-woodcutting behaviors
        if (isReturningToOwner) {
            if (distanceToOwner <= config.getReturnToOwnerDistance() * config.getReturnToOwnerDistance()) {
                isReturningToOwner = false;
            } else {
                returnToOwner(owner);
            }
        }
    }

    private void returnToOwner(PlayerEntity owner) {
        double distance = cat.squaredDistanceTo(owner);
        if (distance > config.getReturnToOwnerDistance() * config.getReturnToOwnerDistance()) {
            cat.getNavigation().startMovingTo(owner, 1.0);
        }
    }
}