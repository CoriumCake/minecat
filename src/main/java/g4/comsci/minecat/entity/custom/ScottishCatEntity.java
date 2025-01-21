package g4.comsci.minecat.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.world.World;

public class ScottishCatEntity extends AbstractCustomCat {
    public ScottishCatEntity(EntityType<? extends ScottishCatEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createScottishCatAttributes() {
        return createBaseAttributes();
    }
}