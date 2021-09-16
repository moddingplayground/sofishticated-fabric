package com.ninni.sofishticated.client.render;

import com.ninni.sofishticated.Sofishticated;
import com.ninni.sofishticated.client.init.SofishticatedEntityModelLayers;
import com.ninni.sofishticated.client.model.entity.BigEyedEntityModel;
import com.ninni.sofishticated.entity.BigEyedEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class BigEyedEntityRenderer extends MobEntityRenderer<BigEyedEntity, BigEyedEntityModel> {
    public static final Identifier TEXTURE = new Identifier(Sofishticated.MOD_ID, "textures/entity/big_eyed.png");

    public BigEyedEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BigEyedEntityModel(ctx.getPart(SofishticatedEntityModelLayers.BIG_EYED)), 0.3F);
    }

    @Override
    public Identifier getTexture(BigEyedEntity entity) {
        return TEXTURE;
    }
}
