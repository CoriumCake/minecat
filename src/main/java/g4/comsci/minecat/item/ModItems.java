package g4.comsci.minecat.item;

import g4.comsci.minecat.MineCat;
import g4.comsci.minecat.entity.ModEntities;
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
            new PickaxeItem(ModToolMaterial.PURRIUM, 2, 2f, new FabricItemSettings()));
    public static final Item PURRIUM_AXE = registerItem("purrium_axe",
            new AxeItem(ModToolMaterial.PURRIUM, 2, 2f, new FabricItemSettings()));
    public static final Item PURRIUM_SHOVEL = registerItem("purrium_shovel",
            new ShovelItem(ModToolMaterial.PURRIUM, 2, 2f, new FabricItemSettings()));
    public static final Item PURRIUM_SWORD = registerItem("purrium_sword",
            new SwordItem(ModToolMaterial.PURRIUM, 999, 50f, new FabricItemSettings()));
    public static final Item PURRIUM_HOE = registerItem("purrium_hoe",
            new HoeItem(ModToolMaterial.PURRIUM, 2, 2f, new FabricItemSettings()));

    public static final Item CAT_TEASER = registerItem("cat_teaser", new CatTeaserItem(new FabricItemSettings().maxCount(1)));

    public static final Item CATFUEL = registerItem("catfuel", new Item(new FabricItemSettings()));


    public static final Item CAT_LOCATOR = registerItem("cat_locator", new CatLocatorItem(new FabricItemSettings()));

    public static final Item CROISSANTS = registerItem("croissants", new Item(new FabricItemSettings().food(ModFoodComponents.CROISSANTS)));

    public static final Item MASHED_POTATOES = registerItem("mashed_potatoes", new Item(new FabricItemSettings().food(ModFoodComponents.MASHED_POTATOES)));

    public static final Item Green_Tea = registerItem("green_tea", new Item(new FabricItemSettings().food(ModFoodComponents.Green_Tea)));

    public static final Item CAT_JELLY = registerItem("cat_jelly", new Item(new Item.Settings().food(ModFoodComponents.CAT_JELLY)));

    public static final Item CAT_SOUP = registerItem("cat_soup", new Item(new Item.Settings().food(ModFoodComponents.CAT_SOUP)));

    public static final Item CAT1_SPAWN_EGG = registerItem("cat1_spawn_egg",
            new SpawnEggItem(ModEntities.CAT1, 0xa86518, 0x3b260f, new FabricItemSettings()));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(MineCat.MOD_ID, name), item);
    }

    public static void registerModItems(){
        MineCat.LOGGER.info("Registering Mod Items for " + MineCat.MOD_ID);
    }
}
