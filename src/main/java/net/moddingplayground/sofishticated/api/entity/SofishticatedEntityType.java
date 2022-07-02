package net.moddingplayground.sofishticated.api.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.moddingplayground.sofishticated.api.Sofishticated;

public interface SofishticatedEntityType {
    EntityType<ShrimpEntity> SHRIMP = register("shrimp",
        FabricEntityTypeBuilder.createMob()
                               .entityFactory(ShrimpEntity::new).spawnGroup(SpawnGroup.WATER_CREATURE)
                               .spawnRestriction(SpawnRestriction.Location.IN_WATER, Heightmap.Type.OCEAN_FLOOR, ShrimpEntity::canSpawn)
                               .defaultAttributes(ShrimpEntity::createShrimpAttributes)
                               .dimensions(EntityDimensions.changing(0.6F, 0.4F))
                               .trackRangeBlocks(8)
    );

    EntityType<AnglerFishEntity> ANGLER_FISH = register("angler_fish",
        FabricEntityTypeBuilder.createMob()
                               .entityFactory(AnglerFishEntity::new).spawnGroup(SpawnGroup.WATER_AMBIENT)
                               .spawnRestriction(SpawnRestriction.Location.IN_WATER, Heightmap.Type.OCEAN_FLOOR, AnglerFishEntity::canSpawn)
                               .defaultAttributes(AnglerFishEntity::createAnglerFishAttributes)
                               .dimensions(EntityDimensions.changing(0.6F, 0.6F))
                               .trackRangeBlocks(8)
    );

    EntityType<SeahorseEntity> SEAHORSE = register("seahorse",
        FabricEntityTypeBuilder.createMob()
                               .entityFactory(SeahorseEntity::new).spawnGroup(SpawnGroup.WATER_AMBIENT)
                               .spawnRestriction(SpawnRestriction.Location.IN_WATER, Heightmap.Type.OCEAN_FLOOR, SeahorseEntity::canSpawn)
                               .defaultAttributes(SeahorseEntity::createSeahorseAttributes)
                               .dimensions(EntityDimensions.changing(0.3F, 0.6F))
                               .trackRangeBlocks(8)
    );

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> type) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Sofishticated.MOD_ID, id), type.build());
    }

    static TagKey<Biome> createSpawnTag(EntityType<?> type) {
        Identifier id = Registry.ENTITY_TYPE.getId(type);
        return TagKey.of(Registry.BIOME_KEY, new Identifier(id.getNamespace(), "spawns/%s".formatted(id.getPath())));
    }
}
