package net.moddingplayground.sofishticated.api.tag;

import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.sofishticated.api.Sofishticated;

public interface SofishticatedBlockTags {
    TagKey<Block> MOIST_BLOCKS = create("moist_blocks");

    private static TagKey<Block> create(String id) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier(Sofishticated.MOD_ID, id));
    }
}
