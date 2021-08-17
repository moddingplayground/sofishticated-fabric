package com.ninni.sofishticated;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class SofishticatedClient implements ClientModInitializer {

	@Override
	@SuppressWarnings({"UnstableApiUsage", "deprecation"})
	public void onInitializeClient() {

		EntityRendererRegistry errInstance = EntityRendererRegistry.INSTANCE;

		ImmutableMap.<EntityModelLayer, EntityModelLayerRegistry.TexturedModelDataProvider>of(
		).forEach(EntityModelLayerRegistry::registerModelLayer);
	}
}
