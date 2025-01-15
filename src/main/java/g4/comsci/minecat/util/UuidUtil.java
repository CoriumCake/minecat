package g4.comsci.minecat.util;

import net.minecraft.nbt.NbtCompound;
import java.util.UUID;

public class UuidUtil {
    public static NbtCompound toNbt(UUID uuid) {
        NbtCompound nbt = new NbtCompound();
        nbt.putLong("Most", uuid.getMostSignificantBits());
        nbt.putLong("Least", uuid.getLeastSignificantBits());
        return nbt;
    }

    public static UUID fromNbt(NbtCompound nbt) {
        return new UUID(nbt.getLong("Most"), nbt.getLong("Least"));
    }
}
