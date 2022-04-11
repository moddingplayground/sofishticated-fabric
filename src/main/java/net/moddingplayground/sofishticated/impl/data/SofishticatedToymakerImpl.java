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
        ItemModelGeneratorStore.register(ItemModelGenerator::new);
        LootGeneratorStore.register(EntityLootTableGenerator::new, LootContextTypes.ENTITY);
        RecipeGeneratorStore.register(RecipeGenerator::new);
        TagGeneratorStore.register(BiomeTagGenerator::new);
        TagGeneratorStore.register(ItemTagGenerator::new);
        TagGeneratorStore.register(EntityTypeTagGenerator::new);
        TagGeneratorStore.register(BlockTagGenerator::new);
        TagGeneratorStore.register(ShrimpVariantTagGenerator::new);
    }
}
