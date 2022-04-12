package net.moddingplayground.sofishticated.api.sound;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.sofishticated.api.Sofishticated;

public interface SofishticatedSoundEvents {
    SoundEvent ENTITY_FISH_FLOP = fish("flop");
    SoundEvent ENTITY_FISH_AMBIENT = fish("ambient");
    SoundEvent ENTITY_FISH_HURT = fish("hurt");
    SoundEvent ENTITY_FISH_DEATH = fish("death");
    private static SoundEvent fish(String id) {
        return entity("fish", id);
    }

    SoundEvent ENTITY_ANGLER_FISH_DEFLATE = anglerFish("deflate");
    SoundEvent ENTITY_ANGLER_FISH_EFFECT_GIVE = anglerFish("effect.give");
    SoundEvent ENTITY_ANGLER_FISH_EFFECT_DENY = anglerFish("effect.deny");
    private static SoundEvent anglerFish(String id) {
        return entity("angler_fish", id);
    }

    SoundEvent ENTITY_SHRIMP_AMBIENT = shrimp("ambient");
    SoundEvent ENTITY_SHRIMP_HURT = shrimp("hurt");
    SoundEvent ENTITY_SHRIMP_DEATH = shrimp("death");
    private static SoundEvent shrimp(String id) {
        return entity("shrimp", id);
    }

    SoundEvent ENTITY_SEAHORSE_CLICK = seahorse("click");
    SoundEvent ENTITY_SEAHORSE_GROWL = seahorse("growl");
    SoundEvent ENTITY_SEAHORSE_HURT = seahorse("hurt");
    SoundEvent ENTITY_SEAHORSE_DEATH = seahorse("death");
    private static SoundEvent seahorse(String id) {
        return entity("seahorse", id);
    }

    SoundEvent ENTITY_PIRANHA_AMBIENT = piranha("ambient");
    SoundEvent ENTITY_PIRANHA_NIBBLE = piranha("nibble");
    private static SoundEvent piranha(String id) {
        return entity("piranha", id);
    }

    SoundEvent ENTITY_SHARK_BITE = shark("bite");
    private static SoundEvent shark(String id) {
        return entity("shark", id);
    }

    private static SoundEvent register(String id) {
        Identifier identifier = new Identifier(Sofishticated.MOD_ID, id);
        return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
    }

    private static SoundEvent entity(String entity, String id) {
        return register("entity.%s.%s".formatted(entity, id));
    }
}
