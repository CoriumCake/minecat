package g4.comsci.minecat;

import g4.comsci.minecat.block.ModBlocks;
import g4.comsci.minecat.client.ClientPacketHandler;
import g4.comsci.minecat.commands.CustomUICommand;
import g4.comsci.minecat.entity.ModEntities;
import g4.comsci.minecat.entity.custom.CatEntity;
import g4.comsci.minecat.item.ModItemGroups;
import g4.comsci.minecat.item.ModItems;
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
		//CustomUICommand.register();
		//ClientPacketHandler.register();
		FabricDefaultAttributeRegistry.register(ModEntities.CAT1, CatEntity.createCatAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CAT2, CatEntity.createCatAttributes());

		FuelRegistry.INSTANCE.add(ModItems.CATFUEL,200);
	}
}