package com.ninni.sofishticated.client.render.feature;

import com.ninni.sofishticated.Sofishticated;
import com.ninni.sofishticated.client.model.entity.AnglerFishEntityModel;
import com.ninni.sofishticated.entity.AnglerFishEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class AnglerFishBioluminescenceFeatureRenderer extends EyesFeatureRenderer<AnglerFishEntity, AnglerFishEntityModel> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(new Identifier(Sofishticated.MOD_ID, "textures/entity/angler_fish/angler_fish_bioluminescence.png"));

    public AnglerFishBioluminescenceFeatureRenderer(FeatureRendererContext<AnglerFishEntity, AnglerFishEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}
