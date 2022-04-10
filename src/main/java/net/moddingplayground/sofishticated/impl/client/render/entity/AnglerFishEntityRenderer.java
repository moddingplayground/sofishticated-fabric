package net.moddingplayground.sofishticated.impl.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.client.model.SofishticatedEntityModelLayers;
import net.moddingplayground.sofishticated.api.client.model.entity.AnglerFishEntityModel;
import net.moddingplayground.sofishticated.api.entity.AnglerFishEntity;
import net.moddingplayground.sofishticated.impl.client.render.entity.feature.EmissiveLayerFeatureRenderer;

@Environment(EnvType.CLIENT)
public class AnglerFishEntityRenderer extends MobEntityRenderer<AnglerFishEntity, AnglerFishEntityModel<AnglerFishEntity>> {
    public static final Identifier TEXTURE = new Identifier(Sofishticated.MOD_ID, "textures/entity/angler_fish/angler_fish.png");
    public static final Identifier TEXTURE_EMISSIVE = new Identifier(Sofishticated.MOD_ID, "textures/entity/angler_fish/angler_fish_bioluminescence.png");

    public AnglerFishEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new AnglerFishEntityModel<>(context.getPart(SofishticatedEntityModelLayers.ANGLER_FISH)), 0.3F);
        this.addFeature(new EmissiveLayerFeatureRenderer<>(this, TEXTURE_EMISSIVE));
    }

    @Override
    protected void setupTransforms(AnglerFishEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
        float i = 4.3F * MathHelper.sin(0.6F * animationProgress);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(i));
        if (!entity.isTouchingWater()) {
            matrices.translate(0.4D, 0.25D, 0.0D);
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
        }

    }

    @Override
    public Identifier getTexture(AnglerFishEntity entity) {
        return TEXTURE;
    }
}
