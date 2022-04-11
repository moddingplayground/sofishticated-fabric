package net.moddingplayground.sofishticated.impl.data;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.Biome;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;
import net.moddingplayground.sofishticated.api.Sofishticated;

import static net.minecraft.world.biome.BiomeKeys.*;
import static net.moddingplayground.sofishticated.impl.entity.SofishticatedEntityTypeImpl.*;

public class BiomeTagGenerator extends AbstractTagGenerator<Biome> {
    public BiomeTagGenerator() {
        super(Sofishticated.MOD_ID, BuiltinRegistries.BIOME);
    }

    @Override
    public void generate() {
        this.add(createSpawnTag(SHRIMP),
            RIVER,
            DARK_FOREST,
            LUSH_CAVES
        );

        this.add(createSpawnTag(ANGLER_FISH),
            DEEP_FROZEN_OCEAN,
            DEEP_OCEAN
        );
    }
}
