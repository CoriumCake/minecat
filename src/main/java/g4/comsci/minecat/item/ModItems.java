package g4.comsci.minecat.item;

import g4.comsci.minecat.MineCat;
import g4.comsci.minecat.item.custom.CatLocatorItem;
import g4.comsci.minecat.item.custom.CatTeaserItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item CATFOOD = registerItem("catfood", new Item(new FabricItemSettings()));

    public static final Item CAT_TEASER = registerItem("cat_teaser", new CatTeaserItem(new FabricItemSettings()));

    public static final Item CATFUEL = registerItem("catfuel", new Item(new FabricItemSettings()));

    public static final Item CAT_LOCATOR = registerItem("cat_locator", new CatLocatorItem(new FabricItemSettings()));

    public static final Item CROISSANTS = registerItem("croissants", new Item(new FabricItemSettings().food(ModFoodComponents.CROISSANTS)));

    public static final Item MASHED_POTATOES = registerItem("mashed_potatoes", new Item(new FabricItemSettings().food(ModFoodComponents.MASHED_POTATOES)));

    public static final Item COCOA_DRINK = registerItem("cocoa_drink", new Item(new FabricItemSettings().food(ModFoodComponents.COCOA_DRINK)));

    public static final Item CAT_JELLY = registerItem("cat_jelly", new Item(new Item.Settings().food(ModFoodComponents.CAT_JELLY)));

    public static final Item CAT_SOUP = registerItem("cat_soup", new Item(new Item.Settings().food(ModFoodComponents.CAT_SOUP)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(MineCat.MOD_ID, name), item);
    }

    public static void registerModItems(){
        MineCat.LOGGER.info("Registering Mod Items for " + MineCat.MOD_ID);
    }
}
