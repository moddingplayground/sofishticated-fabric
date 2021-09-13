package com.ninni.sofishticated.client.render;

import com.ninni.sofishticated.client.init.SofishticatedEntityModelLayers;
import com.ninni.sofishticated.client.model.entity.ShrimpEntityModel;
import com.ninni.sofishticated.entity.ShrimpEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class ShrimpEntityRenderer extends MobEntityRenderer<ShrimpEntity, ShrimpEntityModel> {
    public ShrimpEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ShrimpEntityModel(ctx.getPart(SofishticatedEntityModelLayers.SHRIMP)), 0.3F);
    }

    @Override
    public Identifier getTexture(ShrimpEntity entity) {
        return entity.getVariant().getTexture();
    }
}
