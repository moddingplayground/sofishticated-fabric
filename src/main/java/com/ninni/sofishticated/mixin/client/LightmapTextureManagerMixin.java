package com.ninni.sofishticated.mixin.client;

import com.ninni.sofishticated.init.SofishticatedStatusEffects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Environment(EnvType.CLIENT)
@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {
    @ModifyVariable(method = "update", ordinal = 5, at = @At(value = "STORE", ordinal = 0))
    private float applyUndergroundAmbientLight(float original) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            ClientPlayerEntity player = client.player;
            if (player != null && player.hasStatusEffect(SofishticatedStatusEffects.MANTAS_BLESSING)) {
                float underwaterVisibility = player.getUnderwaterVisibility();
                if (underwaterVisibility > 0) {
                    return underwaterVisibility;
                }
            }
        }

        return original;
    }
}
