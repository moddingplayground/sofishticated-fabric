package net.moddingplayground.sofishticated.api.entity.variant;

import com.google.common.base.Suppliers;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.moddingplayground.sofishticated.api.entity.SeahorseEntity;
import net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType;
import net.moddingplayground.sofishticated.api.registry.SofishticatedRegistry;
import net.moddingplayground.sofishticated.api.util.TextureHelper;

import java.util.function.Supplier;

/**
 * Represents a variant of a {@link SeahorseEntity}.
 */
public class SeahorseVariant {
    private final Supplier<Identifier> texture = Suppliers.memoize(this::createTexture);

    public SeahorseVariant() {}

    private Identifier createTexture() {
        Identifier id = SofishticatedRegistry.SEAHORSE_VARIANT.getId(this);
        return TextureHelper.create(SofishticatedEntityType.SEAHORSE, id.getPath());
    }

    /**
     * Retrieves the cached texture for this variant.
     */
    public Identifier getTexture() {
        return this.texture.get();
    }

    public void writeNbt(NbtCompound nbt, String key) {
        nbt.putString(key, this.toString());
    }

    public static SeahorseVariant readNbt(NbtCompound nbt, String key) {
        String id = nbt.getString(key);
        return SofishticatedRegistry.SEAHORSE_VARIANT.get(Identifier.tryParse(id));
    }

    public Identifier getId() {
        return SofishticatedRegistry.SEAHORSE_VARIANT.getId(this);
    }

    @Override
    public String toString() {
        return this.getId().toString();
    }
}
