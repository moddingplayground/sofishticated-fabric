package net.moddingplayground.sofishticated.api.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.entity.variant.ShrimpVariant;

public interface SofishticatedRegistry {
    DefaultedRegistry<ShrimpVariant> SHRIMP_VARIANT = register("shrimp_variant", ShrimpVariant.class, "bloody_mary");

    private static <T> DefaultedRegistry<T> register(String id, Class<T> clazz, String defaultValue) {
        return FabricRegistryBuilder.createDefaulted(clazz, new Identifier(Sofishticated.MOD_ID, id), new Identifier(Sofishticated.MOD_ID, defaultValue))
                                    .attribute(RegistryAttribute.SYNCED)
                                    .buildAndRegister();
    }
}
