package net.moddingplayground.sofishticated.impl.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tag.ItemTags;
import net.moddingplayground.sofishticated.api.tag.SofishticatedItemTags;

import static net.minecraft.item.Items.*;
import static net.moddingplayground.sofishticated.api.item.SofishticatedItems.*;

class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ItemTagProvider(FabricDataGenerator gen, BlockTagProvider blockTagProvider) {
        super(gen, blockTagProvider);
    }

    @Override
    protected void generateTags() {
        this.getOrCreateTagBuilder(SofishticatedItemTags.FISHES).add(
            SHRIMP,
            COOKED_SHRIMP,
            ANGLER_FISH
        );
        this.getOrCreateTagBuilder(ItemTags.FISHES).addTag(SofishticatedItemTags.FISHES);

        this.getOrCreateTagBuilder(SofishticatedItemTags.SHRIMP_BREEDS).add(SEAGRASS);

        this.getOrCreateTagBuilder(SofishticatedItemTags.ANGLER_FISH_FEEDS).add(
            GLOW_INK_SAC,
            GLOWSTONE_DUST,
            GLOW_LICHEN
        );

        this.getOrCreateTagBuilder(SofishticatedItemTags.ANGLER_FISH_TEMPTS).add(
            GLOW_BERRIES, GLOWSTONE
        ).addTag(SofishticatedItemTags.ANGLER_FISH_FEEDS);
    }
}
