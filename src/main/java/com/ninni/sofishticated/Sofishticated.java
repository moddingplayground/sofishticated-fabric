package com.ninni.sofishticated;

import com.google.common.reflect.Reflection;
import com.ninni.sofishticated.init.SofishticatedItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class Sofishticated implements ModInitializer {
	public static final String MOD_ID = "sofishticated";
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(Sofishticated.MOD_ID, "item_group"), () -> new ItemStack(Items.COD));

	@SuppressWarnings("UnstableApiUsage")
	@Override
	public void onInitialize() {
		Reflection.initialize(
			SofishticatedItems.class
		);
	}
}