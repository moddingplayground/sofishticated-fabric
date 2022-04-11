package net.moddingplayground.sofishticated.impl.data;

import net.minecraft.entity.EntityType;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.toymaker.v0.generator.model.item.AbstractItemModelGenerator;
import net.moddingplayground.sofishticated.api.Sofishticated;

import static net.moddingplayground.sofishticated.api.item.SofishticatedItems.*;

public class ItemModelGenerator extends AbstractItemModelGenerator {
    public ItemModelGenerator() {
        super(Sofishticated.MOD_ID);
    }

    @Override
    public void generate() {
        this.generatedItems(SHRIMP, COOKED_SHRIMP, ANGLER_FISH);

        for (EntityType<?> type : Registry.ENTITY_TYPE) {
            Identifier id = Registry.ENTITY_TYPE.getId(type);
            if (id.getNamespace().equals(Sofishticated.MOD_ID)) this.add(SpawnEggItem.forEntity(type), item -> this.spawnEgg());
        }

        for (Item item : Registry.ITEM) {
            Identifier id = Registry.ITEM.getId(item);
            if (id.getNamespace().equals(Sofishticated.MOD_ID)) {
                if (item instanceof EntityBucketItem) this.add(item, this::generatedItem);
            }
        }
    }
}
