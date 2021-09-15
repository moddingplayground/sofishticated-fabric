package com.ninni.sofishticated.client.render;

import com.ninni.sofishticated.client.init.SofishticatedEntityModelLayers;
import com.ninni.sofishticated.client.model.entity.ButterflyFishEntityModel;
import com.ninni.sofishticated.entity.ButterflyFishEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class ButterflyFishEntityRenderer<T extends LivingEntity> extends MobEntityRenderer<ButterflyFishEntity, ButterflyFishEntityModel> {

    public ButterflyFishEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ButterflyFishEntityModel(ctx.getPart(SofishticatedEntityModelLayers.BUTTERFLY_FISH)), 0.3F);
    }

    @Override
    public Identifier getTexture(ButterflyFishEntity entity) {
        return entity.getVariant().getTexture();
    }
}
