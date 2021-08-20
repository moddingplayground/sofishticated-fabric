package com.ninni.sofishticated.mixin;

import net.minecraft.entity.ai.goal.WanderAroundGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WanderAroundGoal.class)
public interface WanderAroundGoalAccessor {
    @Accessor("canDespawn")
    boolean canDespawn();
}
