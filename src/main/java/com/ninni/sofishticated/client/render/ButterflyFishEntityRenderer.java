package com.ninni.sofishticated.client.render;

import com.ninni.sofishticated.client.init.SofishticatedEntityModelLayers;
import com.ninni.sofishticated.client.model.entity.ButterflyFishEntityModel;
import com.ninni.sofishticated.entity.ButterflyFishEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class ButterflyFishEntityRenderer<T extends LivingEntity> extends MobEntityRenderer<ButterflyFishEntity, ButterflyFishEntityModel> {

    public ButterflyFishEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ButterflyFishEntityModel(ctx.getPart(SofishticatedEntityModelLayers.BUTTERFLY_FISH)), 0.2F);
    }

    @Override
    protected void setupTransforms(ButterflyFishEntity entity, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(entity, matrixStack, f, g, h);
        float i = 4.3F * MathHelper.sin(0.6F * f);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(i));
        if (!entity.isTouchingWater()) {
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
        }

    }

    @Override
    public Identifier getTexture(ButterflyFishEntity entity) {
        return entity.getVariant().getTexture();
    }
}
