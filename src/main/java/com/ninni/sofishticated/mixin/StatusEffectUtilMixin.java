package com.ninni.sofishticated.mixin;

import com.ninni.sofishticated.init.SofishticatedStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectUtil.class)
public class StatusEffectUtilMixin {
    @Inject(method = "hasWaterBreathing", at = @At("RETURN"), cancellable = true)
    private static void modifyHasWaterBreathing(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ() && entity.hasStatusEffect(SofishticatedStatusEffects.MANTAS_BLESSING)) {
            cir.setReturnValue(true);
        }
    }
}
