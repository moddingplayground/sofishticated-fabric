package com.ninni.sofishticated.entity;

import com.ninni.sofishticated.entity.enums.ButterflyFishVariant;
import com.ninni.sofishticated.init.SofishticatedItems;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ButterflyFishEntity extends AbstractSubmissiveFishEntity{
    private static final TrackedData<String> VARIANT = DataTracker.registerData(ButterflyFishEntity.class, TrackedDataHandlerRegistry.STRING);

    public ButterflyFishEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
        if (entityTag != null && entityTag.contains("ButterflyFishBucketVariantTag")) {
            this.setVariant(ButterflyFishVariant.valueOf(entityTag.getString("ButterflyFishBucketVariantTag")));
        } else {
            this.setVariant(ButterflyFishVariant.values()[this.random.nextInt(ButterflyFishVariant.values().length)]);
        }

        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.25F;
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
    public void copyDataToStack(ItemStack stack) {
        super.copyDataToStack(stack);
        NbtCompound tag = stack.getOrCreateNbt();
        tag.putString("ButterflyFishBucketVariantTag", this.getVariant().toString());
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, ButterflyFishVariant.getDefault().toString());
    }

    public ButterflyFishVariant getVariant() {
        return ButterflyFishVariant.valueOf(this.dataTracker.get(VARIANT));
    }
    public void setVariant(ButterflyFishVariant variant) {
        this.dataTracker.set(VARIANT, variant.toString());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("Variant", this.getVariant().toString());
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setVariant(ButterflyFishVariant.valueOf(nbt.getString("Variant")));
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(SofishticatedItems.BUTTERFLY_FISH_BUCKET);
    }
}
