package net.moddingplayground.sofishticated.api.tag;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.sofishticated.api.Sofishticated;

public interface SofishticatedItemTags {
    TagKey<Item> FISHES = create("fishes");

    TagKey<Item> ANGLER_FISH_FEEDS = create("angler_fish_feeds");
    TagKey<Item> ANGLER_FISH_TEMPTS = create("angler_fish_tempts");

    private static TagKey<Item> create(String id) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier(Sofishticated.MOD_ID, id));
    }
}
