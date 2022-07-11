package net.moddingplayground.sofishticated.impl.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.FurnaceSmeltLootFunction;
import net.minecraft.predicate.entity.EntityFlagsPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.util.Identifier;
import net.moddingplayground.sofishticated.api.item.SofishticatedItems;

import java.util.function.BiConsumer;

import static net.minecraft.item.Items.*;
import static net.minecraft.loot.context.LootContext.EntityTarget.*;
import static net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType.*;

class EntityLootTableProvider extends SimpleFabricLootTableProvider {
    private static final EntityPredicate.Builder NEEDS_ENTITY_ON_FIRE = EntityPredicate.Builder.create().flags(EntityFlagsPredicate.Builder.create().onFire(true).build());

    public EntityLootTableProvider(FabricDataGenerator gen) {
        super(gen, LootContextTypes.ENTITY);
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> gen) {
        this.register(SHRIMP, gen, LootTable.builder().pool(LootPool.builder().with(ItemEntry.builder(SofishticatedItems.SHRIMP).apply(FurnaceSmeltLootFunction.builder().conditionally(EntityPropertiesLootCondition.builder(THIS, NEEDS_ENTITY_ON_FIRE))))).pool(LootPool.builder().with(ItemEntry.builder(BONE_MEAL)).conditionally(RandomChanceLootCondition.builder(0.05F))));

        this.register(ANGLER_FISH, gen, LootTable.builder().pool(LootPool.builder().with(ItemEntry.builder(SofishticatedItems.ANGLER_FISH))));

        this.register(SEAHORSE, gen, LootTable.builder().pool(LootPool.builder().with(ItemEntry.builder(BONE_MEAL)).conditionally(RandomChanceLootCondition.builder(0.05F))));
    }

    private void register(EntityType<?> type, BiConsumer<Identifier, LootTable.Builder> gen, LootTable.Builder builder) {
        gen.accept(type.getLootTableId(), builder);
    }
}
