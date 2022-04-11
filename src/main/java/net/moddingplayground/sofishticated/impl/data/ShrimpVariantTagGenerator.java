package net.moddingplayground.sofishticated.impl.data;

import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.entity.variant.ShrimpVariant;
import net.moddingplayground.sofishticated.api.registry.SofishticatedRegistry;
import net.moddingplayground.sofishticated.api.tag.SofishticatedShrimpVariantTags;

import static net.moddingplayground.sofishticated.api.entity.variant.ShrimpVariants.*;

public class ShrimpVariantTagGenerator extends AbstractTagGenerator<ShrimpVariant> {
    public ShrimpVariantTagGenerator() {
        super(Sofishticated.MOD_ID, SofishticatedRegistry.SHRIMP_VARIANT);
    }

    @Override
    public void generate() {
        this.add(SofishticatedShrimpVariantTags.NATURAL_SPAWN_VARIANTS,
            SUPER_WILD_TIGER,
            BABAULTI_GREEN,
            SULAWESI_TIGER_CARDINAL
        );
    }
}
