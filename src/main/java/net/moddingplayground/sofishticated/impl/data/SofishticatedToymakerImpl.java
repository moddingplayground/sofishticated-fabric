package net.moddingplayground.sofishticated.impl.data;

import net.minecraft.loot.context.LootContextTypes;
import net.moddingplayground.frame.api.toymaker.v0.ToymakerEntrypoint;
import net.moddingplayground.frame.api.toymaker.v0.registry.generator.ItemModelGeneratorStore;
import net.moddingplayground.frame.api.toymaker.v0.registry.generator.LootGeneratorStore;
import net.moddingplayground.frame.api.toymaker.v0.registry.generator.RecipeGeneratorStore;
import net.moddingplayground.frame.api.toymaker.v0.registry.generator.TagGeneratorStore;
import net.moddingplayground.sofishticated.api.Sofishticated;

public final class SofishticatedToymakerImpl implements ToymakerEntrypoint, Sofishticated {
    @Override
    public void onInitializeToymaker() {
        ItemModelGeneratorStore.register(() -> new ItemModelGenerator(MOD_ID));
        LootGeneratorStore.register(() -> new EntityLootTableGenerator(MOD_ID), LootContextTypes.ENTITY);
        RecipeGeneratorStore.register(() -> new RecipeGenerator(MOD_ID));
        TagGeneratorStore.register(() -> new BiomeTagGenerator(MOD_ID));
        TagGeneratorStore.register(() -> new ItemTagGenerator(MOD_ID));
        TagGeneratorStore.register(() -> new EntityTypeTagGenerator(MOD_ID));
        TagGeneratorStore.register(() -> new BlockTagGenerator(MOD_ID));
    }
}
