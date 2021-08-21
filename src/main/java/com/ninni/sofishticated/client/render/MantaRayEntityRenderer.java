package com.ninni.sofishticated.client.render;

import com.ninni.sofishticated.Sofishticated;
import com.ninni.sofishticated.client.client.init.SofishticatedEntityModelLayers;
import com.ninni.sofishticated.client.model.entity.MantaRayEntityModel;
import com.ninni.sofishticated.entity.MantaRayEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class MantaRayEntityRenderer<T extends LivingEntity> extends MobEntityRenderer<MantaRayEntity, MantaRayEntityModel> {
    public static final Identifier TEXTURE = new Identifier(Sofishticated.MOD_ID, "textures/entity/manta_ray/manta_ray.png");
    public static final Identifier SADDLED_TEXTURE = new Identifier(Sofishticated.MOD_ID, "textures/entity/manta_ray/manta_ray_saddled.png");

    public MantaRayEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MantaRayEntityModel(ctx.getPart(SofishticatedEntityModelLayers.MANTA_RAY)), 0.0F);
        this.addFeature(new SaddleFeatureRenderer<>(this, new MantaRayEntityModel(ctx.getPart(SofishticatedEntityModelLayers.MANTA_RAY_SADDLE)), new Identifier(Sofishticated.MOD_ID, "textures/entity/manta_ray/manta_ray_saddle_overlay.png")));
    }

    @Override
    protected void setupTransforms(MantaRayEntity entity, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(entity, matrixStack, f, g, h);
    }

    @Override
    public Identifier getTexture(MantaRayEntity entity) {
        return entity.isSaddled() ? SADDLED_TEXTURE : TEXTURE;

    }
}