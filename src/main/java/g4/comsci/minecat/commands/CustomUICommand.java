package g4.comsci.minecat.commands;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CustomUICommand {
    public static final Identifier OPEN_UI_PACKET = new Identifier("mymod", "open_ui");

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    CommandManager.literal("customui")
                            .executes(context -> {
                                var player = context.getSource().getPlayer();
                                if (player != null) {
                                    ServerPlayNetworking.send(player, OPEN_UI_PACKET, PacketByteBufs.empty());
                                }
                                return 1;
                            })
            );
        });
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        var player = context.getSource().getPlayer();
        if (player != null) {
            player.sendMessage(Text.of("This command works!"), false);
        }
        return 1;
    }
}
