package net.moddingplayground.sofishticated.api.entity;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.item.SofishticatedItemGroups;

import java.util.Optional;

public interface SofishticatedEntityType {
    EntityType<ShrimpEntity> SHRIMP = register("shrimp", 0xE73116, 0x780808,
        FabricEntityTypeBuilder.createMob()
                               .entityFactory(ShrimpEntity::new).spawnGroup(SpawnGroup.WATER_CREATURE)
                               .spawnRestriction(SpawnRestriction.Location.IN_WATER, Heightmap.Type.OCEAN_FLOOR, ShrimpEntity::canSpawn)
                               .defaultAttributes(ShrimpEntity::createShrimpAttributes)
                               .dimensions(EntityDimensions.changing(0.6F, 0.4F))
                               .trackRangeBlocks(8)
    );

    @SuppressWarnings("unchecked")
    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> type, int primary, int secondary, SpawnEggFactory egg) {
        EntityType<T> built = type.build();
        Optional.ofNullable(egg).ifPresent(f -> {
            Item.Settings settings = new FabricItemSettings().maxCount(64).group(SofishticatedItemGroups.ALL);
            Item item = f.apply((EntityType<? extends MobEntity>) built, primary, secondary, settings);
            Registry.register(Registry.ITEM,  new Identifier(Sofishticated.MOD_ID, "%s_spawn_egg".formatted(id)), item);
        });
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Sofishticated.MOD_ID, id), built);
    }

    private static <T extends Entity> EntityType<T> register(String id, int primary, int secondary, FabricEntityTypeBuilder<T> entityType) {
        return register(id, entityType, primary, secondary, SpawnEggItem::new);
    }

    @FunctionalInterface interface SpawnEggFactory { SpawnEggItem apply(EntityType<? extends MobEntity> type, int primaryColor, int secondaryColor, Item.Settings settings); }
}
