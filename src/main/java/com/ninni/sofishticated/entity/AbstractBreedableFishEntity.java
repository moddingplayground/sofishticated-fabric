package com.ninni.sofishticated.entity;

import com.ninni.sofishticated.init.SofishticatedSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public abstract class AbstractBreedableFishEntity extends AnimalEntity implements Bucketable {
    private static final TrackedData<Boolean> FROM_BUCKET = DataTracker.registerData(FishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.SEAGRASS);

    protected AbstractBreedableFishEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new AbstractBreedableFishEntity.FishMoveControl(this);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(2, new TemptGoal(this, 1.2D, BREEDING_INGREDIENT, false));
        this.goalSelector.add(3, new FollowParentGoal(this, 1.1D));
        this.goalSelector.add(4, new AbstractBreedableFishEntity.SwimToRandomPlaceGoal(this));
    }

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

    public static DefaultAttributeContainer.Builder createBreedableFishAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D);
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBucket();
    }

    public static boolean canSpawn(EntityType<? extends AbstractBreedableFishEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
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
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
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

    @Override
    public void tickMovement() {
        if (!this.isTouchingWater() && this.onGround && this.verticalCollision) {
            this.setVelocity(this.getVelocity().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4000000059604645D, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
            this.onGround = false;
            this.velocityDirty = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getSoundPitch());
        }

        super.tickMovement();
    }

    private SoundEvent getFlopSound() {
        return SofishticatedSoundEvents.ENTITY_FISH_FLOP;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_FISH_SWIM;
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

    private static class FishMoveControl extends MoveControl {
        private final AbstractBreedableFishEntity fish;

        FishMoveControl(AbstractBreedableFishEntity owner) {
            super(owner);
            this.fish = owner;
        }

        @Override
        public void tick() {
            if (this.fish.isSubmergedIn(FluidTags.WATER)) {
                this.fish.setVelocity(this.fish.getVelocity().add(0.0D, 0.005D, 0.0D));
            }

            if (this.state == MoveControl.State.MOVE_TO && !this.fish.getNavigation().isIdle()) {
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

    protected boolean hasSelfControl() {
        return true;
    }

    private static class SwimToRandomPlaceGoal extends SwimAroundGoal {
        private final AbstractBreedableFishEntity fish;

        public SwimToRandomPlaceGoal(AbstractBreedableFishEntity fish) {
            super(fish, 1.0D, 40);
            this.fish = fish;
        }

        @Override
        public boolean canStart() {
            return this.fish.hasSelfControl() && super.canStart();
        }
    }
}
