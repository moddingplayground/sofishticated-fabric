package net.moddingplayground.sofishticated.api.util;

import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface TextureHelper {
    static Identifier createTexture(EntityType<?> type, String suffix) {
        Identifier id = Registry.ENTITY_TYPE.getId(type);
        return new Identifier(id.getNamespace(), "textures/entity/%1$s/%1$s%2$s.png".formatted(id.getPath(), suffix.isBlank() ? "" : "_" + suffix));
    }

    static Identifier createTexture(EntityType<?> type) {
        return createTexture(type, "");
    }
}
