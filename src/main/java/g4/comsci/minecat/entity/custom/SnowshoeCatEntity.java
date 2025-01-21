package g4.comsci.minecat.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.world.World;

public class SnowshoeCatEntity extends AbstractCustomCat {
    public SnowshoeCatEntity(EntityType<? extends SnowshoeCatEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createSnowshoeCatAttributes() {
        return createBaseAttributes();
    }
}