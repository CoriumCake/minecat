package g4.comsci.minecat.entity.client.renderer;

import g4.comsci.minecat.MineCat;
import g4.comsci.minecat.entity.client.ModModelLayers;
import g4.comsci.minecat.entity.custom.SphynxCatEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SphynxCatRenderer extends MobEntityRenderer<SphynxCatEntity, CatEntityModel<SphynxCatEntity>> {
    private static final Identifier TEXTURE = new Identifier(MineCat.MOD_ID, "textures/entity/cat1.png");

    public SphynxCatRenderer(EntityRendererFactory.Context context) {
        super(context, new CatEntityModel<>(context.getPart(EntityModelLayers.CAT)), 0.6f);
    }

    @Override
    public Identifier getTexture(SphynxCatEntity catEntity) {
        return TEXTURE;
    }

    @Override
    public void render(SphynxCatEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isBaby()){
            matrixStack.scale(0.4f,0.4f,0.4f);
        } else{
            matrixStack.scale(1f, 1f, 1f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
