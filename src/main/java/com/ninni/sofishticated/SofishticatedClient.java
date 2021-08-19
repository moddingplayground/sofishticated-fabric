package com.ninni.sofishticated;

import com.google.common.collect.ImmutableMap;
import com.ninni.sofishticated.client.client.init.SofishticatedEntityModelLayers;
import com.ninni.sofishticated.client.model.entity.AnglerFishEntityModel;
import com.ninni.sofishticated.client.model.entity.MantaRayEntityModel;
import com.ninni.sofishticated.client.model.entity.SunfishEntityModel;
import com.ninni.sofishticated.client.render.AnglerFishEntityRenderer;
import com.ninni.sofishticated.client.render.MantaRayEntityRenderer;
import com.ninni.sofishticated.client.render.SunfishEntityRenderer;
import com.ninni.sofishticated.init.SofishticatedEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class SofishticatedClient implements ClientModInitializer {

	@Override
	@SuppressWarnings({"UnstableApiUsage", "deprecation"})
	public void onInitializeClient() {
		EntityRendererRegistry errInstance = EntityRendererRegistry.INSTANCE;
		errInstance.register(SofishticatedEntities.ANGLER_FISH, AnglerFishEntityRenderer::new);
		errInstance.register(SofishticatedEntities.SUNFISH, SunfishEntityRenderer::new);
		errInstance.register(SofishticatedEntities.MANTA_RAY, MantaRayEntityRenderer::new);

		ImmutableMap.<EntityModelLayer, EntityModelLayerRegistry.TexturedModelDataProvider>of(
			SofishticatedEntityModelLayers.ANGLER_FISH, AnglerFishEntityModel::getTexturedModelData,
			SofishticatedEntityModelLayers.SUNFISH, SunfishEntityModel::getTexturedModelData,
			SofishticatedEntityModelLayers.MANTA_RAY, () -> MantaRayEntityModel.getTexturedModelData(Dilation.NONE),
			SofishticatedEntityModelLayers.MANTA_RAY_SADDLE, () -> MantaRayEntityModel.getTexturedModelData(new Dilation(0.5F))
		).forEach(EntityModelLayerRegistry::registerModelLayer);
	}
}
