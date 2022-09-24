package net.moddingplayground.sofishticated.impl.data.server;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.moddingplayground.sofishticated.api.tag.SofishticatedBlockTags;

import static net.minecraft.block.Blocks.*;

public final class BlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public BlockTagProvider(FabricDataGenerator gen) {
        super(gen);
    }

    @Override
    protected void generateTags() {
        this.getOrCreateTagBuilder(SofishticatedBlockTags.MOIST_BLOCKS).add(CLAY, WET_SPONGE);
    }
}
