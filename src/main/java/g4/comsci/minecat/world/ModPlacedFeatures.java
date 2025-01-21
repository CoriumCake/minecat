package g4.comsci.minecat.world;

import g4.comsci.minecat.MineCat;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {

    public static final RegistryKey<PlacedFeature> PURRIUM_ORE_PLACED_KEY = registerKey("purrium_ore_placed");
    public static final RegistryKey<PlacedFeature> DEEPSLATE_PURRIUM_ORE_PLACED_KEY = registerKey("deepslate_purrium_ore_placed");
    public static final RegistryKey<PlacedFeature> NETHER_PURRIUM_ORE_PLACED_KEY = registerKey("nether_purrium_ore_placed");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        // Regular Purrium Ore - Rare in upper layers
        register(context, PURRIUM_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PURRIUM_ORE_KEY),
                ModOrePlacement.modifiersWithCount(5, // 1 in 5 chunks will have a vein
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-16), YOffset.fixed(32))));

        // Deepslate Purrium Ore - Very rare in deep layers
        register(context, DEEPSLATE_PURRIUM_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.DEEPSLATE_PURRIUM_ORE_KEY),
                ModOrePlacement.modifiersWithRarity(6, // 1 in 6 chunks will have a vein
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-64), YOffset.fixed(-16))));

        // Nether Purrium Ore - Extremely rare
        register(context, NETHER_PURRIUM_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.NETHER_PURRIUM_ORE_KEY),
                ModOrePlacement.modifiersWithRarity(7, // 1 in 7 chunks will have a vein
                        HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(64))));
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(MineCat.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}