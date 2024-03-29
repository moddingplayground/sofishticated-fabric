package net.moddingplayground.sofishticated.impl.client.entity;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.moddingplayground.sofishticated.api.client.render.entity.AnglerFishEntityRenderer;
import net.moddingplayground.sofishticated.api.client.render.entity.SeahorseEntityRenderer;
import net.moddingplayground.sofishticated.api.client.render.entity.ShrimpEntityRenderer;
import net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType;

@Environment(EnvType.CLIENT)
public final class SofishticatedEntityTypeClientImpl implements SofishticatedEntityType, ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(SHRIMP, ShrimpEntityRenderer::new);
        EntityRendererRegistry.register(ANGLER_FISH, AnglerFishEntityRenderer::new);
        EntityRendererRegistry.register(SEAHORSE, SeahorseEntityRenderer::new);
    }
}
