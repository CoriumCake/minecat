package g4.comsci.minecat.datagen;

import g4.comsci.minecat.block.ModBlocks;
import g4.comsci.minecat.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PURRIUM_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_PURRIUM_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHER_PURRIUM_ORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.CATFOOD, Models.GENERATED);
        itemModelGenerator.register(ModItems.CAT_LOCATOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.CAT_TEASER, Models.GENERATED);

        itemModelGenerator.register(ModItems.PURRIUM, Models.GENERATED);

        itemModelGenerator.register(ModItems.PURRIUM_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PURRIUM_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PURRIUM_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PURRIUM_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PURRIUM_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.SPHYNX_SPAWN_EGG,
                new Model(Optional.of(new Identifier("item/template_spawn_egg")),Optional.empty()));
        itemModelGenerator.register(ModItems.KORATCAT_SPAWN_EGG,
                new Model(Optional.of(new Identifier("item/template_spawn_egg")),Optional.empty()));
        itemModelGenerator.register(ModItems.PERSIAN_SPAWN_EGG,
                new Model(Optional.of(new Identifier("item/template_spawn_egg")),Optional.empty()));
        itemModelGenerator.register(ModItems.SNOWSHOE_SPAWN_EGG,
                new Model(Optional.of(new Identifier("item/template_spawn_egg")),Optional.empty()));
        itemModelGenerator.register(ModItems.SCOTTISH_SPAWN_EGG,
                new Model(Optional.of(new Identifier("item/template_spawn_egg")),Optional.empty()));
        itemModelGenerator.register(ModItems.BENGAL_SPAWN_EGG,
                new Model(Optional.of(new Identifier("item/template_spawn_egg")),Optional.empty()));
    }
}
