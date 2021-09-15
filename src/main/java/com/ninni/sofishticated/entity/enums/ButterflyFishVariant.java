package com.ninni.sofishticated.entity.enums;

import com.ninni.sofishticated.Sofishticated;
import net.minecraft.util.Identifier;

import java.util.Locale;

public enum ButterflyFishVariant {
    HOODED,
    THREADFIN,
    SMITHS,
    TINKERS,
    LONGNOSE,
    RACCOON,
    COPPER_BAND;

    private final Identifier texture;

    ButterflyFishVariant() {
        this.texture = new Identifier(Sofishticated.MOD_ID, "textures/entity/butterfly_fish/butterfly_fish_" + this.name().toLowerCase(Locale.ROOT) + ".png");
    }

    public Identifier getTexture() {
        return texture;
    }

    public static ButterflyFishVariant getDefault() {
        return COPPER_BAND;
    }
}
