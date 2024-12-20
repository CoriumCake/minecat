package g4.comsci.minecat;

import g4.comsci.minecat.entity.ModEntities;
import g4.comsci.minecat.entity.client.CatRenderer;
import g4.comsci.minecat.entity.client.HeavenlyCat.HeavenlyCatModel;
import g4.comsci.minecat.entity.client.HeavenlyCat.HeavenlyCatRenderer;
import g4.comsci.minecat.entity.client.KoratCat.KoratCatModel;
import g4.comsci.minecat.entity.client.KoratCat.KoratCatRenderer;
import g4.comsci.minecat.entity.client.LuciferCat.LuciferCatModel;
import g4.comsci.minecat.entity.client.LuciferCat.LuciferCatRenderer;
import g4.comsci.minecat.entity.client.ModModelLayers;
import g4.comsci.minecat.entity.client.OrangeCat.OrangeCatModel;
import g4.comsci.minecat.entity.client.OrangeCat.OrangeCatRenderer;
import g4.comsci.minecat.entity.client.ThailandCat.ThailandCatModel;
import g4.comsci.minecat.entity.client.ThailandCat.ThailandCatRenderer;
import g4.comsci.minecat.entity.client.cat1model;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class MineCatClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CAT1, cat1model::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.CAT1, CatRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CAT2, KoratCatModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.CAT2, KoratCatRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CAT3, OrangeCatModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.CAT3, OrangeCatRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CAT4, LuciferCatModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.CAT4, LuciferCatRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CAT5, HeavenlyCatModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.CAT5, HeavenlyCatRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CAT6, ThailandCatModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.CAT6, ThailandCatRenderer::new);
    }
}
