package g4.comsci.minecat.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {

    public static final FoodComponent CROISSANTS = new FoodComponent.Builder()
            .hunger(3)
            .saturationModifier(0.25f)
            .statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 200), 0.25f)
            .build();

    public static final FoodComponent MASHED_POTATOES = new FoodComponent.Builder()
            .hunger(4)
            .saturationModifier(0.6f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100), 0.5f)
            .build();

    public static final FoodComponent Green_Tea = new FoodComponent.Builder()
            .hunger(2)
            .saturationModifier(0.1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 400), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 400), 0.5f)
            .build();

    public static final FoodComponent CAT_JELLY = new FoodComponent.Builder()
            .hunger(5)
            .saturationModifier(0.4f)
            .statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 200), 1.0f) // กระโดดสูง
            .build();

    public static final FoodComponent CAT_SOUP = new FoodComponent.Builder()
            .hunger(6)
            .saturationModifier(0.8f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300), 1.0f) // ฟื้นฟูเลือด
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 300), 0.5f) // ต้านทานความเสียหาย (50%)
            .build();
}
