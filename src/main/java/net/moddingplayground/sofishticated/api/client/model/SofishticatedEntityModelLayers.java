package net.moddingplayground.sofishticated.api.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.client.model.entity.ShrimpEntityModel;

@Environment(EnvType.CLIENT)
public interface SofishticatedEntityModelLayers {
    /*EntityModelLayer ANGLER_FISH = main("angler_fish");
    EntityModelLayer SUNFISH = main("sunfish");
    EntityModelLayer MANTA_RAY = main("manta_ray");
    EntityModelLayer MANTA_RAY_SADDLE = register("manta_ray", "saddle");
    EntityModelLayer PIRANHA = main("piranha");*/
    EntityModelLayer SHRIMP = main("shrimp", ShrimpEntityModel::getTexturedModelData);
    /*EntityModelLayer REEF_SHARK = main("reef_shark");
    EntityModelLayer BUTTERFLY_FISH = main("butterfly_fish");
    EntityModelLayer GAR = main("gar");*/

    private static EntityModelLayer register(String id, String name, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        EntityModelLayer layer = new EntityModelLayer(new Identifier(Sofishticated.MOD_ID, id), name);
        EntityModelLayerRegistry.registerModelLayer(layer, provider);
        return layer;
    }

    private static EntityModelLayer main(String id, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        return register(id, "main", provider);
    }
}
