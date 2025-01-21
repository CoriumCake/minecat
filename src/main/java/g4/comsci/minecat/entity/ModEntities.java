package g4.comsci.minecat.entity;

import g4.comsci.minecat.MineCat;
import g4.comsci.minecat.entity.custom.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<SphynxCatEntity> CAT1 = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MineCat.MOD_ID, "cat1"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SphynxCatEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f)).build());

    public static final EntityType<PersianCatEntity> CAT2 = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MineCat.MOD_ID, "cat2"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PersianCatEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f))
                    .build()
    );

    public static final EntityType<PersianCatEntity> CAT3 = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MineCat.MOD_ID, "cat3"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PersianCatEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f))
                    .build()
    );

    public static final EntityType<SnowshoeCatEntity> CAT4 = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MineCat.MOD_ID, "cat4"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SnowshoeCatEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f))
                    .build()
    );

    public static final EntityType<ScottishCatEntity> CAT5 = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MineCat.MOD_ID, "cat5"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ScottishCatEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f))
                    .build()
    );

    public static final EntityType<PersianCatEntity> CAT6 = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MineCat.MOD_ID, "cat6"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PersianCatEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f))
                    .build()
    );

}
