package net.moddingplayground.sofishticated.impl.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.impl.data.client.ModelProvider;
import net.moddingplayground.sofishticated.impl.data.server.AdvancementProvider;
import net.moddingplayground.sofishticated.impl.data.server.BiomeTagProvider;
import net.moddingplayground.sofishticated.impl.data.server.BlockTagProvider;
import net.moddingplayground.sofishticated.impl.data.server.EntityLootTableProvider;
import net.moddingplayground.sofishticated.impl.data.server.EntityTypeTagProvider;
import net.moddingplayground.sofishticated.impl.data.server.ItemTagProvider;
import net.moddingplayground.sofishticated.impl.data.server.RecipeProvider;
import net.moddingplayground.sofishticated.impl.data.server.ShrimpVariantTagProvider;

public final class SofishticatedDataGeneratorImpl implements Sofishticated, DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        gen.addProvider(ModelProvider::new);
        gen.addProvider(RecipeProvider::new);
        gen.addProvider(EntityLootTableProvider::new);
        gen.addProvider(AdvancementProvider::new);

        BlockTagProvider blockTagProvider = gen.addProvider(BlockTagProvider::new);
        gen.addProvider(g -> new ItemTagProvider(g, blockTagProvider));
        gen.addProvider(EntityTypeTagProvider::new);
        gen.addProvider(BiomeTagProvider::new);
        gen.addProvider(ShrimpVariantTagProvider::new);
    }
}
