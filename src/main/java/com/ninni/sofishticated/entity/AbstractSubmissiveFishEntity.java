package com.ninni.sofishticated.entity;

import com.ninni.sofishticated.init.SofishticatedSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.AquaticLookControl;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MoveIntoWaterGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public abstract class AbstractSubmissiveFishEntity extends WaterCreatureEntity implements Bucketable {
    private static final TrackedData<Boolean> FROM_BUCKET = DataTracker.registerData(FishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    protected AbstractSubmissiveFishEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        this.moveControl = new AquaticMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new AquaticLookControl(this, 10);
        this.stepHeight = 0;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new MoveIntoWaterGoal(this));
        this.goalSelector.add(4, new SwimAroundGoal(this, 1.0D, 10));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_FISH_SWIM;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SofishticatedSoundEvents.ENTITY_FISH_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SofishticatedSoundEvents.ENTITY_SHRIMP_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        return Bucketable.tryBucket(player, hand, this).orElse(super.interactMob(player, hand));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void copyDataToStack(ItemStack stack) {
        Bucketable.copyDataToStack(this, stack);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
    }

    @Override
    public SoundEvent getBucketedSound() {
        return SoundEvents.ITEM_BUCKET_FILL_FISH;
    }


    @Override
    protected void tickWaterBreathingAir(int air) {
        if (this.isAlive() && !this.isInsideWaterOrBubbleColumn()) {
            this.setAir(air - 1);
            if (this.getAir() == -20) {
                this.setAir(0);
                this.damage(DamageSource.DROWN, 2.0F);
            }
        } else {
            this.setAir(300);
        }

    }

    @Override
    public void baseTick() {
        int i = this.getAir();
        super.baseTick();
        this.tickWaterBreathingAir(i);
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.AQUATIC;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.65F;
    }

    public static DefaultAttributeContainer.Builder createTiltingFishAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 18.0D);
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBucket();
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<? extends AbstractSubmissiveFishEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos).isOf(Blocks.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isFromBucket() && !this.hasCustomName();
    }

    @Override
    public int getLimitPerChunk() {
        return 8;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FROM_BUCKET, false);
    }

    @Override
    public boolean isFromBucket() {
        return this.dataTracker.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.dataTracker.set(FROM_BUCKET, fromBucket);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("FromBucket", this.isFromBucket());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setFromBucket(nbt.getBoolean("FromBucket"));
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.updateVelocity(this.getMovementSpeed(), movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9D));
            if (this.getTarget() == null) {
                this.setVelocity(this.getVelocity().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(movementInput);
        }
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SwimNavigation(this, world);
    }


    @Override
    public void tick() {
        super.tick();
        if (this.isAiDisabled()) {
            this.setAir(this.getMaxAir());
        } else {
                if (this.onGround) {
                    this.setVelocity(this.getVelocity().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F, 0.5D, (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F));
                    this.setYaw(this.random.nextFloat() * 360.0F);
                    this.onGround = false;
                    this.velocityDirty = true;
                }
            }
    }
}
