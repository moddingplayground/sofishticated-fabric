package net.moddingplayground.sofishticated.impl.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.client.model.entity.AnglerFishEntityModel;
import net.moddingplayground.sofishticated.api.entity.AnglerFishEntity;

@Environment(EnvType.CLIENT)
public class AnglerFishBioluminescenceFeatureRenderer extends EyesFeatureRenderer<AnglerFishEntity, AnglerFishEntityModel<AnglerFishEntity>> {
    private static final RenderLayer TEXTURE = RenderLayer.getEyes(new Identifier(Sofishticated.MOD_ID, "textures/entity/angler_fish/angler_fish_bioluminescence.png"));

    public AnglerFishBioluminescenceFeatureRenderer(FeatureRendererContext<AnglerFishEntity, AnglerFishEntityModel<AnglerFishEntity>> context) {
        super(context);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return TEXTURE;
    }
}
