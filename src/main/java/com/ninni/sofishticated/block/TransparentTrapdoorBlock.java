package com.ninni.sofishticated.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.util.math.Direction;

@SuppressWarnings("deprecation")
public class TransparentTrapdoorBlock extends TrapdoorBlock {
    public TransparentTrapdoorBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return (stateFrom.isOf(this) && stateFrom.get(TrapdoorBlock.FACING) == state.get(TrapdoorBlock.FACING)
            && direction.getOpposite() != state.get(TrapdoorBlock.FACING)
        ) || super.isSideInvisible(state, stateFrom, direction);
    }
}
