package com.ninni.sofishticated.entity;

import com.ninni.sofishticated.entity.enums.ShrimpVariant;
import com.ninni.sofishticated.init.SofishticatedEntities;
import com.ninni.sofishticated.init.SofishticatedItems;
import com.ninni.sofishticated.init.SofishticatedSoundEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ShrimpEntity extends AnimalEntity implements Bucketable {
    private static final TrackedData<String> VARIANT = DataTracker.registerData(ShrimpEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Blocks.SEAGRASS.asItem());
    private static final TrackedData<Boolean> FROM_BUCKET = DataTracker.registerData(ShrimpEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public ShrimpEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new MoveIntoWaterGoal(this));
        this.goalSelector.add(2, new EscapeDangerGoal(this, 0.25));
        this.goalSelector.add(3, new AnimalMateGoal(this, 0.25));
        this.goalSelector.add(4, new TemptGoal(this, 0.25, Ingredient.ofItems(Blocks.SEAGRASS.asItem()), false));
        this.goalSelector.add(4, new TemptGoal(this, 0.25, BREEDING_INGREDIENT, false));
        this.goalSelector.add(5, new FollowParentGoal(this, 0.25));
        this.goalSelector.add(6, new SwimAroundGoal(this, 0.4D, 10));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new FleeEntityGoal<>(this, PlayerEntity.class, 8.0F, 0.25, 0.25));

    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    protected void tickWaterBreathingAir(int air) {
        if (this.isAlive() && !this.isInsideWaterOrBubbleColumn()) {
            this.setAir(air - 1);
            if (this.getAir() == -20) {
                this.setAir(0);
                this.damage(DamageSource.DROWN, 2.0F);
            }
        } else {
            this.setAir(600);
        }

    }
    @Override
    public void baseTick() {
        int i = this.getAir();
        super.baseTick();
        this.tickWaterBreathingAir(i);
    }

    public static DefaultAttributeContainer.Builder createShrimpAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0D);
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        if (entityTag != null && entityTag.contains("ShrimpBucketVariantTag")) {
            this.setVariant(ShrimpVariant.valueOf(entityTag.getString("ShrimpBucketVariantTag")));
        } else {
            this.setVariant(ShrimpVariant.values()[this.random.nextInt(ShrimpVariant.values().length)]);
        }

        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
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
    public EntityGroup getGroup() {
        return EntityGroup.AQUATIC;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FROM_BUCKET, false);
        this.dataTracker.startTracking(VARIANT, ShrimpVariant.getDefault().toString());
    }

    public ShrimpVariant getVariant() {
        return ShrimpVariant.valueOf(this.dataTracker.get(VARIANT));
    }
    public void setVariant(ShrimpVariant variant) {
        this.dataTracker.set(VARIANT, variant.toString());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putString("Variant", this.getVariant().toString());
        tag.putBoolean("FromBucket", this.isFromBucket());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.setVariant(ShrimpVariant.valueOf(tag.getString("Variant")));
        this.setFromBucket(tag.getBoolean("FromBucket"));
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBucket();
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    @Override
    protected float getSoundVolume() {
        return 0.18f;
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
    public ItemStack getBucketItem() {
        return new ItemStack(SofishticatedItems.SHRIMP_BUCKET);
    }

    @Override
    public SoundEvent getBucketedSound() {
        return SoundEvents.ITEM_BUCKET_EMPTY_FISH;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SofishticatedSoundEvents.ENTITY_SHRIMP_DEATH;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SofishticatedSoundEvents.ENTITY_SHRIMP_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SofishticatedSoundEvents.ENTITY_SHRIMP_HURT;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity other) {
        ShrimpEntity entity = SofishticatedEntities.SHRIMP.create(world);
        if (entity != null) {
            entity.setVariant(this.getVariant());
        }
        return entity;
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<? extends ShrimpEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos).isOf(Blocks.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER);
    }
}
