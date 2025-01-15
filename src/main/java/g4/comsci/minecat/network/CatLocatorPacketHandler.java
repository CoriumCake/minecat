package g4.comsci.minecat.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class CatLocatorPacketHandler {
    public static final Identifier SET_GLOW_PACKET_ID = new Identifier("minecat", "set_glow");

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(SET_GLOW_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            int entityId = buf.readInt(); // Read the entity ID
            server.execute(() -> {
                Entity entity = player.getWorld().getEntityById(entityId);
                if (entity != null) {
                    entity.setGlowing(true); // Apply the glow effect
                }
            });
        });
    }
}
