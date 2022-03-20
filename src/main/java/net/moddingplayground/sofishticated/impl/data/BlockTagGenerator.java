package net.moddingplayground.sofishticated.impl.data;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.toymaker.v0.generator.tag.AbstractTagGenerator;
import net.moddingplayground.sofishticated.api.tag.SofishticatedBlockTags;

public class BlockTagGenerator extends AbstractTagGenerator<Block> {
    public BlockTagGenerator(String modId) {
        super(modId, Registry.BLOCK);
    }

    @Override
    public void generate() {
        this.add(SofishticatedBlockTags.MOIST_BLOCKS, Blocks.CLAY, Blocks.WET_SPONGE);
    }
}
