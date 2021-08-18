package com.ninni.sofishticated.client.client.init;

import com.ninni.sofishticated.Sofishticated;
import com.ninni.sofishticated.mixin.client.EntityModelLayersInvoker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SofishticatedEntityModelLayers {
    public static final EntityModelLayer ANGLER_FISH = registerMain("angler_fish");

    private static EntityModelLayer registerMain(String id) {
        return EntityModelLayersInvoker.register(new Identifier(Sofishticated.MOD_ID, id).toString(), "main");
    }
}