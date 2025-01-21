package g4.comsci.minecat.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.world.World;

public class SphynxCatEntity extends AbstractCustomCat {
    public SphynxCatEntity(EntityType<? extends SphynxCatEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createSphynxCatAttributes() {
        return createBaseAttributes();
    }
}