package g4.comsci.minecat.entity.client.renderer;

import g4.comsci.minecat.MineCat;
import g4.comsci.minecat.entity.custom.PersianCatEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class BengalCatRenderer extends MobEntityRenderer<PersianCatEntity, CatEntityModel<PersianCatEntity>> {
    private static final Identifier TEXTURE = new Identifier(MineCat.MOD_ID, "textures/entity/cat6.png");

    public BengalCatRenderer(EntityRendererFactory.Context context) {
        super(context, new CatEntityModel<>(context.getPart(EntityModelLayers.CAT)), 0.6f);
    }

    @Override
    public Identifier getTexture(PersianCatEntity catEntity) {
        return TEXTURE;
    }
}