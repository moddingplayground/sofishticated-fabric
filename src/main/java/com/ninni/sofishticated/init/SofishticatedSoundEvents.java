package com.ninni.sofishticated.init;

import com.ninni.sofishticated.Sofishticated;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SofishticatedSoundEvents {

    public static final SoundEvent ENTITY_FISH_FLOP = fish("flop");
    public static final SoundEvent ENTITY_FISH_AMBIENT = fish("ambient");
    public static final SoundEvent ENTITY_FISH_HURT = fish("hurt");
    public static final SoundEvent ENTITY_FISH_DEATH = fish("death");
    private static SoundEvent fish(String id) {
        return createEntitySound("fish", id);
    }

    public static final SoundEvent ENTITY_PIRANHA_AMBIENT = piranha("ambient");
    public static final SoundEvent ENTITY_PIRANHA_NIBBLE = piranha("nibble");
    private static SoundEvent piranha(String id) {
        return createEntitySound("piranha", id);
    }

    private static SoundEvent register(String id) {
        Identifier identifier = new Identifier(Sofishticated.MOD_ID, id);
        return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
    }

    private static SoundEvent createEntitySound(String entity, String id) {
        return register("entity." + entity + "." + id);
    }
}