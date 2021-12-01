package com.ninni.sofishticated;

import com.google.common.collect.ImmutableMap;
import com.ninni.sofishticated.client.init.SofishticatedEntityModelLayers;
import com.ninni.sofishticated.client.model.entity.*;
import com.ninni.sofishticated.client.render.*;
import com.ninni.sofishticated.init.SofishticatedBlocks;
import com.ninni.sofishticated.init.SofishticatedEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class SofishticatedClient implements ClientModInitializer {

    @Override
    @SuppressWarnings({ "deprecation" })
    public void onInitializeClient() {
        EntityRendererRegistry erri = EntityRendererRegistry.INSTANCE;
        erri.register(SofishticatedEntities.ANGLER_FISH, AnglerFishEntityRenderer::new);
        erri.register(SofishticatedEntities.SUNFISH, SunfishEntityRenderer::new);
        erri.register(SofishticatedEntities.MANTA_RAY, MantaRayEntityRenderer::new);
        erri.register(SofishticatedEntities.PIRANHA, PiranhaEntityRenderer::new);
        erri.register(SofishticatedEntities.SHRIMP, ShrimpEntityRenderer::new);
        erri.register(SofishticatedEntities.REEF_SHARK, ReefSharkEntityRenderer::new);
        erri.register(SofishticatedEntities.BUTTERFLY_FISH, ButterflyFishEntityRenderer::new);
        erri.register(SofishticatedEntities.GAR, GarEntityRenderer::new);

        new ImmutableMap.Builder<EntityModelLayer, EntityModelLayerRegistry.TexturedModelDataProvider>()
            .put(SofishticatedEntityModelLayers.ANGLER_FISH, AnglerFishEntityModel::getTexturedModelData)
            .put(SofishticatedEntityModelLayers.SUNFISH, SunfishEntityModel::getTexturedModelData)
            .put(SofishticatedEntityModelLayers.MANTA_RAY, () -> MantaRayEntityModel.getTexturedModelData(Dilation.NONE))
            .put(SofishticatedEntityModelLayers.MANTA_RAY_SADDLE, () -> MantaRayEntityModel.getTexturedModelData(new Dilation(0.5F)))
            .put(SofishticatedEntityModelLayers.PIRANHA, PiranhaEntityModel::getTexturedModelData)
            .put(SofishticatedEntityModelLayers.SHRIMP, ShrimpEntityModel::getTexturedModelData)
            .put(SofishticatedEntityModelLayers.REEF_SHARK, ReefSharkEntityModel::getTexturedModelData)
            .put(SofishticatedEntityModelLayers.BUTTERFLY_FISH, ButterflyFishEntityModel::getTexturedModelData)
            .put(SofishticatedEntityModelLayers.GAR, GarEntityModel::getTexturedModelData)
        .build().forEach(EntityModelLayerRegistry::registerModelLayer);

        BlockRenderLayerMap brlm = BlockRenderLayerMap.INSTANCE;
        brlm.putBlocks(
            RenderLayer.getTranslucent(),

            SofishticatedBlocks.CLEAR_GLASS,
            SofishticatedBlocks.CLEAR_GLASS_PANE,
            SofishticatedBlocks.CLEAR_GLASS_TRAPDOOR
        );
    }
}
