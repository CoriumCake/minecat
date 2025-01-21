package g4.comsci.minecat.item;

import g4.comsci.minecat.MineCat;
import g4.comsci.minecat.entity.ModEntities;
import g4.comsci.minecat.item.Interaction.CatInteractionAxe;
import g4.comsci.minecat.item.Interaction.CatInteractionPickaxe;
import g4.comsci.minecat.item.custom.CatLocatorItem;
import g4.comsci.minecat.item.custom.CatTeaserItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item CATFOOD = registerItem("catfood", new Item(new FabricItemSettings()));
    public static final Item PURRIUM = registerItem("purrium", new Item(new FabricItemSettings()));

    public static final Item PURRIUM_PICKAXE = registerItem("purrium_pickaxe",
            new CatInteractionPickaxe(ModToolMaterial.PURRIUM, 3, 2f, new FabricItemSettings()));
    public static final Item PURRIUM_AXE = registerItem("purrium_axe",
            new CatInteractionAxe(ModToolMaterial.PURRIUM, 8, 0.8f, new FabricItemSettings()));
    public static final Item PURRIUM_SHOVEL = registerItem("purrium_shovel",
            new ShovelItem(ModToolMaterial.PURRIUM, 3, 2f, new FabricItemSettings()));
    public static final Item PURRIUM_SWORD = registerItem("purrium_sword",
            new SwordItem(ModToolMaterial.PURRIUM, 7, 2f, new FabricItemSettings()));
    public static final Item PURRIUM_HOE = registerItem("purrium_hoe",
            new HoeItem(ModToolMaterial.PURRIUM, 2, 2f, new FabricItemSettings()));

    public static final Item CAT_TEASER = registerItem("cat_teaser", new CatTeaserItem(new FabricItemSettings().maxCount(1)));
    public static final Item CAT_LOCATOR = registerItem("cat_locator", new CatLocatorItem(new FabricItemSettings().maxCount(1)));

    public static final Item SPHYNX_SPAWN_EGG = registerItem("sphynx_spawn_egg",
            new SpawnEggItem(ModEntities.CAT1, 0x8D817F, 0xfbecd2, new FabricItemSettings()));
    public static final Item KORATCAT_SPAWN_EGG = registerItem("korat_spawn_egg",
            new SpawnEggItem(ModEntities.CAT2, 0xEAEAEA, 0x919095 , new FabricItemSettings()));
    public static final Item PERSIAN_SPAWN_EGG = registerItem("persian_spawn_egg",
            new SpawnEggItem(ModEntities.CAT3, 0xf98b00, 0x3b260f, new FabricItemSettings()));
    public static final Item SNOWSHOE_SPAWN_EGG = registerItem("snowshoe_spawn_egg",
            new SpawnEggItem(ModEntities.CAT4, 0x422823, 0xc4c4c4, new FabricItemSettings()));
    public static final Item SCOTTISH_SPAWN_EGG = registerItem("scottish_spawn_egg",
            new SpawnEggItem(ModEntities.CAT5, 0x797068, 0xe6e5e3, new FabricItemSettings()));
    public static final Item BENGAL_SPAWN_EGG = registerItem("bengal_spawn_egg",
            new SpawnEggItem(ModEntities.CAT6, 0xa58667, 0x3b260f, new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MineCat.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MineCat.LOGGER.info("Registering Mod Items for " + MineCat.MOD_ID);
    }
}
