package g4.comsci.minecat.world.gen;

import g4.comsci.minecat.entity.ModEntities;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntitySpawns {
    public static void addSpawns() {
        // Bengal Cat (CAT6) - Jungle biomes
        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(BiomeKeys.JUNGLE),
                SpawnGroup.CREATURE,
                ModEntities.CAT6,
                200, // weight
                1,  // min per spawn
                3   // max per spawn
        );

        // Korat Cat (CAT2) - Plains and Villages
        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(BiomeKeys.PLAINS),
                SpawnGroup.CREATURE,
                ModEntities.CAT2,
                200,
                1,
                2
        );

        // Persian Cat (CAT3) - Desert biomes
        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(BiomeKeys.DESERT),
                SpawnGroup.CREATURE,
                ModEntities.CAT3,
                200,
                1,
                2
        );

        // Scottish Cat (CAT5) - Plains and Cold biomes
        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SNOWY_PLAINS),
                SpawnGroup.CREATURE,
                ModEntities.CAT5,
                200,
                1,
                3
        );

        // Snowshoe Cat (CAT4) - Snowy biomes
        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS),
                SpawnGroup.CREATURE,
                ModEntities.CAT4,
                200,
                1,
                2
        );

        // Sphynx Cat (CAT1) - Desert and Badlands
        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(BiomeKeys.DESERT, BiomeKeys.BADLANDS),
                SpawnGroup.CREATURE,
                ModEntities.CAT1,
                200,
                1,
                2
        );

        // Register spawn restrictions for all cats
        SpawnRestriction.register(ModEntities.CAT1, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.CAT2, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.CAT3, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.CAT4, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.CAT5, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.CAT6, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
    }
}