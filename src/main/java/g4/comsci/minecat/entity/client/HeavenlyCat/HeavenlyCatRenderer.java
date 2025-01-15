package g4.comsci.minecat.entity.client.HeavenlyCat;

import g4.comsci.minecat.MineCat;
import g4.comsci.minecat.entity.client.LuciferCat.LuciferCatModel;
import g4.comsci.minecat.entity.client.ModModelLayers;
import g4.comsci.minecat.entity.custom.HeavenlyCatEntity;
import g4.comsci.minecat.entity.custom.LuciferCatEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class HeavenlyCatRenderer extends MobEntityRenderer<HeavenlyCatEntity, HeavenlyCatModel<HeavenlyCatEntity>> {
    private static final Identifier TEXTURE = new Identifier(MineCat.MOD_ID, "textures/entity/cat5.png");

    public HeavenlyCatRenderer(EntityRendererFactory.Context context) {
        super(context, new HeavenlyCatModel<>(context.getPart(ModModelLayers.CAT5)),0.6f);
    }

    @Override
    public Identifier getTexture(HeavenlyCatEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(HeavenlyCatEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isBaby()){
            matrixStack.scale(0.4f,0.4f,0.4f);
        } else{
            matrixStack.scale(1f, 1f, 1f);
        }
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}