package net.moddingplayground.sofishticated.impl.data.server;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType;

import static net.minecraft.world.biome.BiomeKeys.*;
import static net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType.*;

public final class BiomeTagProvider extends FabricTagProvider.DynamicRegistryTagProvider<Biome> {
    public BiomeTagProvider(FabricDataGenerator gen) {
        super(gen, Registry.BIOME_KEY);
    }

    @Override
    protected void generateTags() {
        this.getOrCreateTagBuilder(createSpawnTag(SofishticatedEntityType.SHRIMP)).add(RIVER, DARK_FOREST, LUSH_CAVES);
        this.getOrCreateTagBuilder(createSpawnTag(SofishticatedEntityType.ANGLER_FISH)).forceAddTag(BiomeTags.IS_DEEP_OCEAN);
        this.getOrCreateTagBuilder(createSpawnTag(SofishticatedEntityType.SEAHORSE)).add(WARM_OCEAN);
    }
}
