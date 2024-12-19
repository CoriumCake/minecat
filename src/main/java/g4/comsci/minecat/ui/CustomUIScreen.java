package g4.comsci.minecat.ui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class CustomUIScreen extends Screen {
    public CustomUIScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        // ปุ่มสำหรับตัวเลือกแมว
        this.addDrawableChild(ButtonWidget.builder(Text.of("Siamese"), (button) -> {
            client.player.sendMessage(Text.of("คุณเลือก Siamese"), false);
            this.close();
        }).dimensions(this.width / 2 - 50, this.height / 2 - 30, 100, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.of("Persian"), (button) -> {
            client.player.sendMessage(Text.of("คุณเลือก Persian"), false);
            this.close();
        }).dimensions(this.width / 2 - 50, this.height / 2, 100, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.of("Maine Coon"), (button) -> {
            client.player.sendMessage(Text.of("คุณเลือก Maine Coon"), false);
            this.close();
        }).dimensions(this.width / 2 - 50, this.height / 2 + 30, 100, 20).build());
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        this.renderBackground(drawContext);
        super.render(drawContext, mouseX, mouseY, delta);
    }
}
