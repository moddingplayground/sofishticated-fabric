package com.ninni.sofishticated.entity.ai.goal;

import com.ninni.sofishticated.mixin.WanderAroundGoalAccessor;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;

public class MantaRaySwimAroundGoal extends SwimAroundGoal {
    public MantaRaySwimAroundGoal(PathAwareEntity pathAwareEntity, double d, int i) {
        super(pathAwareEntity, d, i);
    }

    @Override
    public boolean canStart() {
        if (!this.ignoringChance) {
            if (((WanderAroundGoalAccessor) this).canDespawn() && this.mob.getDespawnCounter() >= 100) {
                return false;
            }
        }

        Vec3d vec3d = this.getWanderTarget();
        if (vec3d == null) {
            return false;
        } else {
            this.targetX = vec3d.x;
            this.targetY = vec3d.y;
            this.targetZ = vec3d.z;
            this.ignoringChance = false;
            return true;
        }
    }

    @Override
    public boolean shouldContinue() {
        return !this.mob.getNavigation().isIdle();
    }
}
