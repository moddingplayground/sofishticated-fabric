package com.ninni.sofishticated.init;

import com.ninni.sofishticated.Sofishticated;
import com.ninni.sofishticated.entity.effect.vanilla.PublicStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SofishticatedStatusEffects {
    public static final StatusEffect MANTAS_BLESSING = register("mantas_blessing", new PublicStatusEffect(StatusEffectCategory.BENEFICIAL, 0x88A3BE));

    private static StatusEffect register(String id, StatusEffect effect) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(Sofishticated.MOD_ID, id), effect);
    }
}
