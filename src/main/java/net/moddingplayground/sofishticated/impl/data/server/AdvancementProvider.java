package net.moddingplayground.sofishticated.impl.data.server;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.item.SofishticatedItems;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@SuppressWarnings("UnstableApiUsage")
public final class AdvancementProvider extends FabricAdvancementProvider {
    public AdvancementProvider(FabricDataGenerator gen) {
        super(gen);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
    }

    public void generateAdvancements(Consumer<Advancement.Builder> consumer) {
        // obtain shrimp bucket
        create(consumer, "obtain_shrimp_bucket", new ItemStack(SofishticatedItems.SHRIMP_BUCKET), true, builder -> {
            builder.parent(new Identifier("husbandry/tactical_fishing"));
            builder.criterion("obtain_shrimp_bucket", InventoryChangedCriterion.Conditions.items(SofishticatedItems.SHRIMP_BUCKET));
            return builder;
        });
    }

    public Advancement.Builder create(Consumer<Advancement.Builder> consumer, String id, ItemStack icon, boolean visible, UnaryOperator<Advancement.Builder> processor
    ) {
        Advancement.Builder builder = processor.apply(
            CustomAdvancementBuilder.create(new Identifier(Sofishticated.MOD_ID, id))
                                    .display(icon,
                                   Text.translatable("advancements.%s.%s.title".formatted(Sofishticated.MOD_ID, id)),
                                   Text.translatable("advancements.%s.%s.description".formatted(Sofishticated.MOD_ID, id)),
                                   null, AdvancementFrame.TASK, visible, visible, false
                               )
        );
        consumer.accept(builder);
        return builder;
    }

    @Override
    public void run(DataWriter writer) throws IOException {
        Set<Identifier> identifiers = Sets.newHashSet();
        Set<Advancement.Builder> advancements = Sets.newHashSet();

        generateAdvancements(advancements::add);

        for (Advancement.Builder advancement : advancements) {
            if (advancement instanceof CustomAdvancementBuilder builder) {
                if (!identifiers.add(builder.getId())) {
                    throw new IllegalStateException("Duplicate advancement " + builder.getId());
                }

                JsonObject advancementJson = builder.toJson();
                ConditionJsonProvider.write(advancementJson, FabricDataGenHelper.consumeConditions(builder));

                DataProvider.writeToPath(writer, advancementJson, this.getOutputPath(builder));
            }
        }
    }

    public Path getOutputPath(CustomAdvancementBuilder advancement) {
        return dataGenerator.getOutput().resolve("data/%s/advancements/%s.json".formatted(advancement.getId().getNamespace(), advancement.getId().getPath()));
    }

    public static class CustomAdvancementBuilder extends Advancement.Builder {
        private final Identifier id;

        protected CustomAdvancementBuilder(Identifier id) {
            super();
            this.id = id;
        }

        public static Advancement.Builder create(Identifier id) {
            return new CustomAdvancementBuilder(id);
        }

        public Identifier getId() {
            return this.id;
        }

        @Override
        public boolean findParent(Function<Identifier, Advancement> parentProvider) {
            super.findParent(parentProvider);
            return true;
        }
    }
}
