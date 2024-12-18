package g4.comsci.minecat.entity.client;

import g4.comsci.minecat.MineCat;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer CAT1 =
            new EntityModelLayer(new Identifier(MineCat.MOD_ID, "cat1"),"main");

    public static final EntityModelLayer CAT2 =
            new EntityModelLayer(new Identifier(MineCat.MOD_ID, "cat2"), "main");
}
