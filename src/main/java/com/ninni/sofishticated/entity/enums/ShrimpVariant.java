package com.ninni.sofishticated.entity.enums;

import com.ninni.sofishticated.Sofishticated;
import net.minecraft.util.Identifier;

import java.util.Locale;

public enum ShrimpVariant {
    METALLIC,
    SNOW_WHITE,
    AMBER_PEARL,
    WHITE_PEARL,
    SUPER_WILD_TIGER,
    CHOCOLATE,
    SHOKO,
    GOLDBACK_CARBON_RILI,
    GREEN_EMERALD,
    GREEN_RILI,
    GOLDENBACK_JADE,
    GREEN_JADE,
    GREEN,
    BABAULTI_GREEN,
    KANOKO,
    RED_ONYX,
    DRAGON_BLOOD,
    BLUE_FANTASY,
    BLUE_DREAM,
    BLUE_DIAMOND,
    BLUE_JELLY,
    BLUE_BOLT,
    SULAWESI_TIGER_CARDINAL,
    ORANGE,
    YELLOW_NEON,
    BLACK_ROSE,
    CARBON_RILI,
    YELLOW_RILI,
    ORANGE_RILI,
    BLUE_RILI,
    CARBON_BLUE_RILI,
    RED_BOLT,
    RED_DEVIL_OE,
    RED_RILI,
    RED_BLUE_RILI,
    BLOODY_MARY;

    private final Identifier texture;

    ShrimpVariant() {
        this.texture = new Identifier(Sofishticated.MOD_ID, "textures/entity/shrimp/shrimp_" + this.name().toLowerCase(Locale.ROOT) + ".png");
    }

    public Identifier getTexture() {
        return texture;
    }

    public static ShrimpVariant getDefault() {
        return BLOODY_MARY;
    }
}
