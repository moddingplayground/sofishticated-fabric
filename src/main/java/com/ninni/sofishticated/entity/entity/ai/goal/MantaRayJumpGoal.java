package com.ninni.sofishticated.entity.entity.ai.goal;

import com.ninni.sofishticated.entity.MantaRayEntity;
import net.minecraft.entity.ai.goal.DiveJumpingGoal;
import net.minecraft.fluid.FluidState;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MantaRayJumpGoal extends DiveJumpingGoal {
    private static final int[] OFFSET_MULTIPLIERS = new int[]{0, 1, 4, 5, 6, 7};
    private final MantaRayEntity mob;
    private final int chance;
    private boolean inWater;

    public MantaRayJumpGoal(MantaRayEntity mob, int chance) {
        this.mob = mob;
        this.chance = chance;
    }

    @Override
    public boolean canStart() {
        if (this.mob.getRandom().nextInt(this.chance) != 0) {
            return false;
        } else {
            Direction direction = this.mob.getMovementDirection();
            int offsetX = direction.getOffsetX();
            int offsetZ = direction.getOffsetZ();
            BlockPos blockPos = this.mob.getBlockPos();
            for (int multiplier : OFFSET_MULTIPLIERS) {
                if (!this.isWater(blockPos, offsetX, offsetZ, multiplier) || !this.isAirAbove(blockPos, offsetX, offsetZ, multiplier)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isWater(BlockPos pos, int offsetX, int offsetZ, int multiplier) {
        BlockPos blockPos = pos.add(offsetX * multiplier, 0, offsetZ * multiplier);
        return this.mob.world.getFluidState(blockPos).isIn(FluidTags.WATER) && !this.mob.world.getBlockState(blockPos).getMaterial().blocksMovement();
    }

    private boolean isAirAbove(BlockPos pos, int offsetX, int offsetZ, int multiplier) {
        return this.mob.world.getBlockState(pos.add(offsetX * multiplier, 1, offsetZ * multiplier)).isAir() && this.mob.world.getBlockState(pos.add(offsetX * multiplier, 2, offsetZ * multiplier)).isAir();
    }

    @Override
    public boolean shouldContinue() {
        double velY = this.mob.getVelocity().y;
        return (!(velY * velY < 0.03D) || this.mob.getPitch() == 0.0F || !(Math.abs(this.mob.getPitch()) < 10.0F) || !this.mob.isTouchingWater()) && !this.mob.isOnGround();
    }

    @Override
    public boolean canStop() {
        return false;
    }

    @Override
    public void start() {
        Direction direction = this.mob.getMovementDirection();
        this.mob.setVelocity(this.mob.getVelocity().add((double)direction.getOffsetX() * 0.6D, 0.7D, (double)direction.getOffsetZ() * 0.6D));
        this.mob.getNavigation().stop();
    }

    @Override
    public void stop() {
        this.mob.setPitch(0.0F);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick() {
        boolean bl = this.inWater;
        if (!bl) {
            FluidState fluidState = this.mob.world.getFluidState(this.mob.getBlockPos());
            this.inWater = fluidState.isIn(FluidTags.WATER);
        }

        if (this.inWater && !bl) {
            this.mob.playSound(SoundEvents.ENTITY_DOLPHIN_JUMP, 1.0F, 1.0F);
        }

        Vec3d vec3d = this.mob.getVelocity();
        if (vec3d.y * vec3d.y < 0.029999999329447746D && this.mob.getPitch() != 0.0F) {
            this.mob.setPitch(MathHelper.lerpAngle(this.mob.getPitch(), 0.0F, 0.2F));
        } else if (vec3d.length() > 9.999999747378752E-6D) {
            double d = vec3d.horizontalLength();
            double e = Math.atan2(-vec3d.y, d) * 57.2957763671875D;
            this.mob.setPitch((float)e);
        }

    }
}

