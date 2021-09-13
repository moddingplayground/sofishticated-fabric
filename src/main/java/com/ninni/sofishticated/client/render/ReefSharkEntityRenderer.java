package com.ninni.sofishticated.client.render;

import com.ninni.sofishticated.Sofishticated;
import com.ninni.sofishticated.client.init.SofishticatedEntityModelLayers;
import com.ninni.sofishticated.client.model.entity.ReefSharkEntityModel;
import com.ninni.sofishticated.entity.ReefSharkEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class ReefSharkEntityRenderer<T extends LivingEntity> extends MobEntityRenderer<ReefSharkEntity, ReefSharkEntityModel> {
    public static final Identifier TEXTURE = new Identifier(Sofishticated.MOD_ID, "textures/entity/reef_shark.png");

    public ReefSharkEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ReefSharkEntityModel(ctx.getPart(SofishticatedEntityModelLayers.REEF_SHARK)), 0.0F);
    }

    @Override
    protected void setupTransforms(ReefSharkEntity entity, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(entity, matrixStack, f, g, h);
        float i = 4.3F * MathHelper.sin(0.6F * f);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(i));
    }

    @Override
    public Identifier getTexture(ReefSharkEntity entity) {
        return TEXTURE;
    }
}
