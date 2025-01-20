package g4.comsci.minecat;

import g4.comsci.minecat.entity.ModEntities;
import g4.comsci.minecat.entity.client.renderer.ScottishCatRenderer;
import g4.comsci.minecat.entity.client.renderer.SphynxCatRenderer;
import g4.comsci.minecat.entity.client.renderer.KoratCatRenderer;
import g4.comsci.minecat.entity.client.renderer.SnowshoeCatRenderer;
import g4.comsci.minecat.entity.client.renderer.PersianCatRenderer;
import g4.comsci.minecat.entity.client.renderer.BengalCatRenderer;

import g4.comsci.minecat.item.custom.CatLocatorItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;


@Environment(EnvType.CLIENT)
public class MineCatClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Register entity models and renderers
        EntityRendererRegistry.register(ModEntities.CAT1, SphynxCatRenderer::new);
        EntityRendererRegistry.register(ModEntities.CAT2, KoratCatRenderer::new);
        EntityRendererRegistry.register(ModEntities.CAT3, PersianCatRenderer::new);
        EntityRendererRegistry.register(ModEntities.CAT4, SnowshoeCatRenderer::new);
        EntityRendererRegistry.register(ModEntities.CAT5, ScottishCatRenderer::new);
        EntityRendererRegistry.register(ModEntities.CAT6, BengalCatRenderer::new);

    }

    public static void openCatLocatorScreen(PlayerEntity player) {
        MinecraftClient.getInstance().setScreen(new CatLocatorItem.CatLocatorScreen(player));
    }
}
