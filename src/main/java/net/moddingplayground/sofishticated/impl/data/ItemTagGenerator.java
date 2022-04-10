package net.moddingplayground.sofishticated.impl.data;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;
import net.moddingplayground.sofishticated.api.tag.SofishticatedItemTags;

import static net.moddingplayground.sofishticated.api.item.SofishticatedItems.*;

public class ItemTagGenerator extends AbstractTagGenerator<Item> {
    public ItemTagGenerator(String modId) {
        super(modId, Registry.ITEM);
    }

    @Override
    public void generate() {
        this.add(SofishticatedItemTags.FISHES, SHRIMP, COOKED_SHRIMP, ANGLER_FISH);
        this.add(ItemTags.FISHES, SofishticatedItemTags.FISHES);

        this.add(SofishticatedItemTags.ANGLER_FISH_TEMPTS,
            Items.GLOW_INK_SAC,
            Items.GLOW_BERRIES,
            Items.GLOWSTONE_DUST,
            Items.GLOWSTONE
        );
    }
}
