package net.moddingplayground.sofishticated.impl.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.Models;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.FurnaceSmeltLootFunction;
import net.minecraft.predicate.entity.EntityFlagsPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType;
import net.moddingplayground.sofishticated.api.entity.variant.ShrimpVariant;
import net.moddingplayground.sofishticated.api.item.SofishticatedItems;
import net.moddingplayground.sofishticated.api.registry.SofishticatedRegistry;
import net.moddingplayground.sofishticated.api.tag.SofishticatedBlockTags;
import net.moddingplayground.sofishticated.api.tag.SofishticatedItemTags;
import net.moddingplayground.sofishticated.api.tag.SofishticatedShrimpVariantTags;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

import static net.minecraft.loot.context.LootContext.EntityTarget.*;
import static net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType.*;
import static net.moddingplayground.sofishticated.api.entity.variant.ShrimpVariants.*;

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

    /* Models */

    private static class ModelProvider extends FabricModelProvider {
        public ModelProvider(FabricDataGenerator gen) {
            super(gen);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator gen) {
            StreamSupport.stream(SpawnEggItem.getAll().spliterator(), false)
                         .filter(this::isSofishticated)
                         .forEach(item -> gen.registerParentedItemModel(item, ModelIds.getMinecraftNamespacedItem("template_spawn_egg")));
        }

        @Override
        public void generateItemModels(ItemModelGenerator gen) {
            gen.register(SofishticatedItems.SHRIMP, Models.GENERATED);
            gen.register(SofishticatedItems.COOKED_SHRIMP, Models.GENERATED);
            gen.register(SofishticatedItems.ANGLER_FISH, Models.GENERATED);

            Registry.ITEM.stream()
                         .filter(this::isSofishticated)
                         .forEach(item -> {
                             if (item instanceof EntityBucketItem) gen.register(item, Models.GENERATED);
                         });
        }

        private boolean isSofishticated(Item item) {
            Identifier id = Registry.ITEM.getId(item);
            return id.getNamespace().equals(Sofishticated.MOD_ID);
        }
    }

    /* Recipes */

    private static class RecipeProvider extends FabricRecipeProvider {
        public RecipeProvider(FabricDataGenerator gen) {
            super(gen);
        }

        @Override
        protected void generateRecipes(Consumer<RecipeJsonProvider> gen) {
            cookingRecipes(gen, SofishticatedItems.SHRIMP, SofishticatedItems.COOKED_SHRIMP, 0.35f);
        }

        public void cookingRecipes(Consumer<RecipeJsonProvider> gen, Item item, Item cooked, float experience) {
            offerCookingRecipe(gen, "smelting", CookingRecipeSerializer.SMELTING, 200, item, cooked, experience);
            offerCookingRecipe(gen, "smoking", CookingRecipeSerializer.SMOKING, 100, item, cooked, experience);
            offerCookingRecipe(gen, "campfire_cooking", CookingRecipeSerializer.CAMPFIRE_COOKING, 600, item, cooked, experience);
        }
    }

    /* Loot Tables */

    private static class EntityLootTableProvider extends SimpleFabricLootTableProvider {
        private static final EntityPredicate.Builder NEEDS_ENTITY_ON_FIRE = EntityPredicate.Builder.create()
                                                                                                   .flags(
                                                                                                       EntityFlagsPredicate.Builder.create()
                                                                                                                                   .onFire(true)
                                                                                                                                   .build()
                                                                                                   );

        public EntityLootTableProvider(FabricDataGenerator gen) {
            super(gen, LootContextTypes.ENTITY);
        }

        @Override
        public void accept(BiConsumer<Identifier, LootTable.Builder> gen) {
            this.register(SofishticatedEntityType.SHRIMP, gen,
                LootTable.builder()
                         .pool(LootPool.builder()
                                       .with(
                                           ItemEntry.builder(SofishticatedItems.SHRIMP)
                                                    .apply(FurnaceSmeltLootFunction
                                                        .builder()
                                                        .conditionally(EntityPropertiesLootCondition.builder(THIS, NEEDS_ENTITY_ON_FIRE)))
                                       )
                         )
                         .pool(LootPool.builder()
                                       .with(ItemEntry.builder(Items.BONE_MEAL))
                                       .conditionally(RandomChanceLootCondition.builder(0.05F))
                         )
            );

            this.register(SofishticatedEntityType.ANGLER_FISH, gen,
                LootTable.builder().pool(LootPool.builder()
                                                 .with(ItemEntry.builder(SofishticatedItems.ANGLER_FISH))
                )
            );

            this.register(SofishticatedEntityType.SEAHORSE, gen,
                LootTable.builder().pool(LootPool.builder()
                                                 .with(ItemEntry.builder(Items.BONE_MEAL))
                                                 .conditionally(RandomChanceLootCondition.builder(0.05F))
                )
            );
        }

        private void register(EntityType<?> type, BiConsumer<Identifier, LootTable.Builder> gen, LootTable.Builder builder) {
            gen.accept(type.getLootTableId(), builder);
        }
    }

    /* Tags */

    private static class BlockTagProvider extends FabricTagProvider.BlockTagProvider {
        public BlockTagProvider(FabricDataGenerator gen) {
            super(gen);
        }

        @Override
        protected void generateTags() {
            this.getOrCreateTagBuilder(SofishticatedBlockTags.MOIST_BLOCKS).add(
                Blocks.CLAY,
                Blocks.WET_SPONGE
            );
        }
    }

    private static class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
        public ItemTagProvider(FabricDataGenerator gen, BlockTagProvider blockTagProvider) {
            super(gen, blockTagProvider);
        }

        @Override
        protected void generateTags() {
            this.getOrCreateTagBuilder(SofishticatedItemTags.FISHES).add(
                SofishticatedItems.SHRIMP,
                SofishticatedItems.COOKED_SHRIMP,
                SofishticatedItems.ANGLER_FISH
            );
            this.getOrCreateTagBuilder(ItemTags.FISHES).addTag(SofishticatedItemTags.FISHES);

            this.getOrCreateTagBuilder(SofishticatedItemTags.SHRIMP_BREEDS).add(Items.SEAGRASS);

            this.getOrCreateTagBuilder(SofishticatedItemTags.ANGLER_FISH_FEEDS).add(
                Items.GLOW_INK_SAC,
                Items.GLOWSTONE_DUST,
                Items.GLOW_LICHEN
            );
            this.getOrCreateTagBuilder(SofishticatedItemTags.ANGLER_FISH_TEMPTS)
                .addTag(SofishticatedItemTags.ANGLER_FISH_FEEDS)
                .add(
                    Items.GLOW_BERRIES,
                    Items.GLOWSTONE
                );
        }
    }

    private static class EntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
        public EntityTypeTagProvider(FabricDataGenerator gen) {
            super(gen);
        }

        @Override
        protected void generateTags() {
            this.getOrCreateTagBuilder(EntityTypeTags.AXOLOTL_HUNT_TARGETS).add(SofishticatedEntityType.SHRIMP);
        }
    }

    private static class BiomeTagProvider extends FabricTagProvider.DynamicRegistryTagProvider<Biome> {
        protected BiomeTagProvider(FabricDataGenerator gen) {
            super(gen, Registry.BIOME_KEY);
        }

        @Override
        protected void generateTags() {
            this.getOrCreateTagBuilder(createSpawnTag(SofishticatedEntityType.SHRIMP)).add(
                BiomeKeys.RIVER,
                BiomeKeys.DARK_FOREST,
                BiomeKeys.LUSH_CAVES
            );

            this.getOrCreateTagBuilder(createSpawnTag(SofishticatedEntityType.ANGLER_FISH)).forceAddTag(BiomeTags.IS_DEEP_OCEAN);
            this.getOrCreateTagBuilder(createSpawnTag(SofishticatedEntityType.SEAHORSE)).add(BiomeKeys.WARM_OCEAN);
        }
    }

    private static class ShrimpVariantTagProvider extends FabricTagProvider<ShrimpVariant> {
        public ShrimpVariantTagProvider(FabricDataGenerator gen) {
            super(gen, SofishticatedRegistry.SHRIMP_VARIANT);
        }

        @Override
        protected void generateTags() {
            this.getOrCreateTagBuilder(SofishticatedShrimpVariantTags.NATURAL_SPAWN_VARIANTS).add(
                SUPER_WILD_TIGER,
                BABAULTI_GREEN,
                SULAWESI_TIGER_CARDINAL
            );
        }
    }
}
