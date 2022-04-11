package net.moddingplayground.sofishticated.api.entity.variant;

import com.google.common.base.Suppliers;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.moddingplayground.sofishticated.api.entity.ShrimpEntity;
import net.moddingplayground.sofishticated.api.entity.SofishticatedEntityType;
import net.moddingplayground.sofishticated.api.registry.SofishticatedRegistry;
import net.moddingplayground.sofishticated.api.util.TextureHelper;

import java.util.function.Supplier;

/**
 * Represents a variant of a {@link ShrimpEntity}.
 */
public class ShrimpVariant {
    private final Supplier<Identifier> texture = Suppliers.memoize(this::createTexture);

    public ShrimpVariant() {}

    private Identifier createTexture() {
        Identifier id = SofishticatedRegistry.SHRIMP_VARIANT.getId(this);
        return TextureHelper.create(SofishticatedEntityType.SHRIMP, id.getPath());
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

    public static ShrimpVariant readNbt(NbtCompound nbt, String key) {
        String id = nbt.getString(key);
        return SofishticatedRegistry.SHRIMP_VARIANT.get(Identifier.tryParse(id));
    }

    public Identifier getId() {
        return SofishticatedRegistry.SHRIMP_VARIANT.getId(this);
    }

    @Override
    public String toString() {
        return this.getId().toString();
    }
}
