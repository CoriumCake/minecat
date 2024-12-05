package g4.comsci.minecat.sound;

import g4.comsci.minecat.MineCat;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent CAT_TEASER_RCLICK = registerSoundEvent("cat_teaser_rclick");

    private static SoundEvent registerSoundEvent(String name){
        Identifier id = new Identifier(MineCat.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds(){
        MineCat.LOGGER.info("Registering Sounds for " + MineCat.MOD_ID);
    }
}
