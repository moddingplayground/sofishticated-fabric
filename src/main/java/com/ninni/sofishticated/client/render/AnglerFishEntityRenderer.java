package com.ninni.sofishticated.client.render;

import com.ninni.sofishticated.Sofishticated;
import com.ninni.sofishticated.client.init.SofishticatedEntityModelLayers;
import com.ninni.sofishticated.client.model.entity.AnglerFishEntityModel;
import com.ninni.sofishticated.client.render.feature.AnglerFishBioluminescenceFeatureRenderer;
import com.ninni.sofishticated.entity.AnglerFishEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class AnglerFishEntityRenderer<T extends LivingEntity> extends MobEntityRenderer<AnglerFishEntity, AnglerFishEntityModel> {
    public static final Identifier TEXTURE = new Identifier(Sofishticated.MOD_ID, "textures/entity/angler_fish/angler_fish.png");

    public AnglerFishEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new AnglerFishEntityModel(ctx.getPart(SofishticatedEntityModelLayers.ANGLER_FISH)), 0.3F);
        this.addFeature(new AnglerFishBioluminescenceFeatureRenderer(this));
    }

    @Override
    protected void setupTransforms(AnglerFishEntity entity, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(entity, matrixStack, f, g, h);
        float i = 4.3F * MathHelper.sin(0.6F * f);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(i));
        if (!entity.isTouchingWater()) {
            matrixStack.translate(0.4D, 0.25D, 0.0D);
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
        }

    }

    @Override
    public Identifier getTexture(AnglerFishEntity entity) {
        return TEXTURE;
    }
}
