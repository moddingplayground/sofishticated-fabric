package com.ninni.sofishticated;

import com.google.common.reflect.Reflection;
import com.ninni.sofishticated.init.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Sofishticated implements ModInitializer {
    public static final String MOD_ID = "sofishticated";
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(Sofishticated.MOD_ID, "item_group"), () -> new ItemStack(SofishticatedItems.ANGLER_FISH));

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onInitialize() {
        Reflection.initialize(
            SofishticatedEnchantments.class,
            SofishticatedStatusEffects.class,
            SofishticatedEntities.class,
            SofishticatedSoundEvents.class,
            SofishticatedItems.class
        );
    }
}
