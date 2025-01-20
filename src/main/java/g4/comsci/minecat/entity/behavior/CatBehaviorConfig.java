package g4.comsci.minecat.entity.behavior;

import java.util.HashMap;
import java.util.Map;

public class CatBehaviorConfig {
    private final Map<String, Boolean> enabledBehaviors = new HashMap<>();
    private boolean debugMode = true;
    private static final double MAX_DISTANCE_FROM_OWNER = 30.0;
    private static final double RETURN_TO_OWNER_DISTANCE = 3.0;
    private static final int DETECTION_RADIUS = 20;

    public CatBehaviorConfig() {
        // Initialize default behaviors
        enabledBehaviors.put("mining", false);
        enabledBehaviors.put("woodcutting", false);
    }

    public void toggleBehavior(String behavior) {
        enabledBehaviors.put(behavior, !enabledBehaviors.getOrDefault(behavior, false));
    }

    public void disableAllBehaviors() {
        enabledBehaviors.replaceAll((k, v) -> false);
    }

    public boolean isBehaviorEnabled(String behavior) {
        return enabledBehaviors.getOrDefault(behavior, false);
    }

    public boolean hasActiveWork() {
        return enabledBehaviors.values().stream().anyMatch(enabled -> enabled);
    }

    public boolean isDebugModeEnabled() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public double getMaxDistanceFromOwner() {
        return MAX_DISTANCE_FROM_OWNER;
    }

    public double getReturnToOwnerDistance() {
        return RETURN_TO_OWNER_DISTANCE;
    }

    public int getDetectionRadius() {
        return DETECTION_RADIUS;
    }
}