package g4.comsci.minecat.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import g4.comsci.minecat.util.UuidUtil;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CatData extends PersistentState {
    private static final String KEY = "minecat_data";
    private final List<UUID> catList = new ArrayList<>();

    public static CatData get(MinecraftServer server) {
        return server.getWorld(World.OVERWORLD).getPersistentStateManager().getOrCreate(
                CatData::fromNbt,
                CatData::new,
                KEY
        );
    }

    public static CatData fromNbt(NbtCompound nbt) {
        CatData data = new CatData();
        if (nbt.contains(KEY)) {
            NbtList uuidList = nbt.getList(KEY, 10); // 10 is the NBT type for compound
            for (int i = 0; i < uuidList.size(); i++) {
                NbtCompound uuidCompound = uuidList.getCompound(i);
                data.catList.add(UuidUtil.fromNbt(uuidCompound));
            }
        }
        return data;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList uuidList = new NbtList();
        for (UUID uuid : catList) {
            uuidList.add(UuidUtil.toNbt(uuid));
        }
        nbt.put(KEY, uuidList);
        return nbt;
    }

    public void addCat(UUID catId) {
        if (!catList.contains(catId)) {
            catList.add(catId);
            markDirty(); // Mark for saving
        }
    }

    public void removeCat(UUID catId) {
        catList.remove(catId);
        markDirty(); // Mark for saving
    }

    public List<UUID> getCatList() {
        return catList;
    }
}
