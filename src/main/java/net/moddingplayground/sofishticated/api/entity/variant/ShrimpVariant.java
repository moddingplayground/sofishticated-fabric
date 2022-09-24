package net.moddingplayground.sofishticated.api.entity.variant;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.moddingplayground.sofishticated.api.entity.SeahorseEntity;
import net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType;
import net.moddingplayground.sofishticated.api.registry.SofishticatedRegistry;
import net.moddingplayground.sofishticated.api.util.TextureHelper;

/**
 * Represents a variant of a {@link SeahorseEntity}.
 */
public class ShrimpVariant {
    private Identifier texture;

    public ShrimpVariant() {}

    /**
     * Retrieves the cached texture for this variant.
     */
    public Identifier getTexture() {
        if (this.texture == null) {
            this.texture = TextureHelper.createTexture(SofishticatedEntityType.SHRIMP, this.getId().getPath());
        }

        return this.texture;
    }

    public void writeNbt(NbtCompound nbt, String key) {
        nbt.putString(key, this.getStringId());
    }

    public static ShrimpVariant readNbt(NbtCompound nbt, String key) {
        String id = nbt.getString(key);
        return SofishticatedRegistry.SHRIMP_VARIANT.get(Identifier.tryParse(id));
    }

    public Identifier getId() {
        return SofishticatedRegistry.SHRIMP_VARIANT.getId(this);
    }

    public String getStringId() {
        return this.getId().toString();
    }

    @Override
    public String toString() {
        return this.getStringId();
    }
}
