package net.moddingplayground.sofishticated.impl.entity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType;

public final class SofishticatedEntityTypeImpl implements SofishticatedEntityType, ModInitializer {
    @Override
    public void onInitialize() {
        addSpawn(SHRIMP, SpawnGroup.WATER_CREATURE, 100, 2, 5);
        addSpawn(ANGLER_FISH, SpawnGroup.WATER_AMBIENT, 10, 1, 1);
    }

    private void addSpawn(EntityType<?> type, SpawnGroup group, int weight, int minGroupSize, int maxGroupSize) {
        TagKey<Biome> tag = createSpawnTag(type);
        BiomeModifications.addSpawn(BiomeSelectors.tag(tag), group, type, weight, minGroupSize, maxGroupSize);
    }

    public static TagKey<Biome> createSpawnTag(EntityType<?> type) {
        Identifier id = Registry.ENTITY_TYPE.getId(type);
        return TagKey.of(Registry.BIOME_KEY, new Identifier(id.getNamespace(), "spawns/%s".formatted(id.getPath())));
    }
}
