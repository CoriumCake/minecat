package g4.comsci.minecat.world.gen;

public class ModWorldGeneration {
    public static void generateModWorldGeneration() {
        ModOreGeneration.generateOres();

        ModEntitySpawns.addSpawns();
    }
}
