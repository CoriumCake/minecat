package g4.comsci.minecat.entity.client.Snowshoe;

import g4.comsci.minecat.MineCat;
import g4.comsci.minecat.entity.client.ModModelLayers;
import g4.comsci.minecat.entity.custom.SnowshoeCatEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SnowshoeCatRenderer extends MobEntityRenderer<SnowshoeCatEntity, SnowshoeCatModel<SnowshoeCatEntity>> {
    private static final Identifier TEXTURE = new Identifier(MineCat.MOD_ID, "textures/entity/cat4.png");

    public SnowshoeCatRenderer(EntityRendererFactory.Context context) {
        super(context, new SnowshoeCatModel<>(context.getPart(ModModelLayers.CAT4)),0.6f);
    }

    @Override
    public Identifier getTexture(SnowshoeCatEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(SnowshoeCatEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isBaby()){
            matrixStack.scale(0.4f,0.4f,0.4f);
        } else{
            matrixStack.scale(1f, 1f, 1f);
        }
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
