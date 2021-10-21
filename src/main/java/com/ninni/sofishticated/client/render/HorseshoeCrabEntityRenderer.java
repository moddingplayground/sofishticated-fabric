package com.ninni.sofishticated.client.render;

import com.ninni.sofishticated.Sofishticated;
import com.ninni.sofishticated.client.init.SofishticatedEntityModelLayers;
import com.ninni.sofishticated.client.model.entity.HorseshoeCrabEntityModel;
import com.ninni.sofishticated.entity.HorseshoeCrabEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class HorseshoeCrabEntityRenderer<T extends LivingEntity> extends MobEntityRenderer<HorseshoeCrabEntity, HorseshoeCrabEntityModel> {
    public static final Identifier TEXTURE = new Identifier(Sofishticated.MOD_ID, "textures/entity/horseshoe_crab.png");

    public HorseshoeCrabEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new HorseshoeCrabEntityModel(ctx.getPart(SofishticatedEntityModelLayers.HORSESHOE_CRAB)), 0.3F);
    }

    @Override
    protected void setupTransforms(HorseshoeCrabEntity entity, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(entity, matrixStack, f, g, h);
        if (entity.world.getFluidState(entity.getBlockPos().up(1)).isIn(FluidTags.WATER)) {
            matrixStack.translate(0.0D, 0.35D, 0.0D);
            matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
        }

    }

    @Override
    public Identifier getTexture(HorseshoeCrabEntity entity) {
        return TEXTURE;
    }
}
