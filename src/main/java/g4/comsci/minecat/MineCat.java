package g4.comsci.minecat;

import g4.comsci.minecat.block.ModBlocks;
import g4.comsci.minecat.entity.ModEntities;
import g4.comsci.minecat.entity.custom.BengalCatEntity;
import g4.comsci.minecat.entity.custom.KoratCatEntity;
import g4.comsci.minecat.entity.custom.SphynxCatEntity;
import g4.comsci.minecat.entity.custom.PersianCatEntity;
import g4.comsci.minecat.entity.custom.ScottishCatEntity;
import g4.comsci.minecat.entity.custom.SnowshoeCatEntity;

import g4.comsci.minecat.item.ModItemGroups;
import g4.comsci.minecat.item.ModItems;

import g4.comsci.minecat.network.CatLocatorPacketHandler;

import g4.comsci.minecat.sound.ModSounds;
import g4.comsci.minecat.world.gen.ModWorldGeneration;
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
		FabricDefaultAttributeRegistry.register(ModEntities.CAT1, SphynxCatEntity.createSphynxCatAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CAT2, KoratCatEntity.createKoratCatAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CAT3, PersianCatEntity.createPersianCatAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CAT4, SnowshoeCatEntity.createSnowshoeCatAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CAT5, ScottishCatEntity.createScottishCatAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CAT6, BengalCatEntity.createBengalCatAttributes());

		ModWorldGeneration.generateModWorldGeneration();
	}
}