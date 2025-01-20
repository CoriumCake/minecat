package g4.comsci.minecat.item.Interaction;

import g4.comsci.minecat.entity.CustomCatEntity;
import g4.comsci.minecat.entity.custom.KoratCatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CatInteractionPickaxe extends PickaxeItem {
    public CatInteractionPickaxe(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (player.isSneaking()) {
            // Adjust search to look for a compatible entity class
            world.getEntitiesByClass(KoratCatEntity.class, player.getBoundingBox().expand(5), entity -> true)
                    .forEach(customCat -> {
                        customCat.toggleMining(); // Directly use the entity since it's already KoratCatEntity
                        String message = customCat.isMining()
                                ? "Your cat started mining!"
                                : "Your cat start mining!";
                        player.sendMessage(Text.literal(message), true);
                    });

            return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
        }

        return new TypedActionResult<>(ActionResult.PASS, player.getStackInHand(hand));
    }

}
