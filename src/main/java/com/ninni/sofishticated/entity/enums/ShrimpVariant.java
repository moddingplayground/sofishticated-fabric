package com.ninni.sofishticated.entity.enums;

import com.ninni.sofishticated.Sofishticated;
import net.minecraft.util.Identifier;

import java.util.Locale;

public enum ShrimpVariant {
    APRICOT,
    APRICOT_RILI,
    CHERRY,
    DREAM,
    DREAM_BOLT,
    DREAM_RACING,
    DREAM_TIGER,
    GHOST,
    GOLDEN,
    JADE,
    JELLY,
    JELLY_BOLT,
    JELLY_BRUSH,
    JELLY_FISHBONE,
    JELLY_SULAWESI,
    NEON,
    ROSE,
    ROSE_BRUSH,
    ROSE_FISHBONE,
    ROSE_TIGER,
    SAKURA,
    SAKURA_CRYSTAL,
    SAKURA_FISHBONE,
    SAKURA_RACING,
    SCR,
    SNOW,
    SNOW_SULAWESI,
    WASP,
    WASP_SULAWESI,
    WASP_TIGER,
    WILD,
    WILD_TIGER;
    public final Identifier texture;

    ShrimpVariant() {
        this.texture = new Identifier(Sofishticated.MOD_ID, "textures/entity/shrimp/shrimp_" + this.toString().toLowerCase(Locale.ROOT) + ".png");
    }

    public static ShrimpVariant getDefault() {
        return WILD;
    }
}
