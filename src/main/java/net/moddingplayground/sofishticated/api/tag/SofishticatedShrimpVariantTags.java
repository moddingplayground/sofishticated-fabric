package net.moddingplayground.sofishticated.api.tag;

import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.entity.variant.ShrimpVariant;
import net.moddingplayground.sofishticated.api.registry.SofishticatedRegistry;

public interface SofishticatedShrimpVariantTags {
    TagKey<ShrimpVariant> NATURAL_SPAWN_VARIANTS = create("natural_spawn_variants");

    private static TagKey<ShrimpVariant> create(String id) {
        return TagKey.of(SofishticatedRegistry.SHRIMP_VARIANT.getKey(), new Identifier(Sofishticated.MOD_ID, id));
    }
}
