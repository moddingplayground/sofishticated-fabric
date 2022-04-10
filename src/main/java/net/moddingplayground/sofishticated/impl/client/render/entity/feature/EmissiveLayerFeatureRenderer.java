package net.moddingplayground.sofishticated.impl.client.render.entity.feature;

import com.google.common.base.Suppliers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class EmissiveLayerFeatureRenderer<T extends Entity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private final Identifier texture;
    private final Supplier<RenderLayer> layer;
    private final Predicate<T> predicate;

    public EmissiveLayerFeatureRenderer(FeatureRendererContext<T, M> context, Identifier texture, Predicate<T> predicate) {
        super(context);
        this.texture = texture;
        this.layer = Suppliers.memoize(() -> RenderLayer.getEyes(this.getTexture()));
        this.predicate = predicate;
    }

    public EmissiveLayerFeatureRenderer(FeatureRendererContext<T, M> context, Identifier texture) {
        this(context, texture, e -> true);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertices, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (!this.predicate.test(entity)) return;
        VertexConsumer vertex = vertices.getBuffer(this.layer.get());
        this.getContextModel().render(matrices, vertex, 0xF00000, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    public Identifier getTexture() {
        return this.texture;
    }
}
