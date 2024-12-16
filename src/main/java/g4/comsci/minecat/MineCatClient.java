package g4.comsci.minecat;

import g4.comsci.minecat.entity.ModEntities;
import g4.comsci.minecat.entity.client.CatRenderer;
import g4.comsci.minecat.entity.client.ModModelLayers;
import g4.comsci.minecat.entity.client.cat1model;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.CatEntityModel;

public class MineCatClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CAT1, cat1model::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.CAT1, CatRenderer::new);
    }
}
