package net.moddingplayground.sofishticated.api.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.moddingplayground.sofishticated.api.Sofishticated;

public interface SofishticatedItemGroups {
    ItemGroup ALL = FabricItemGroupBuilder.build(new Identifier(Sofishticated.MOD_ID, "item_group"), () -> new ItemStack(SofishticatedItems.SHRIMP_BUCKET));
}
