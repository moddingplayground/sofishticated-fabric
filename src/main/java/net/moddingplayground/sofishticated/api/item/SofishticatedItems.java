package net.moddingplayground.sofishticated.api.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public interface SofishticatedItems {
    Item SHRIMP = food("shrimp", food -> food.hunger(3).saturationModifier(0.3F));
    Item COOKED_SHRIMP = food("cooked_shrimp", food -> food.hunger(5).saturationModifier(0.4F));
    Item SHRIMP_BUCKET = variantEntityBucket("shrimp_bucket", SofishticatedEntityType.SHRIMP);

    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Sofishticated.MOD_ID, id), item);
    }

    private static Item register(String id, Function<FabricItemSettings, FabricItemSettings> item) {
        return register(id, new Item(item.apply(new FabricItemSettings().group(SofishticatedItemGroups.ALL))));
    }

    private static Item food(String id, UnaryOperator<FoodComponent.Builder> food) {
        return register(id, settings -> settings.food(food.apply(new FoodComponent.Builder()).build()));
    }

    static Item variantEntityBucket(String id, EntityType<?> type) {
        return register(id, new VariantEntityBucketItem(type, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1).group(SofishticatedItemGroups.ALL)));
    }
}
