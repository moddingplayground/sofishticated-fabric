package net.moddingplayground.sofishticated.api.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType;

import java.util.function.UnaryOperator;

public interface SofishticatedItems {
    Item SHRIMP_BUCKET = variantEntityBucket("shrimp_bucket", SofishticatedEntityType.SHRIMP);
    Item ANGLER_FISH_BUCKET = variantEntityBucket("angler_fish_bucket", SofishticatedEntityType.ANGLER_FISH);
    Item SEAHORSE_BUCKET = variantEntityBucket("seahorse_bucket", SofishticatedEntityType.SEAHORSE);

    Item SHRIMP = food("shrimp", 3, 0.3F);
    Item COOKED_SHRIMP = food("cooked_shrimp", 5, 0.4F);

    Item ANGLER_FISH = food("angler_fish", 3, 0.3F, food -> food.statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 3 * 20), 1.0F).alwaysEdible());

    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Sofishticated.MOD_ID, id), item);
    }

    private static Item register(String id, UnaryOperator<FabricItemSettings> item) {
        return register(id, new Item(item.apply(new FabricItemSettings().group(SofishticatedItemGroups.ALL))));
    }

    private static Item food(String id, int hunger, float saturationModifier, UnaryOperator<FoodComponent.Builder> food) {
        return food(id, f -> food.apply(f).hunger(hunger).saturationModifier(saturationModifier));
    }

    private static Item food(String id, int hunger, float saturationModifier) {
        return food(id, hunger, saturationModifier, UnaryOperator.identity());
    }

    private static Item food(String id, UnaryOperator<FoodComponent.Builder> food) {
        return register(id, settings -> settings.food(food.apply(new FoodComponent.Builder()).build()));
    }

    private static Item variantEntityBucket(String id, EntityType<?> type) {
        return register(id, new VariantEntityBucketItem(type, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1).group(SofishticatedItemGroups.ALL)));
    }
}
