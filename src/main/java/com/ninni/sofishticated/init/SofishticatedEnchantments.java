package com.ninni.sofishticated.init;

import com.ninni.sofishticated.Sofishticated;
import com.ninni.sofishticated.enchantment.DebilitatingEnchantment;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused, FieldMayBeFinal")
public class SofishticatedEnchantments {

    private static Enchantment DEBILITATING = Registry.register(
        Registry.ENCHANTMENT,
        new Identifier(Sofishticated.MOD_ID, "debilitating"),
        new DebilitatingEnchantment(Enchantment.Rarity.UNCOMMON, DamageEnchantment.ALL_INDEX, EquipmentSlot.MAINHAND)
    );
}
