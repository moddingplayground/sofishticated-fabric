package net.moddingplayground.sofishticated.api.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.moddingplayground.sofishticated.mixin.access.EntityBucketItemAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.moddingplayground.sofishticated.api.entity.VariantHelper.*;

public class VariantEntityBucketItem extends EntityBucketItem {
    public VariantEntityBucketItem(EntityType<?> type, Fluid fluid, SoundEvent emptyingSound, Settings settings) {
        super(type, fluid, emptyingSound, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getNbt();
        if (nbt != null && nbt.contains(BUCKET_VARIANT_TAG_KEY, NbtElement.STRING_TYPE)) {
            String variant = nbt.getString(BUCKET_VARIANT_TAG_KEY);
            String entityTranslation = ((EntityBucketItemAccessor) this).getEntityType().getTranslationKey();
            String key = "%s.variant.%s".formatted(entityTranslation, variant.toLowerCase());
            Text text = Text.translatable(key).formatted(Formatting.ITALIC, Formatting.GRAY);
            tooltip.add(text);
        }
    }
}
