package net.moddingplayground.sofishticated.impl.entity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType;

public final class SofishticatedEntityTypeImpl implements SofishticatedEntityType, ModInitializer {
    @Override
    public void onInitialize() {
        addSpawn(SHRIMP, SpawnGroup.WATER_CREATURE, 100, 2, 5);
        addSpawn(ANGLER_FISH, SpawnGroup.WATER_AMBIENT, 10, 1, 1);
        addSpawn(SEAHORSE, SpawnGroup.WATER_AMBIENT, 10, 1, 3);
    }

    private void addSpawn(EntityType<?> type, SpawnGroup group, int weight, int minGroupSize, int maxGroupSize) {
        BiomeModifications.addSpawn(BiomeSelectors.tag(SofishticatedEntityType.createSpawnTag(type)), group, type, weight, minGroupSize, maxGroupSize);
    }
}
