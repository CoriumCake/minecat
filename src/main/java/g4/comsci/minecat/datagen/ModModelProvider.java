package g4.comsci.minecat.datagen;

import g4.comsci.minecat.block.ModBlocks;
import g4.comsci.minecat.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PURRIUM_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.END_PURRIUM_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_PURRIUM_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHER_PURRIUM_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.LITTER_BOX);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.CATFOOD, Models.GENERATED);
        itemModelGenerator.register(ModItems.CATFUEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.CAT_TEASER, Models.GENERATED);
    }
}
