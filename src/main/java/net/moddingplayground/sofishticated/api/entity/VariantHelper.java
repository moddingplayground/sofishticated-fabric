package net.moddingplayground.sofishticated.api.entity;

import net.minecraft.nbt.NbtCompound;

import java.util.Random;

public interface VariantHelper {
    String KEY_VARIANT = "Variant";
    String KEY_BUCKET_VARIANT_TAG = "BucketVariantTag";
    String KEY_FROM_BUCKET = "FromBucket";

    /* From NBT */

    static <V extends Enum<V>> V fromNbt(Class<V> clazz, NbtCompound nbt, Random random, String key) {
        String id = nbt.getString(key);
        try { return V.valueOf(clazz, id); } catch (IllegalArgumentException ignored) {}
        return getRandom(clazz, random);
    }

    static <V extends Enum<V>> V fromNbt(Class<V> clazz, NbtCompound nbt, Random random) {
        return fromNbt(clazz, nbt, random, KEY_VARIANT);
    }

    static <V extends Enum<V>> V fromBucket(Class<V> clazz, NbtCompound nbt, Random random) {
        return fromNbt(clazz, nbt, random, KEY_BUCKET_VARIANT_TAG);
    }

    /* To NBT */

    static <V extends Enum<V>> void toNbt(V variant, NbtCompound nbt, String key) {
        nbt.putString(key, variant.name());
    }

    static <V extends Enum<V>> void toNbt(V variant, NbtCompound nbt) {
        toNbt(variant, nbt, KEY_VARIANT);
    }

    static <V extends Enum<V>> void toBucket(V variant, NbtCompound nbt) {
        toNbt(variant, nbt, KEY_BUCKET_VARIANT_TAG);
    }

    /* Value Of */

    static <V extends Enum<V>> V safeValueOf(Class<V> clazz, String id) {
        try { return V.valueOf(clazz, id); } catch (IllegalArgumentException ignored) {}
        return getDefault(clazz);
    }

    /* Constants */

    static <V extends Enum<V>> V getDefault(Class<V> clazz) {
        V[] values = clazz.getEnumConstants();
        return values[0];
    }

    static <V extends Enum<V>> V getRandom(Class<V> clazz, Random random) {
        V[] values = clazz.getEnumConstants();
        return values[random.nextInt(values.length)];
    }
}
