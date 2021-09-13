package com.ninni.sofishticated.client.render;

import com.ninni.sofishticated.Sofishticated;
import com.ninni.sofishticated.client.init.SofishticatedEntityModelLayers;
import com.ninni.sofishticated.client.model.entity.SunfishEntityModel;
import com.ninni.sofishticated.entity.SunfishEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class SunfishEntityRenderer<T extends LivingEntity> extends MobEntityRenderer<SunfishEntity, SunfishEntityModel> {
    public static final Identifier TEXTURE = new Identifier(Sofishticated.MOD_ID, "textures/entity/sunfish.png");

    public SunfishEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SunfishEntityModel(ctx.getPart(SofishticatedEntityModelLayers.SUNFISH)), 0.0F);
    }

    @Override
    protected void setupTransforms(SunfishEntity entity, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(entity, matrixStack, f, g, h);
        if (!entity.world.getFluidState(entity.getBlockPos().up(4)).isIn(FluidTags.WATER)) {
            matrixStack.translate(0.85D, 0.45D, 0.0D);
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
        }

    }

    @Override
    public Identifier getTexture(SunfishEntity entity) {
        return TEXTURE;
    }
}
