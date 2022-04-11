package net.moddingplayground.sofishticated.impl.data;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.FurnaceSmeltLootFunction;
import net.minecraft.predicate.entity.EntityFlagsPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.toymaker.v0.generator.loot.AbstractLootTableGenerator;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType;
import net.moddingplayground.sofishticated.api.item.SofishticatedItems;

import static net.minecraft.loot.context.LootContext.EntityTarget.*;

public class EntityLootTableGenerator extends AbstractLootTableGenerator<EntityType<?>> {
    private static final EntityPredicate.Builder NEEDS_ENTITY_ON_FIRE = EntityPredicate.Builder.create().flags(EntityFlagsPredicate.Builder.create().onFire(true).build());

    public EntityLootTableGenerator() {
        super(Sofishticated.MOD_ID);
    }

    @Override
    public void generate() {
        this.add(SofishticatedEntityType.SHRIMP,
            LootTable.builder()
                     .pool(pool()
                         .with(
                             ItemEntry.builder(SofishticatedItems.SHRIMP)
                                      .apply(FurnaceSmeltLootFunction
                                          .builder()
                                          .conditionally(EntityPropertiesLootCondition.builder(THIS, NEEDS_ENTITY_ON_FIRE)))
                         )
                     )
                     .pool(pool().with(ItemEntry.builder(Items.BONE_MEAL)).conditionally(chance(0.05F)))
        );

        this.add(SofishticatedEntityType.ANGLER_FISH, LootTable.builder().pool(pool().with(ItemEntry.builder(SofishticatedItems.ANGLER_FISH))));
    }

    @Override
    public Registry<EntityType<?>> getRegistry() {
        return Registry.ENTITY_TYPE;
    }
}
