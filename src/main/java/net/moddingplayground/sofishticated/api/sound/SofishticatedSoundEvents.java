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
    private static SoundEvent anglerFish(String id) {
        return entity("angler_fish", id);
    }

    SoundEvent ENTITY_SHRIMP_AMBIENT = shrimp("ambient");
    SoundEvent ENTITY_SHRIMP_HURT = shrimp("hurt");
    SoundEvent ENTITY_SHRIMP_DEATH = shrimp("death");
    private static SoundEvent shrimp(String id) {
        return entity("shrimp", id);
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

    SoundEvent ENTITY_BIG_EYED_ATTACK = bigEyed("attack");
    SoundEvent ENTITY_BIG_EYED_HURT = bigEyed("hurt");
    SoundEvent ENTITY_BIG_EYED_DEATH = bigEyed("death");
    private static SoundEvent bigEyed(String id) {
        return entity("big_eyed", id);
    }

    private static SoundEvent register(String id) {
        Identifier identifier = new Identifier(Sofishticated.MOD_ID, id);
        return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
    }

    private static SoundEvent entity(String entity, String id) {
        return register("entity.%s.%s".formatted(entity, id));
    }
}
