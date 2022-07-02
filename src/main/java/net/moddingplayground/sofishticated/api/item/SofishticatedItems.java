package net.moddingplayground.sofishticated.api.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType;

import java.util.function.UnaryOperator;

public interface SofishticatedItems {
    Item SHRIMP_SPAWN_EGG = registerSpawnEgg(SofishticatedEntityType.SHRIMP, 0xE73116, 0x780808);
    Item ANGLER_FISH_SPAWN_EGG = registerSpawnEgg(SofishticatedEntityType.ANGLER_FISH, 0x372D2A, 0x73EFE8);
    Item SEAHORSE_SPAWN_EGG = registerSpawnEgg(SofishticatedEntityType.SEAHORSE, 0xE88A36, 0xFFFFFF);

    Item SHRIMP_BUCKET = registerVariantBucket("shrimp_bucket", SofishticatedEntityType.SHRIMP);
    Item ANGLER_FISH_BUCKET = registerVariantBucket("angler_fish_bucket", SofishticatedEntityType.ANGLER_FISH);
    Item SEAHORSE_BUCKET = registerVariantBucket("seahorse_bucket", SofishticatedEntityType.SEAHORSE);

    Item SHRIMP = registerFood("shrimp", 3, 0.3F);
    Item COOKED_SHRIMP = registerFood("cooked_shrimp", 5, 0.4F);

    Item ANGLER_FISH = registerFood("angler_fish", 3, 0.3F, food -> food.statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 3 * 20), 1.0F).alwaysEdible());

    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Sofishticated.MOD_ID, id), item);
    }

    private static Item register(String id, UnaryOperator<FabricItemSettings> item) {
        return register(id, new Item(item.apply(new FabricItemSettings().group(SofishticatedItemGroups.ALL))));
    }

    private static Item registerSpawnEgg(EntityType<? extends MobEntity> type, int primary, int secondary) {
        Identifier id = Registry.ENTITY_TYPE.getId(type);
        return register("%s_spawn_egg".formatted(id.getPath()), new SpawnEggItem(type, primary, secondary, new FabricItemSettings().group(SofishticatedItemGroups.ALL)));
    }

    private static Item registerVariantBucket(String id, EntityType<?> type) {
        return register(id, new VariantEntityBucketItem(type, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1).group(SofishticatedItemGroups.ALL)));
    }

    private static Item registerFood(String id, int hunger, float saturationModifier, UnaryOperator<FoodComponent.Builder> food) {
        return registerFood(id, f -> food.apply(f).hunger(hunger).saturationModifier(saturationModifier));
    }

    private static Item registerFood(String id, int hunger, float saturationModifier) {
        return registerFood(id, hunger, saturationModifier, UnaryOperator.identity());
    }

    private static Item registerFood(String id, UnaryOperator<FoodComponent.Builder> food) {
        return register(id, settings -> settings.food(food.apply(new FoodComponent.Builder()).build()));
    }
}
