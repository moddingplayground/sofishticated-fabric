package net.moddingplayground.sofishticated.impl.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.moddingplayground.sofishticated.api.client.model.SofishticatedEntityModelLayers;
import net.moddingplayground.sofishticated.api.client.model.entity.ShrimpEntityModel;
import net.moddingplayground.sofishticated.api.entity.ShrimpEntity;

@Environment(EnvType.CLIENT)
public class ShrimpEntityRenderer extends MobEntityRenderer<ShrimpEntity, ShrimpEntityModel> {
    public ShrimpEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ShrimpEntityModel(context.getPart(SofishticatedEntityModelLayers.SHRIMP)), 0.3F);
    }

    @Override
    public Identifier getTexture(ShrimpEntity entity) {
        return entity.getVariant().getTexture();
    }
}
