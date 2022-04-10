package net.moddingplayground.sofishticated.impl.client.particle;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.GlowParticle;
import net.moddingplayground.sofishticated.api.particle.SofishticatedParticleTypes;

@Environment(EnvType.CLIENT)
public final class SofishticatedParticleTypesClientImpl implements SofishticatedParticleTypes, ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry particles = ParticleFactoryRegistry.getInstance();
        particles.register(ANGLER_FISH_LANTERN_GLOW, GlowParticle.GlowFactory::new);
    }
}
