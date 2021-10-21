package com.ninni.sofishticated.entity;

import com.ninni.sofishticated.entity.ai.goal.CustomSwimAroundGoal;
import com.ninni.sofishticated.init.SofishticatedEntities;
import com.ninni.sofishticated.init.SofishticatedItems;
import com.ninni.sofishticated.init.SofishticatedSoundEvents;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.AquaticLookControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.AmphibiousPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class HorseshoeCrabEntity extends AnimalEntity {
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.fromTag(ItemTags.FISHES);
    private static final TrackedData<Boolean> FROM_BUCKET = DataTracker.registerData(HorseshoeCrabEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public HorseshoeCrabEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new HorseshoeCrabEntity.HorseshoeCrabMoveControl(this);
        this.lookControl = new AquaticLookControl(this, 0);
        this.stepHeight = 1;
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.0D));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(3, new TemptGoal(this, 1.2D, BREEDING_INGREDIENT, false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.2D));
        this.goalSelector.add(5, new CustomSwimAroundGoal(this, 1.0D, 10));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D, 10));
        this.goalSelector.add(6, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createHorseshoeCrabAttributes() {
        return MobEntity.createMobAttributes()
                        .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
                        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D)
                        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D);
    }

    private boolean isFromBucket() {
        return this.dataTracker.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean fromBucket) {
        this.dataTracker.set(FROM_BUCKET, fromBucket);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FROM_BUCKET, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putBoolean("FromBucket", this.isFromBucket());
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.setFromBucket(tag.getBoolean("FromBucket"));
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == Items.WATER_BUCKET && this.isAlive() && !this.isBaby()) {
            this.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0F, 1.0F);
            itemStack.decrement(1);
            ItemStack itemStack2 = this.getFishBucketItem();
            this.copyDataToStack(itemStack2);
            if (!this.world.isClient) {
                Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity)player, itemStack2);
            }

            if (itemStack.isEmpty()) {
                player.setStackInHand(hand, itemStack2);
            } else if (!player.getInventory().insertStack(itemStack2)) {
                player.dropItem(itemStack2, false);
            }

            this.setRemoved(RemovalReason.DISCARDED);
            return ActionResult.success(this.world.isClient);
        } else {
            return super.interactMob(player, hand);
        }
    }

    protected void copyDataToStack(ItemStack stack) {
        if (this.hasCustomName()) {
            stack.setCustomName(this.getCustomName());
        }
    }

    protected ItemStack getFishBucketItem() {
        return new ItemStack(SofishticatedItems.HORSESHOE_CRAB_BUCKET);
    }


    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.5F;
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        if (!this.world.getFluidState(entity.getBlockPos().up(1)).isIn(FluidTags.WATER)) {
            return SofishticatedEntities.HORSESHOE_CRAB.create(world);
        } else {
            this.resetLoveTicks();
            return null;

        }
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.AQUATIC;
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.updateVelocity(this.getMovementSpeed(), movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.6D));
            if (this.getTarget() == null) {
                this.setVelocity(this.getVelocity().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(movementInput);
        }
    }

    @Override
    protected HorseshoeCrabNavigation createNavigation(World world) {
        return new HorseshoeCrabEntity.HorseshoeCrabNavigation(this, world);
    }

    private static class HorseshoeCrabNavigation extends SwimNavigation {
        HorseshoeCrabNavigation(HorseshoeCrabEntity owner, World world) {
            super(owner, world);
        }

        @Override
        protected boolean isAtValidPosition() {
            return true;
        }

        @Override
        protected PathNodeNavigator createPathNodeNavigator(int range) {
            this.nodeMaker = new AmphibiousPathNodeMaker(true);
            this.nodeMaker.setCanOpenDoors(false);
            this.nodeMaker.setCanEnterOpenDoors(false);
            return new PathNodeNavigator(this.nodeMaker, range);
        }

    }

    static class HorseshoeCrabMoveControl extends MoveControl {
        private final HorseshoeCrabEntity crab;

        HorseshoeCrabMoveControl(HorseshoeCrabEntity crab) {
            super(crab);
            this.crab = crab;
        }

        private void updateVelocity() {
            if (this.crab.isSubmergedInWater()) {
                this.crab.setVelocity(this.crab.getVelocity().add(0.0D, -0.005D, 0.0D));
            }
        }

        @Override
        public void tick() {
            this.updateVelocity();
            if (this.state == MoveControl.State.MOVE_TO && !this.crab.getNavigation().isIdle()) {
                double d = this.targetX - this.crab.getX();
                double e = this.targetY - this.crab.getY();
                double f = this.targetZ - this.crab.getZ();
                double g = Math.sqrt(d * d + e * e + f * f);
                e /= g;
                float h = (float)(MathHelper.atan2(f, d) * 57.2957763671875D) - 90.0F;
                this.crab.setYaw(this.wrapDegrees(this.crab.getYaw(), h, 90.0F));
                this.crab.bodyYaw = this.crab.getYaw();
                float i = (float)(this.speed * this.crab.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                this.crab.setMovementSpeed(MathHelper.lerp(0.125F, this.crab.getMovementSpeed(), i));
                this.crab.setVelocity(this.crab.getVelocity().add(0.0D, (double)this.crab.getMovementSpeed() * e * 0.1D, 0.0D));
            } else {
                this.crab.setMovementSpeed(0.0F);
            }
        }
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_FISH_SWIM;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SofishticatedSoundEvents.ENTITY_BIG_EYED_DEATH;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SofishticatedSoundEvents.ENTITY_FISH_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SofishticatedSoundEvents.ENTITY_BIG_EYED_HURT;
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<HorseshoeCrabEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos).isOf(Blocks.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER);
    }
}
