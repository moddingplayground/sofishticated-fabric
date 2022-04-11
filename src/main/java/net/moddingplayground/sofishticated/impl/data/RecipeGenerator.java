package net.moddingplayground.sofishticated.impl.data;

import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.toymaker.v0.generator.recipe.AbstractRecipeGenerator;
import net.moddingplayground.sofishticated.api.Sofishticated;

import static net.minecraft.recipe.Ingredient.*;
import static net.moddingplayground.sofishticated.api.item.SofishticatedItems.*;

public class RecipeGenerator extends AbstractRecipeGenerator {
    public RecipeGenerator() {
        super(Sofishticated.MOD_ID);
    }

    @Override
    public void generate() {
        this.cookingRecipes(SHRIMP, COOKED_SHRIMP, 0.35f);
    }

    public void cookingRecipes(Item item, Item cooked, float experience) {
        this.cookingRecipe(item, cooked, experience, 200, CookingRecipeSerializer.SMELTING);
        this.cookingRecipe(item, cooked, experience, 100, CookingRecipeSerializer.SMOKING);
        this.cookingRecipe(item, cooked, experience, 600, CookingRecipeSerializer.CAMPFIRE_COOKING);
    }

    public void cookingRecipe(Item item, Item cooked, float experience, int cookingTime, CookingRecipeSerializer<?> serializer) {
        Identifier id = Registry.ITEM.getId(cooked);
        String suffix = serializer == CookingRecipeSerializer.SMELTING ? "" : "_from_%s".formatted(Registry.RECIPE_SERIALIZER.getId(serializer).getPath());
        this.add(id.getPath() + suffix,
            CookingRecipeJsonBuilder.create(ofItems(item), cooked, experience, cookingTime, serializer)
                                    .criterion("has_ingredient", hasItem(item))
        );
    }
}
