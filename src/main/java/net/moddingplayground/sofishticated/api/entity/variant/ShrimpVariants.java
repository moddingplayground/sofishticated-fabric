package net.moddingplayground.sofishticated.api.entity.variant;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.registry.SofishticatedRegistry;

public interface ShrimpVariants {
    ShrimpVariant AMBER_PEARL = register("amber_pearl");
    ShrimpVariant BABAULTI_GREEN = register("babaulti_green");
    ShrimpVariant BLACK_BEE = register("black_bee");
    ShrimpVariant BLACK_FANCY = register("black_fancy");
    ShrimpVariant BLACK_ROSE = register("black_rose");
    ShrimpVariant BLOODY_MARY = register("bloody_mary");
    ShrimpVariant BLUE_BOLT = register("blue_bolt");
    ShrimpVariant BLUE_DIAMOND = register("blue_diamond");
    ShrimpVariant BLUE_DREAM = register("blue_dream");
    ShrimpVariant BLUE_FANTASY = register("blue_fantasy");
    ShrimpVariant BLUE_JELLY = register("blue_jelly");
    ShrimpVariant BLUE_RILI = register("blue_rili");
    ShrimpVariant CARBON_BLUE_RILI = register("carbon_blue_rili");
    ShrimpVariant CARBON_RILI = register("carbon_rili");
    ShrimpVariant CHOCOLATE = register("chocolate");
    ShrimpVariant DEEP_BLUE_TIGER = register("deep_blue_tiger");
    ShrimpVariant DRAGON_BLOOD = register("dragon_blood");
    ShrimpVariant GOLDBACK_CARBON_RILI = register("goldback_carbon_rili");
    ShrimpVariant GOLDEN_BOA = register("golden_boa");
    ShrimpVariant GOLDENBACK_JADE = register("goldenback_jade");
    ShrimpVariant GREEN = register("green");
    ShrimpVariant GREEN_APPLE = register("green_apple");
    ShrimpVariant GREEN_EMERALD = register("green_emerald");
    ShrimpVariant GREEN_JADE = register("green_jade");
    ShrimpVariant GREEN_RILI = register("green_rili");
    ShrimpVariant KANOKO = register("kanoko");
    ShrimpVariant METALLIC = register("metallic");
    ShrimpVariant ORANGE = register("orange");
    ShrimpVariant ORANGE_RILI = register("orange_rili");
    ShrimpVariant PURPLE_ZEBRA = register("purple_zebra");
    ShrimpVariant RED_BEE = register("red_bee");
    ShrimpVariant RED_BLUE_RILI = register("red_blue_rili");
    ShrimpVariant RED_BOLT = register("red_bolt");
    ShrimpVariant RED_DEVIL_OE = register("red_devil_oe");
    ShrimpVariant RED_FANCY = register("red_fancy");
    ShrimpVariant RED_ONYX = register("red_onyx");
    ShrimpVariant RED_RILI = register("red_rili");
    ShrimpVariant RED_TIGER = register("red_tiger");
    ShrimpVariant RED_WINE = register("red_wine");
    ShrimpVariant RED_YELLOW_TIGER = register("red_yellow_tiger");
    ShrimpVariant RED_ZEBRA = register("red_zebra");
    ShrimpVariant ROYAL_BLUE_OE = register("royal_blue_oe");
    ShrimpVariant RUSTY_RED_TIFER_OE = register("rusty_red_tifer_oe");
    ShrimpVariant SHOKO = register("shoko");
    ShrimpVariant SNOW_WHITE = register("snow_white");
    ShrimpVariant SULAWESI_TIGER_CARDINAL = register("sulawesi_tiger_cardinal");
    ShrimpVariant SUPER_WILD_TIGER = register("super_wild_tiger");
    ShrimpVariant TANGERINE_TIGER = register("tangerine_tiger");
    ShrimpVariant TIGER_OE = register("tiger_oe");
    ShrimpVariant WHITE_PEARL = register("white_pearl");
    ShrimpVariant YELLOW_NEON = register("yellow_neon");
    ShrimpVariant YELLOW_RILI = register("yellow_rili");

    private static ShrimpVariant register(String id) {
        return Registry.register(SofishticatedRegistry.SHRIMP_VARIANT, new Identifier(Sofishticated.MOD_ID, id), new ShrimpVariant());
    }
}
