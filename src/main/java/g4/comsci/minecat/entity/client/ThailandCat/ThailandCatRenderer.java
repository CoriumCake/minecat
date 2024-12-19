package g4.comsci.minecat.entity.client.ThailandCat;

import g4.comsci.minecat.MineCat;
import g4.comsci.minecat.entity.client.ModModelLayers;
import g4.comsci.minecat.entity.client.OrangeCat.OrangeCatModel;
import g4.comsci.minecat.entity.custom.OrangeCatEntity;
import g4.comsci.minecat.entity.custom.ThailandCatEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ThailandCatRenderer extends MobEntityRenderer<ThailandCatEntity, ThailandCatModel<ThailandCatEntity>> {
    private static final Identifier TEXTURE = new Identifier(MineCat.MOD_ID, "textures/entity/cat6.png");

    public ThailandCatRenderer(EntityRendererFactory.Context context) {
        super(context, new ThailandCatModel<>(context.getPart(ModModelLayers.CAT6)),0.6f);
    }

    @Override
    public Identifier getTexture(ThailandCatEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(ThailandCatEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isBaby()){
            matrixStack.scale(0.4f,0.4f,0.4f);
        } else{
            matrixStack.scale(1f, 1f, 1f);
        }
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
