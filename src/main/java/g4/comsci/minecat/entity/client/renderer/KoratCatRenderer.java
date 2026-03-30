package g4.comsci.minecat.entity.client.renderer;

import g4.comsci.minecat.MineCat;
import g4.comsci.minecat.entity.custom.KoratCatEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class KoratCatRenderer extends MobEntityRenderer<KoratCatEntity, CatEntityModel<KoratCatEntity>> {
    private static final Identifier TEXTURE = new Identifier(MineCat.MOD_ID, "textures/entity/cat2.png");

    public KoratCatRenderer(EntityRendererFactory.Context context) {
        super(context, new CatEntityModel<>(context.getPart(EntityModelLayers.CAT)), 0.4F);
    }

    @Override
    public Identifier getTexture(KoratCatEntity catEntity) {
        return TEXTURE;
    }
}