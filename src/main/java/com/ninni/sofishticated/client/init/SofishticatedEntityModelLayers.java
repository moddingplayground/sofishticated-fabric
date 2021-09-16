package com.ninni.sofishticated.client.init;

import com.ninni.sofishticated.Sofishticated;
import com.ninni.sofishticated.mixin.client.EntityModelLayersInvoker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SofishticatedEntityModelLayers {
    public static final EntityModelLayer ANGLER_FISH = registerMain("angler_fish");
    public static final EntityModelLayer SUNFISH = registerMain("sunfish");
    public static final EntityModelLayer MANTA_RAY = registerMain("manta_ray");
    public static final EntityModelLayer MANTA_RAY_SADDLE = register("manta_ray", "saddle");
    public static final EntityModelLayer PIRANHA = registerMain("piranha");
    public static final EntityModelLayer SHRIMP = registerMain("shrimp");
    public static final EntityModelLayer REEF_SHARK = registerMain("reef_shark");
    public static final EntityModelLayer BUTTERFLY_FISH = registerMain("butterfly_fish");
    public static final EntityModelLayer BIG_EYED = registerMain("big_eyed");

    private static EntityModelLayer registerMain(String id) {
        return EntityModelLayersInvoker.register(new Identifier(Sofishticated.MOD_ID, id).toString(), "main");
    }

    private static EntityModelLayer register(String id, String layer) {
        return EntityModelLayersInvoker.register(new Identifier(Sofishticated.MOD_ID, id).toString(), layer);
    }
}
