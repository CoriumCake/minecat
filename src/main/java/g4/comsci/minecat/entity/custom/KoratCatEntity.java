package g4.comsci.minecat.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.world.World;

public class KoratCatEntity extends AbstractCustomCat {
    public KoratCatEntity(EntityType<? extends PersianCatEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createKoratCatAttributes() {
        return createBaseAttributes();
    }
}