package net.moddingplayground.sofishticated.api.tag;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.sofishticated.api.Sofishticated;

public interface SofishticatedItemTags {
    TagKey<Item> FISHES = register("fishes");

    private static TagKey<Item> register(String id) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier(Sofishticated.MOD_ID, id));
    }
}
