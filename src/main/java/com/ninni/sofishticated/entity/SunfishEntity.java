package com.ninni.sofishticated.entity;

import com.ninni.sofishticated.init.SofishticatedSoundEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;

public class SunfishEntity extends WaterCreatureEntity {

    public SunfishEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new SunfishEntity.SunfishMoveControl(this);
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return true;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.AQUATIC;
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn();
    }

    public static DefaultAttributeContainer.Builder createSunfishAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 32.0D);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.hasCustomName();
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.65F;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25D));
        Predicate<Entity> var10009 = EntityPredicates.EXCEPT_SPECTATOR;
        Objects.requireNonNull(var10009);
        this.goalSelector.add(2, new FleeEntityGoal<>(this, PlayerEntity.class, 8.0F, 1.6D, 1.4D, var10009::test));
        this.goalSelector.add(4, new SunfishEntity.SwimToRandomPlaceGoal(this));
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SwimNavigation(this, world);
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.updateVelocity(0.01F, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9D));
            if (this.getTarget() == null) {
                this.setVelocity(this.getVelocity().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(movementInput);
        }

    }

    protected boolean hasSelfControl() {
        return true;
    }

    static class SunfishMoveControl extends MoveControl {
        private final SunfishEntity fish;

        SunfishMoveControl(SunfishEntity owner) {
            super(owner);
            this.fish = owner;
        }

        @Override
        public void tick() {
            if (this.fish.isSubmergedIn(FluidTags.WATER)) {
                this.fish.setVelocity(this.fish.getVelocity().add(0.0D, 0.005D, 0.0D));
            }

            if (this.state == State.MOVE_TO && !this.fish.getNavigation().isIdle()) {
                float f = (float)(this.speed * this.fish.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                this.fish.setMovementSpeed(MathHelper.lerp(0.125F, this.fish.getMovementSpeed(), f));
                double d = this.targetX - this.fish.getX();
                double e = this.targetY - this.fish.getY();
                double g = this.targetZ - this.fish.getZ();
                if (e != 0.0D) {
                    double h = Math.sqrt(d * d + e * e + g * g);
                    this.fish.setVelocity(this.fish.getVelocity().add(0.0D, (double)this.fish.getMovementSpeed() * (e / h) * 0.1D, 0.0D));
                }

                if (d != 0.0D || g != 0.0D) {
                    float i = (float)(MathHelper.atan2(g, d) * 57.2957763671875D) - 90.0F;
                    this.fish.setYaw(this.wrapDegrees(this.fish.getYaw(), i, 90.0F));
                    this.fish.bodyYaw = this.fish.getYaw();
                }

            } else {
                this.fish.setMovementSpeed(0.0F);
            }
        }
    }

    static class SwimToRandomPlaceGoal extends SwimAroundGoal {
        private final SunfishEntity fish;

        public SwimToRandomPlaceGoal(SunfishEntity fish) {
            super(fish, 1.0D, 40);
            this.fish = fish;
        }

        @Override
        public boolean canStart() {
            return this.fish.hasSelfControl() && super.canStart();
        }
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_FISH_SWIM;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SofishticatedSoundEvents.ENTITY_FISH_DEATH;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SofishticatedSoundEvents.ENTITY_FISH_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SofishticatedSoundEvents.ENTITY_FISH_HURT;
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<SunfishEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos).isOf(Blocks.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER);
    }
}
