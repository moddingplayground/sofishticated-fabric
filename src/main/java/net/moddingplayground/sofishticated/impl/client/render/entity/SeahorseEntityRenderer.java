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
import net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType;
import net.moddingplayground.sofishticated.api.util.TextureHelper;

@Environment(EnvType.CLIENT)
public class SeahorseEntityRenderer extends MobEntityRenderer<SeahorseEntity, SeahorseEntityModel<SeahorseEntity>> {
    public static final Identifier TEXTURE = TextureHelper.create(SofishticatedEntityType.SEAHORSE);

    public SeahorseEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SeahorseEntityModel<>(context.getPart(SofishticatedEntityModelLayers.SEAHORSE)), 0.3F);
    }

    @Override
    protected void setupTransforms(SeahorseEntity entity, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(entity, matrixStack, f, g, h);
        if (!entity.isTouchingWater()) {
            matrixStack.translate(0.10000000149011612D, 0.10000000149011612D, -0.10000000149011612D);
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
        }

    }

    @Override
    public Identifier getTexture(SeahorseEntity entity) {
        return TEXTURE;
    }
}
