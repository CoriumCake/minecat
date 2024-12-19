package g4.comsci.minecat.entity;

import g4.comsci.minecat.MineCat;
import g4.comsci.minecat.entity.custom.CatEntity;
import g4.comsci.minecat.entity.custom.KoratCatEntity;
import g4.comsci.minecat.entity.custom.OrangeCatEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<CatEntity> CAT1 = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MineCat.MOD_ID, "cat1"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CatEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f)).build());

    public static final EntityType<KoratCatEntity> CAT2 = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MineCat.MOD_ID, "cat2"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, KoratCatEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f)) // Size of the second cat
                    .build()
    );

    public static final EntityType<OrangeCatEntity> CAT3 = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MineCat.MOD_ID, "cat3"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, OrangeCatEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f)) // Size of the second cat
                    .build()
    );

}
