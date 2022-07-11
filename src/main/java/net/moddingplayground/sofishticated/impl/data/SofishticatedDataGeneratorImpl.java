package net.moddingplayground.sofishticated.impl.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.moddingplayground.sofishticated.api.Sofishticated;

public final class SofishticatedDataGeneratorImpl implements Sofishticated, DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        gen.addProvider(ModelProvider::new);
        gen.addProvider(RecipeProvider::new);
        gen.addProvider(EntityLootTableProvider::new);

        BlockTagProvider blockTagProvider = gen.addProvider(BlockTagProvider::new);
        gen.addProvider(g -> new ItemTagProvider(g, blockTagProvider));
        gen.addProvider(EntityTypeTagProvider::new);
        gen.addProvider(BiomeTagProvider::new);
        gen.addProvider(ShrimpVariantTagProvider::new);
    }
}
