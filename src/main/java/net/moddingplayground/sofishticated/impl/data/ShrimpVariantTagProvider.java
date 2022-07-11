package net.moddingplayground.sofishticated.impl.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.moddingplayground.sofishticated.api.entity.variant.ShrimpVariant;
import net.moddingplayground.sofishticated.api.registry.SofishticatedRegistry;
import net.moddingplayground.sofishticated.api.tag.SofishticatedShrimpVariantTags;

import static net.moddingplayground.sofishticated.api.entity.variant.ShrimpVariants.*;

class ShrimpVariantTagProvider extends FabricTagProvider<ShrimpVariant> {
    public ShrimpVariantTagProvider(FabricDataGenerator gen) {
        super(gen, SofishticatedRegistry.SHRIMP_VARIANT);
    }

    @Override
    protected void generateTags() {
        this.getOrCreateTagBuilder(SofishticatedShrimpVariantTags.NATURAL_SPAWN_VARIANTS).add(
            SUPER_WILD_TIGER,
            BABAULTI_GREEN,
            SULAWESI_TIGER_CARDINAL
        );
    }
}
