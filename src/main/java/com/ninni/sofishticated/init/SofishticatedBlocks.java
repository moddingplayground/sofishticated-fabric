package com.ninni.sofishticated.init;

import com.ninni.sofishticated.Sofishticated;
import com.ninni.sofishticated.block.BigEyedFossilBlock;
import com.ninni.sofishticated.block.TransparentTrapdoorBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class SofishticatedBlocks {

    public static final Block BIG_EYED_FOSSIL = register("big_eyed_fossil", new BigEyedFossilBlock(AbstractBlock.Settings.of(Material.STONE).strength(0.5f, 0.0f).sounds(BlockSoundGroup.STONE)));
    public static final Block CLEAR_GLASS = register("clear_glass", new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS)));
    public static final Block CLEAR_GLASS_PANE = register("clear_glass_pane", new StainedGlassPaneBlock(DyeColor.WHITE, FabricBlockSettings.copyOf(SofishticatedBlocks.CLEAR_GLASS)));
    public static final Block CLEAR_GLASS_TRAPDOOR = register("clear_glass_trapdoor", new TransparentTrapdoorBlock(FabricBlockSettings.of(Material.GLASS).strength(0.3F).sounds(BlockSoundGroup.GLASS).nonOpaque()));

    private static Block register(String id, Block block, boolean registerItem) {
        return Registry.register(Registry.BLOCK, new Identifier(Sofishticated.MOD_ID, id), block);
    }
    private static Block register(String id, Block block) {
        return register(id, block, false);
    }
}
