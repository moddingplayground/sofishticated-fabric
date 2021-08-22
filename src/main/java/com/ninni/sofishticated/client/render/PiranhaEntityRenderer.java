package com.ninni.sofishticated.client.render;

import com.ninni.sofishticated.Sofishticated;
import com.ninni.sofishticated.client.client.init.SofishticatedEntityModelLayers;
import com.ninni.sofishticated.client.model.entity.PiranhaEntityModel;
import com.ninni.sofishticated.entity.PiranhaEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class PiranhaEntityRenderer<T extends LivingEntity> extends MobEntityRenderer<PiranhaEntity, PiranhaEntityModel> {
    public static final Identifier TEXTURE = new Identifier(Sofishticated.MOD_ID, "textures/entity/piranha/piranha.png");
    public static final Identifier ANGRY_TEXTURE = new Identifier(Sofishticated.MOD_ID, "textures/entity/piranha/piranha_angry.png");

    public PiranhaEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PiranhaEntityModel(ctx.getPart(SofishticatedEntityModelLayers.PIRANHA)), 0.0F);
    }

    @Override
    protected void setupTransforms(PiranhaEntity entity, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(entity, matrixStack, f, g, h);
        float i = 4.3F * MathHelper.sin(0.6F * f);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(i));
    }

    @Override
    public Identifier getTexture(PiranhaEntity entity) {
        return entity.isAttacking() ? ANGRY_TEXTURE : TEXTURE;
    }
}