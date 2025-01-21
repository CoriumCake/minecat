package g4.comsci.minecat.world;

import g4.comsci.minecat.MineCat;
import g4.comsci.minecat.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> PURRIUM_ORE_KEY = registerKey("purrium_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NETHER_PURRIUM_ORE_KEY = registerKey("nether_purrium_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DEEPSLATE_PURRIUM_ORE_KEY = registerKey("deepslate_purrium_ore");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplaceables = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);

        List<OreFeatureConfig.Target> overworldPurriumOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.PURRIUM_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.DEEPSLATE_PURRIUM_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> netherPurriumOres =
                List.of(OreFeatureConfig.createTarget(netherReplaceables, ModBlocks.NETHER_PURRIUM_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> deepslatePurriumOres =
                List.of(OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.DEEPSLATE_PURRIUM_ORE.getDefaultState()));

        // Register the ore configurations
        register(context, PURRIUM_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldPurriumOres, 8));  // Smaller vein size for balance
        register(context, NETHER_PURRIUM_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherPurriumOres, 6));  // Slightly rarer in nether
        register(context, DEEPSLATE_PURRIUM_ORE_KEY, Feature.ORE, new OreFeatureConfig(deepslatePurriumOres, 4));  // Rarest in deepslate
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(MineCat.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}