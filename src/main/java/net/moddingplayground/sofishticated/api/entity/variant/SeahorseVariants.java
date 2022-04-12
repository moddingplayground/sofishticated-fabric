package net.moddingplayground.sofishticated.api.entity.variant;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.registry.SofishticatedRegistry;

public interface SeahorseVariants {
    SeahorseVariant BLACK = register("black");
    SeahorseVariant BLUE = register("blue");
    SeahorseVariant ORANGE = register("orange");
    SeahorseVariant PURPLE = register("purple");
    SeahorseVariant WHITE = register("white");
    SeahorseVariant YELLOW = register("yellow");

    private static SeahorseVariant register(String id) {
        return Registry.register(SofishticatedRegistry.SEAHORSE_VARIANT, new Identifier(Sofishticated.MOD_ID, id), new SeahorseVariant());
    }
}
