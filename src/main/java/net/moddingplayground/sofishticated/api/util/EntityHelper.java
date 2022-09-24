package net.moddingplayground.sofishticated.api.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.moddingplayground.sofishticated.mixin.access.LivingEntityAccessor;

public interface EntityHelper {
    static void playSoundFromEntity(PlayerEntity except, LivingEntity entity, SoundEvent sound) {
        entity.world.playSoundFromEntity(except, entity, sound, entity.getSoundCategory(), ((LivingEntityAccessor) entity).invokeGetSoundVolume(), entity.getSoundPitch());
    }

    static void playSoundFromEntity(LivingEntity entity, SoundEvent sound) {
        playSoundFromEntity(null, entity, sound);
    }

    static void spawnParticles(Entity entity, ParticleEffect particle, double widthScale, double heightScale, double delta, int count, double speed) {
        if (entity.world instanceof ServerWorld world) {
            world.spawnParticles(
                particle,
                entity.getParticleX(widthScale), entity.getBodyY(heightScale), entity.getParticleZ(widthScale),
                count, delta, delta, delta, speed
            );
        }
    }

    static void addParticle(Entity entity, ParticleEffect particle, double widthScale, double heightScale) {
        entity.world.addParticle(
            particle,
            entity.getParticleX(widthScale), entity.getBodyY(heightScale), entity.getParticleZ(widthScale),
            0.0D, 0.0D, 0.0D
        );
    }
}
