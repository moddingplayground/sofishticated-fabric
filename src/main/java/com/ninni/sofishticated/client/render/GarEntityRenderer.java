package com.ninni.sofishticated.client.render;

import com.ninni.sofishticated.Sofishticated;
import com.ninni.sofishticated.client.init.SofishticatedEntityModelLayers;
import com.ninni.sofishticated.client.model.entity.GarEntityModel;
import com.ninni.sofishticated.entity.GarEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class GarEntityRenderer<T extends LivingEntity> extends MobEntityRenderer<GarEntity, GarEntityModel> {
    public static final Identifier TEXTURE = new Identifier(Sofishticated.MOD_ID, "textures/entity/gar.png");

    public GarEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new GarEntityModel(ctx.getPart(SofishticatedEntityModelLayers.GAR)), 0.0F);
    }

    @Override
    protected void setupTransforms(GarEntity entity, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(entity, matrixStack, f, g, h);
        if (!entity.isTouchingWater()) {
            matrixStack.translate(0.4D, 0.25D, 0.0D);
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
        }

    }

    @Override
    public Identifier getTexture(GarEntity entity) {
        return TEXTURE;
    }
}
