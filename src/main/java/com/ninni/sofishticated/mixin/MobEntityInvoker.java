package com.ninni.sofishticated.mixin;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MobEntity.class)
public interface MobEntityInvoker {
    @Invoker("disablePlayerShield")
    void invoke_disablePlayerShield(PlayerEntity player, ItemStack mobStack, ItemStack playerStack);
}
