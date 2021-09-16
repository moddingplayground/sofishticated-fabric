package com.ninni.sofishticated.entity;

import com.ninni.sofishticated.entity.ai.goal.CustomSwimAroundGoal;
import com.ninni.sofishticated.init.SofishticatedEntities;
import com.ninni.sofishticated.init.SofishticatedSoundEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.AquaticLookControl;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.goal.*;
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
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BigEyedEntity extends AnimalEntity {
    private static final TrackedData<Integer> MOISTNESS = DataTracker.registerData(MantaRayEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.fromTag(ItemTags.FISHES);

    public BigEyedEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        this.moveControl = new AquaticMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new AquaticLookControl(this, 10);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new MoveIntoWaterGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(3, new TemptGoal(this, 1.2D, BREEDING_INGREDIENT, false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.add(5, new CustomSwimAroundGoal(this, 1.0D, 10));
        this.goalSelector.add(6, new LookAroundGoal(this));

        this.targetSelector.add(0, new RevengeGoal(this));
    }

    public static DefaultAttributeContainer.Builder createBigEyedAttributes() {
        return MobEntity.createMobAttributes()
                        .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
                        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D)
                        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.2D);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.5F;
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean bl = target.damage(DamageSource.mob(this), (float)((int)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
        if (bl) {
            this.applyDamageEffects(this, target);
            this.playSound(SofishticatedSoundEvents.ENTITY_BIG_EYED_ATTACK, 1.0F, 0.6F);
        }

        return bl;
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
        return SofishticatedEntities.BIG_EYED.create(world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(MOISTNESS, 2400);
    }
    public int getMoistness() {
        return this.dataTracker.get(MOISTNESS);
    }
    public void setMoistness(int moistness) {
        this.dataTracker.set(MOISTNESS, moistness);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setMoistness(nbt.getInt("Moistness"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Moistness", this.getMoistness());
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SwimNavigation(this, world);
    }

    @Override
    public int getLookPitchSpeed() {
        return 1;
    }

    @Override
    public int getBodyYawSpeed() {
        return 1;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAiDisabled()) {
            this.setAir(this.getMaxAir());
        } else {
            if (this.isWet()) {
                this.setMoistness(2400);
            } else {
                this.setMoistness(this.getMoistness() - 1);
                if (this.getMoistness() <= 0) {
                    this.damage(DamageSource.DRYOUT, 1.0F);
                }

                if (this.onGround) {
                    this.setVelocity(this.getVelocity().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F, 0.5D, (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F));
                    this.setYaw(this.random.nextFloat() * 360.0F);
                    this.onGround = false;
                    this.velocityDirty = true;
                }
            }
        }
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
            this.setVelocity(this.getVelocity().multiply(0.8D));
            if (this.getTarget() == null) {
                this.setVelocity(this.getVelocity().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(movementInput);
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
    public static boolean canSpawn(EntityType<BigEyedEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos).isOf(Blocks.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER);
    }
}
