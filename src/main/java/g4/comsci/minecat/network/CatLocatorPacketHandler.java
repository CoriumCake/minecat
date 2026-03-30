package g4.comsci.minecat.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class CatLocatorPacketHandler {
    public static final Identifier SET_GLOW_PACKET_ID = new Identifier("minecat", "set_glow");
    private static final int GLOW_DURATION_TICKS = 300; // 15 seconds

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(SET_GLOW_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            int entityId = buf.readInt(); // Read the entity ID
            server.execute(() -> {
                Entity entity = player.getWorld().getEntityById(entityId);
                if (entity != null) {
                    entity.setGlowing(true); // Apply the glow effect

                    // Schedule glow removal after GLOW_DURATION_TICKS
                    final Entity glowEntity = entity;
                    server.execute(() -> {
                        // Use a simple tick counter via scheduled task
                        Thread glowTimer = new Thread(() -> {
                            try {
                                Thread.sleep(GLOW_DURATION_TICKS * 50L); // Convert ticks to ms (50ms per tick)
                            } catch (InterruptedException ignored) {}
                            server.execute(() -> {
                                if (glowEntity.isAlive()) {
                                    glowEntity.setGlowing(false);
                                }
                            });
                        });
                        glowTimer.setDaemon(true);
                        glowTimer.start();
                    });
                }
            });
        });
    }
}
