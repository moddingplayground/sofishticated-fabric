package net.moddingplayground.sofishticated.impl.data.client;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.Models;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.toymaker.v0.model.uploader.ItemModelUploader;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.item.SofishticatedItems;

import java.util.List;
import java.util.stream.StreamSupport;

public final class ModelProvider extends FabricModelProvider {
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
        ItemModelUploader uploader = ItemModelUploader.of(gen);
        uploader.register(Models.GENERATED,
            SofishticatedItems.SHRIMP,
            SofishticatedItems.COOKED_SHRIMP,
            SofishticatedItems.ANGLER_FISH
        );

        List<Item> sofishticated = Registry.ITEM.stream().filter(this::isSofishticated).toList();
        sofishticated.stream().filter(EntityBucketItem.class::isInstance).forEach(item -> uploader.register(Models.GENERATED, item));
    }

    private boolean isSofishticated(Item item) {
        Identifier id = Registry.ITEM.getId(item);
        return id.getNamespace().equals(Sofishticated.MOD_ID);
    }
}
