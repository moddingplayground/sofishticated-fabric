package com.ninni.sofishticated.init;

import com.ninni.sofishticated.Sofishticated;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class SofishticatedItems {
    public static final Item ANGLER_FISH_BUCKET = register("angler_fish_bucket", new EntityBucketItem(SofishticatedEntities.ANGLER_FISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1).group(Sofishticated.ITEM_GROUP)));
    public static final Item ANGLER_FISH = register("angler_fish", new Item(new FabricItemSettings().group(Sofishticated.ITEM_GROUP).food(new FoodComponent.Builder().statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 3 * 20), 1.0f).hunger(3).saturationModifier(0.3F).build())));
    public static final Item PIRANHA_BUCKET = register("piranha_bucket", new EntityBucketItem(SofishticatedEntities.PIRANHA, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1).group(Sofishticated.ITEM_GROUP)));
    public static final Item PIRANHA = register("piranha", new Item(new FabricItemSettings().group(Sofishticated.ITEM_GROUP).food(new FoodComponent.Builder().hunger(2).saturationModifier(0.3F).build())));
    public static final Item SHRIMP_BUCKET = register("shrimp_bucket", new EntityBucketItem(SofishticatedEntities.SHRIMP, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new FabricItemSettings().maxCount(1).group(Sofishticated.ITEM_GROUP)));
    public static final Item SHRIMP = register("shrimp", new Item(new FabricItemSettings().group(Sofishticated.ITEM_GROUP).food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).build())));
    public static final Item COOKED_SHRIMP = register("cooked_shrimp", new Item(new FabricItemSettings().group(Sofishticated.ITEM_GROUP).food(new FoodComponent.Builder().hunger(5).saturationModifier(0.4F).build())));

    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Sofishticated.MOD_ID, id), item);
    }
}


