package com.ninni.sofishticated.init;

import com.ninni.sofishticated.Sofishticated;
import com.ninni.sofishticated.entity.AnglerFishEntity;
import com.ninni.sofishticated.entity.MantaRayEntity;
import com.ninni.sofishticated.entity.SunfishEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

@SuppressWarnings("deprecation")
public class SofishticatedEntities {
    public static final EntityType<AnglerFishEntity> ANGLER_FISH = register(
        "angler_fish",
        FabricEntityTypeBuilder.createMob()
                               .entityFactory(AnglerFishEntity::new)
                               .defaultAttributes(AnglerFishEntity::createAnglerFishAttributes)
                               .spawnGroup(SpawnGroup.WATER_AMBIENT)
                               .spawnRestriction(SpawnRestriction.Location.IN_WATER, Heightmap.Type.OCEAN_FLOOR, FishEntity::canSpawn)
                               .dimensions(EntityDimensions.fixed(0.6F, 0.6F))
                               .trackRangeBlocks(8),
        new int[]{ 0x372d2a, 0x73efe8 }
    );

    public static final EntityType<SunfishEntity> SUNFISH = register(
        "sunfish",
        FabricEntityTypeBuilder.createMob()
                               .entityFactory(SunfishEntity::new)
                               .defaultAttributes(SunfishEntity::createSunfishAttributes)
                               .spawnGroup(SpawnGroup.WATER_CREATURE)
                               .spawnRestriction(SpawnRestriction.Location.IN_WATER, Heightmap.Type.OCEAN_FLOOR, SunfishEntity::canSpawn)
                               .dimensions(EntityDimensions.fixed(1.6F, 2.0F))
                               .trackRangeBlocks(8),
        new int[]{ 0x505359, 0xcfd0d0 }
    );

    public static final EntityType<MantaRayEntity> MANTA_RAY = register(
        "manta_ray",
        FabricEntityTypeBuilder.createMob()
                               .entityFactory(MantaRayEntity::new)
                               .defaultAttributes(MantaRayEntity::createMantaRayAttributes)
                               .spawnGroup(SpawnGroup.WATER_CREATURE)
                               .spawnRestriction(SpawnRestriction.Location.IN_WATER, Heightmap.Type.OCEAN_FLOOR, MantaRayEntity::canSpawn)
                               .dimensions(EntityDimensions.fixed(3.5F, 0.6F))
                               .trackRangeBlocks(8),
        new int[]{ 0x303030, 0xe5e9e8 }
    );

    static {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.DEEP_OCEAN), SpawnGroup.WATER_AMBIENT, SofishticatedEntities.ANGLER_FISH, 10, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.DEEP_WARM_OCEAN), SpawnGroup.WATER_CREATURE, SofishticatedEntities.MANTA_RAY, 8, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.COLD_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN), SpawnGroup.WATER_CREATURE, SofishticatedEntities.SUNFISH, 10, 1, 1);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Entity> EntityType<T> register(String id, EntityType<T> entityType, int[] spawnEggColors) {
        if (spawnEggColors != null)
            Registry.register(Registry.ITEM, new Identifier(Sofishticated.MOD_ID, id + "_spawn_egg"), new SpawnEggItem((EntityType<? extends MobEntity>) entityType, spawnEggColors[0], spawnEggColors[1], new Item.Settings().maxCount(64).group(Sofishticated.ITEM_GROUP)));

        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Sofishticated.MOD_ID, id), entityType);
    }

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> entityType, int[] spawnEggColors) {
        return register(id, entityType.build(), spawnEggColors);
    }

}

