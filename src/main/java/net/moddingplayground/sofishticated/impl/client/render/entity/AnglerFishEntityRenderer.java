package net.moddingplayground.sofishticated.impl.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.moddingplayground.sofishticated.api.client.model.SofishticatedEntityModelLayers;
import net.moddingplayground.sofishticated.api.client.model.entity.AnglerFishEntityModel;
import net.moddingplayground.sofishticated.api.entity.AnglerFishEntity;
import net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType;
import net.moddingplayground.sofishticated.api.util.TextureHelper;
import net.moddingplayground.sofishticated.impl.client.render.entity.feature.EmissiveLayerFeatureRenderer;

import java.util.function.Predicate;

@Environment(EnvType.CLIENT)
public class AnglerFishEntityRenderer<E extends AnglerFishEntity> extends MobEntityRenderer<E, AnglerFishEntityModel<E>> {
    public static final Identifier TEXTURE = TextureHelper.create(SofishticatedEntityType.ANGLER_FISH);
    public static final Identifier TEXTURE_EMISSIVE = TextureHelper.create(SofishticatedEntityType.ANGLER_FISH, "bioluminescence");
    public static final Identifier TEXTURE_DEFLATED = TextureHelper.create(SofishticatedEntityType.ANGLER_FISH, "deflated");

    private final AnglerFishEntityModel<E> normalModel, deflatedModel;

    public AnglerFishEntityRenderer(EntityRendererFactory.Context context) {
        super(context, null, 0.3F);
        this.addFeature(new EmissiveLayerFeatureRenderer<>(this, TEXTURE_EMISSIVE, Predicate.not(E::isDeflated)));

        this.model = this.normalModel = createModel(context, SofishticatedEntityModelLayers.ANGLER_FISH);
        this.deflatedModel = createModel(context, SofishticatedEntityModelLayers.ANGLER_FISH_DEFLATED);
    }

    @Override
    public void render(E entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertices, int light) {
        this.model = entity.isDeflated() ? this.deflatedModel : this.normalModel;
        super.render(entity, yaw, tickDelta, matrices, vertices, light);
    }

    @Override
    protected void setupTransforms(E entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
        if (entity.isDeflated()) return;

        if (!entity.isTouchingWater()) {
            matrices.translate(0.4D, 0.25D, 0.0D);
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
        }
    }

    @Override
    public Identifier getTexture(E entity) {
        return entity.isDeflated() ? TEXTURE_DEFLATED : TEXTURE;
    }

    public AnglerFishEntityModel<E> createModel(EntityRendererFactory.Context context, EntityModelLayer layer) {
        return new AnglerFishEntityModel<>(context.getPart(layer));
    }
}
