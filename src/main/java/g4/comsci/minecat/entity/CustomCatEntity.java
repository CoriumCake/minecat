package g4.comsci.minecat.entity;

public interface CustomCatEntity {
    CatBehaviorManager getBehaviorManager();

    default void toggleMining() {
        getBehaviorManager().toggleBehavior("mining");
    }

    default boolean isMining() {
        return getBehaviorManager().isBehaviorEnabled("mining");
    }

    default void toggleWoodcutting() {
        getBehaviorManager().toggleBehavior("woodcutting");
    }

    default boolean isWoodcutting() {
        return getBehaviorManager().isBehaviorEnabled("woodcutting");
    } // Add this
}
