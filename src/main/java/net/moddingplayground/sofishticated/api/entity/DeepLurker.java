package net.moddingplayground.sofishticated.api.entity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public interface DeepLurker {
    default float getLurkingPathfindingFavor(BlockPos pos, WorldView world) {
        int y = pos.getY() + Math.abs(world.getBottomY());
        return 1f / (y == 0 ? 1 : y);
    }
}
