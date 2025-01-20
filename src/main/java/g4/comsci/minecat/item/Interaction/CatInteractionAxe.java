package g4.comsci.minecat.item.Interaction;

import g4.comsci.minecat.entity.CustomCatEntity;
import g4.comsci.minecat.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CatInteractionAxe extends AxeItem {
    public CatInteractionAxe(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (player.isSneaking() && player.getStackInHand(hand).getItem() == ModItems.PURRIUM_AXE) {
            // Find nearby CustomCatEntity
            world.getEntitiesByClass(Entity.class, player.getBoundingBox().expand(10),
                            entity -> entity instanceof CustomCatEntity)
                    .forEach(entity -> {
                        CustomCatEntity customCat = (CustomCatEntity) entity;
                        TameableEntity tameable = (TameableEntity) entity;

                        // Trigger tree-chopping if cat is tamed
                        if (tameable.isTamed() && !tameable.isSitting()) {
                            customCat.toggleWoodcutting();

                            String message = customCat.isWoodcutting()
                                    ? "Your cat started chopping!"
                                    : "Your cat stopped chopping!";
                            player.sendMessage(Text.literal(message), true);
                        }
                    });

            return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
        }

        return new TypedActionResult<>(ActionResult.PASS, player.getStackInHand(hand));
    }
}