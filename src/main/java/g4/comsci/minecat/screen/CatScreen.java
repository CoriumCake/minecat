package g4.comsci.minecat.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CatScreen extends HandledScreen<CatScreenHandler> {

    private static final Identifier BACKGROUND_TEXTURE = new Identifier("minecat", "textures/gui/blank_screen.png");

    public CatScreen(CatScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 200; // Adjust the width as needed
        this.backgroundHeight = 100; // Adjust the height as needed
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        // Draw a plain background
        context.drawTexture(BACKGROUND_TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context); // Render the screen background
        super.render(context, mouseX, mouseY, delta);

        // Draw only the desired text
        context.drawText(this.textRenderer, "This is your pet Korat Cat!", this.x + 10, this.y + 30, 0xFFFFFF, false);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        // Override this method to prevent the "Inventory" text from rendering
    }

    @Override
    public void init() {
        super.init();
        this.titleX = -1000; // Move the title out of view to remove "Korat Cat GUI"
    }
}
