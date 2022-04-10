package net.moddingplayground.sofishticated.api.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.sofishticated.api.Sofishticated;

public interface SofishticatedParticleTypes {
    DefaultParticleType ANGLER_FISH_LANTERN_GLOW = register("angler_fish_lantern_glow", FabricParticleTypes.simple());

    private static DefaultParticleType register(String id, DefaultParticleType type) {
        return Registry.register(Registry.PARTICLE_TYPE, new Identifier(Sofishticated.MOD_ID, id), type);
    }
}
