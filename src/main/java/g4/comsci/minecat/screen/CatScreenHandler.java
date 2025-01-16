package g4.comsci.minecat.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class CatScreenHandler extends ScreenHandler {
    public static final ScreenHandlerType<CatScreenHandler> CAT_SCREEN_HANDLER =
            ScreenHandlerRegistry.registerSimple(new Identifier("minecat", "cat_screen"), CatScreenHandler::new);

    public CatScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(CAT_SCREEN_HANDLER, syncId);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true; // Allow all players to open the GUI
    }
}
