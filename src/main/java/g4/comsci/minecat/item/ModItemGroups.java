package g4.comsci.minecat.item;

import g4.comsci.minecat.MineCat;
import g4.comsci.minecat.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup MINECAT_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(MineCat.MOD_ID, "minecat"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.minecat"))
                    .icon(() -> new ItemStack(ModItems.CATFOOD)).entries((displayContext, entries) -> {
                        entries.add(ModItems.CATFOOD);
                        entries.add(ModItems.CAT_TEASER);
                        entries.add(ModItems.CATFUEL);
                        entries.add(ModItems.CAT_LOCATOR);

                        entries.add(ModItems.PURRIUM);
                        entries.add(ModItems.PURRIUM_PICKAXE);
                        entries.add(ModItems.PURRIUM_AXE);
                        entries.add(ModItems.PURRIUM_SHOVEL);
                        entries.add(ModItems.PURRIUM_SWORD);
                        entries.add(ModItems.PURRIUM_HOE);

                        entries.add(ModBlocks.PURRIUM_ORE);
                        entries.add(ModBlocks.DEEPSLATE_PURRIUM_ORE);
                        entries.add(ModBlocks.NETHER_PURRIUM_ORE);
                        entries.add(ModBlocks.END_PURRIUM_ORE);
                        entries.add(ModItems.CROISSANTS);
                        entries.add(ModItems.MASHED_POTATOES);
                        entries.add(ModItems.Green_Tea);
                        entries.add(ModItems.CAT_JELLY);
                        entries.add(ModItems.CAT_SOUP);
                        entries.add(ModItems.CAT1_SPAWN_EGG);

                    }).build());
    public static void registerModItemGroups(){
        MineCat.LOGGER.info("Registering Mod Item Groups for " + MineCat.MOD_ID);
    }
}
