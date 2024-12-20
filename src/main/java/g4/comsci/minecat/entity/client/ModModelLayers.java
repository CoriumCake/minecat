package g4.comsci.minecat.entity.client;

import g4.comsci.minecat.MineCat;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer CAT1 =
            new EntityModelLayer(new Identifier(MineCat.MOD_ID, "cat1"),"main");

    public static final EntityModelLayer CAT2 =
            new EntityModelLayer(new Identifier(MineCat.MOD_ID, "cat2"), "main");

    public static final EntityModelLayer CAT3 =
            new EntityModelLayer(new Identifier(MineCat.MOD_ID, "cat3"), "main");

    public static final EntityModelLayer CAT4 =
            new EntityModelLayer(new Identifier(MineCat.MOD_ID, "cat4"), "main");

    public static final EntityModelLayer CAT5 =
            new EntityModelLayer(new Identifier(MineCat.MOD_ID, "cat5"), "main");

    public static final EntityModelLayer CAT6 =
            new EntityModelLayer(new Identifier(MineCat.MOD_ID, "cat6"), "main");

}
