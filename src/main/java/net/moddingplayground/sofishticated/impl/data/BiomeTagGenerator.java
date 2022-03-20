package net.moddingplayground.sofishticated.impl.data;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;

import static net.moddingplayground.sofishticated.impl.entity.SofishticatedEntityTypeImpl.*;

public class BiomeTagGenerator extends AbstractTagGenerator<Biome> {
    public BiomeTagGenerator(String modId) {
        super(modId, BuiltinRegistries.BIOME);
    }

    @Override
    public void generate() {
        this.add(createSpawnTag(SHRIMP),
            BiomeKeys.RIVER,
            BiomeKeys.DARK_FOREST,
            BiomeKeys.LUSH_CAVES
        );
    }
}
