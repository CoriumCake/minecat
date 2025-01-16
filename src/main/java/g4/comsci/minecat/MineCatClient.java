package g4.comsci.minecat;

import g4.comsci.minecat.entity.ModEntities;
import g4.comsci.minecat.entity.client.ScottishCat.ScottishCatModel;
import g4.comsci.minecat.entity.client.ScottishCat.ScottishCatRenderer;
import g4.comsci.minecat.entity.client.SphynxCat.SphynxCatRenderer;
import g4.comsci.minecat.entity.client.KoratCat.KoratCatModel;
import g4.comsci.minecat.entity.client.KoratCat.KoratCatRenderer;
import g4.comsci.minecat.entity.client.Snowshoe.SnowshoeCatModel;
import g4.comsci.minecat.entity.client.Snowshoe.SnowshoeCatRenderer;
import g4.comsci.minecat.entity.client.ModModelLayers;
import g4.comsci.minecat.entity.client.PersianCat.PersianCatModel;
import g4.comsci.minecat.entity.client.PersianCat.PersianCatRenderer;
import g4.comsci.minecat.entity.client.BengalCat.BengalCatModel;
import g4.comsci.minecat.entity.client.BengalCat.BengalCatRenderer;
import g4.comsci.minecat.entity.client.SphynxCat.SphynxCatModel;

import g4.comsci.minecat.item.custom.CatLocatorItem;
import g4.comsci.minecat.screen.CatScreenHandler;
import g4.comsci.minecat.screen.ModScreenHandlers;
import g4.comsci.minecat.screen.CatScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;


@Environment(EnvType.CLIENT)
public class MineCatClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register entity models and renderers
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CAT1, SphynxCatModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.CAT1, SphynxCatRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CAT2, KoratCatModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.CAT2, KoratCatRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CAT3, PersianCatModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.CAT3, PersianCatRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CAT4, SnowshoeCatModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.CAT4, SnowshoeCatRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CAT5, ScottishCatModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.CAT5, ScottishCatRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CAT6, BengalCatModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.CAT6, BengalCatRenderer::new);

        // Register entity renderer
        EntityRendererRegistry.register(ModEntities.CAT2, KoratCatRenderer::new);

        // Register screen
        ScreenRegistry.register(CatScreenHandler.CAT_SCREEN_HANDLER, CatScreen::new);
    }

    public static void openCatLocatorScreen(PlayerEntity player) {
        MinecraftClient.getInstance().setScreen(new CatLocatorItem.CatLocatorScreen(player));
    }
}
