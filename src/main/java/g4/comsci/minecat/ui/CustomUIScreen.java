package g4.comsci.minecat.ui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class CustomUIScreen extends Screen {

    public CustomUIScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        this.addDrawableChild(ButtonWidget.builder(
                        Text.of("Click Me!"), // Button text
                        button -> this.close() // Action on button press
                ).dimensions(this.width / 2 - 50, this.height / 2 - 10, 100, 20) // Button position and size
                .build());
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        this.renderBackground(drawContext); // Use DrawContext for background
        super.render(drawContext, mouseX, mouseY, delta); // Use DrawContext for rendering
    }

    @Override
    public void renderBackground(DrawContext drawContext) {
        super.renderBackground(drawContext);
    }
}
