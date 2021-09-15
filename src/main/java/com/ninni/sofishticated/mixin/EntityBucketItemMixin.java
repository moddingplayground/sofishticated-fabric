package com.ninni.sofishticated.mixin;

import com.ninni.sofishticated.entity.ShrimpEntity;
import com.ninni.sofishticated.entity.enums.ButterflyFishVariant;
import com.ninni.sofishticated.entity.enums.ShrimpVariant;
import com.ninni.sofishticated.init.SofishticatedEntities;
import com.ninni.sofishticated.init.SofishticatedItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Locale;

@Mixin(EntityBucketItem.class)
public class EntityBucketItemMixin {
    @Inject(method = "spawnEntity", at = @At("HEAD"), cancellable = true)
    private void spawnShrimp(ServerWorld serverWorld, ItemStack stack, BlockPos pos, CallbackInfo ci) {
        if (stack.getItem() == SofishticatedItems.SHRIMP_BUCKET) {
            Entity entity = SofishticatedEntities.SHRIMP.spawnFromItemStack(serverWorld, stack, null, pos, SpawnReason.BUCKET, true, false);
            if (entity instanceof ShrimpEntity shrimpEntity) {
                shrimpEntity.setFromBucket(true);

                NbtCompound tag = stack.getNbt();
                if (tag != null) {
                    String variant = tag.getString("BucketVariantTag");
                    if (variant != null) {
                        shrimpEntity.setVariant(ShrimpVariant.valueOf(variant));
                    }
                }
            }

            ci.cancel();
        }
    }

    @Inject(method = "appendTooltip", at = @At("HEAD"))
    private void appendCustomTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        if (stack.getItem() == SofishticatedItems.SHRIMP_BUCKET) {
            NbtCompound tag = stack.getNbt();
            if (tag != null) {
                String variant = tag.getString("BucketVariantTag");
                if (variant != null) {
                    tooltip.add(sofishticated_createVariantTooltip(SofishticatedEntities.SHRIMP, ShrimpVariant.valueOf(variant)));
                }
            }
        }
        if (stack.getItem() == SofishticatedItems.BUTTERFLY_FISH_BUCKET) {
            NbtCompound tag = stack.getNbt();
            if (tag != null) {
                String variant = tag.getString("ButterflyFishBucketVariantTag");
                if (variant != null) {
                    tooltip.add(sofishticated_createVariantTooltip(SofishticatedEntities.BUTTERFLY_FISH, ButterflyFishVariant.valueOf(variant)));
                }
            }
        }
    }

    private static Text sofishticated_createVariantTooltip(EntityType<?> type, Object variant) {
        return new TranslatableText(
            String.format(
                "%s.variant.%s",
                type.getTranslationKey(), variant.toString().toLowerCase(Locale.ROOT)
            )
        ).formatted(Formatting.GRAY, Formatting.ITALIC);
    }
}