package g4.comsci.minecat.datagen;

import g4.comsci.minecat.block.ModBlocks;
import g4.comsci.minecat.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

public class ModLootTableProvider extends FabricBlockLootTableProvider {

    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.LITTER_BOX);

        addDrop(ModBlocks.PURRIUM_ORE, copperLikeOreDrops(ModBlocks.PURRIUM_ORE, ModItems.CATFUEL));
        addDrop(ModBlocks.DEEPSLATE_PURRIUM_ORE, copperLikeOreDrops(ModBlocks.DEEPSLATE_PURRIUM_ORE, ModItems.CATFUEL));
        addDrop(ModBlocks.NETHER_PURRIUM_ORE, copperLikeOreDrops(ModBlocks.NETHER_PURRIUM_ORE, ModItems.CATFUEL));
        addDrop(ModBlocks.END_PURRIUM_ORE, copperLikeOreDrops(ModBlocks.END_PURRIUM_ORE, ModItems.CATFUEL));
    }

    public LootTable.Builder copperLikeOreDrops(Block drop, Item item) {
        return BlockLootTableGenerator.dropsWithSilkTouch(
                drop,
                (LootPoolEntry.Builder<?>)this.applyExplosionDecay(
                        drop,
                        ItemEntry.builder(ModItems.CATFUEL)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 5.0F)))
                                .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
                )
        );
    }

}
