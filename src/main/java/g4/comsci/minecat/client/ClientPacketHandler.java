package g4.comsci.minecat.client;

import g4.comsci.minecat.commands.CustomUICommand;
import g4.comsci.minecat.ui.CustomUIScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ClientPacketHandler {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(CustomUICommand.OPEN_UI_PACKET, (client, handler, buf, responseSender) -> {
            client.execute(() -> {
                MinecraftClient.getInstance().setScreen(new CustomUIScreen(Text.of("Custom UI")));
            });
        });
    }
}
