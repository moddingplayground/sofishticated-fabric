package net.moddingplayground.sofishticated.impl.data.server;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.recipe.CookingRecipeSerializer;

import java.util.function.Consumer;

import static net.moddingplayground.sofishticated.api.item.SofishticatedItems.*;

public final class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataGenerator gen) {
        super(gen);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporterFunction) {
        cookingRecipes(exporterFunction, SHRIMP, COOKED_SHRIMP, 0.35f);
    }

    public void cookingRecipes(Consumer<RecipeJsonProvider> gen, Item item, Item cooked, float experience) {
        offerCookingRecipe(gen, "smelting", CookingRecipeSerializer.SMELTING, 200, item, cooked, experience);
        offerCookingRecipe(gen, "smoking", CookingRecipeSerializer.SMOKING, 100, item, cooked, experience);
        offerCookingRecipe(gen, "campfire_cooking", CookingRecipeSerializer.CAMPFIRE_COOKING, 600, item, cooked, experience);
    }
}
