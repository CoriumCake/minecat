package g4.comsci.minecat;

import g4.comsci.minecat.block.ModBlocks;
import g4.comsci.minecat.entity.ModEntities;
import g4.comsci.minecat.entity.custom.SphynxCatEntity;
import g4.comsci.minecat.entity.custom.PersianCatEntity;
import g4.comsci.minecat.item.ModItemGroups;
import g4.comsci.minecat.item.ModItems;
import g4.comsci.minecat.network.CatLocatorPacketHandler;
import g4.comsci.minecat.sound.ModSounds;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MineCat implements ModInitializer {

	public static final String MOD_ID = "minecat";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();
		ModBlocks.registerModBlocks();
		ModSounds.registerSounds();
		CatLocatorPacketHandler.register();
		FabricDefaultAttributeRegistry.register(ModEntities.CAT1, SphynxCatEntity.createCatAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CAT2, SphynxCatEntity.createCatAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CAT3, PersianCatEntity.createOrangeCatAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CAT4, SphynxCatEntity.createCatAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CAT5, SphynxCatEntity.createCatAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CAT6, SphynxCatEntity.createCatAttributes());

		FuelRegistry.INSTANCE.add(ModItems.CATFUEL,200);
	}
}