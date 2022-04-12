package net.moddingplayground.sofishticated.impl.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import net.moddingplayground.sofishticated.api.client.model.SofishticatedEntityModelLayers;
import net.moddingplayground.sofishticated.api.client.model.entity.SeahorseEntityModel;
import net.moddingplayground.sofishticated.api.entity.SeahorseEntity;

@Environment(EnvType.CLIENT)
public class SeahorseEntityRenderer<E extends SeahorseEntity> extends MobEntityRenderer<E, SeahorseEntityModel<E>> {
    public SeahorseEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SeahorseEntityModel<>(context.getPart(SofishticatedEntityModelLayers.SEAHORSE)), 0.3F);
    }

    @Override
    protected void setupTransforms(E entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
        if (!entity.isTouchingWater()) {
            matrices.translate(0.4D, 0.1D, 0.0D);
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
        }
    }

    @Override
    public Identifier getTexture(E entity) {
        return entity.getVariant().getTexture();
    }
}
