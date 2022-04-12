package net.moddingplayground.sofishticated.api.entity;

import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MoveIntoWaterGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.moddingplayground.sofishticated.api.entity.variant.SeahorseVariant;
import net.moddingplayground.sofishticated.api.item.SofishticatedItems;
import net.moddingplayground.sofishticated.api.registry.SofishticatedRegistry;
import net.moddingplayground.sofishticated.api.sound.SofishticatedSoundEvents;
import org.jetbrains.annotations.Nullable;

import static net.moddingplayground.sofishticated.api.entity.VariantHelper.*;

public class SeahorseEntity extends FishEntity implements Bucketable {
    public static final TrackedData<String> VARIANT = DataTracker.registerData(SeahorseEntity.class, TrackedDataHandlerRegistry.STRING);

    public SeahorseEntity(EntityType<? extends SeahorseEntity> type, World world) {
        super(type, world);
    }

    /* Initialization */

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason reason, @Nullable EntityData data, @Nullable NbtCompound nbt) {
        if (reason != SpawnReason.BUCKET) {
            SofishticatedRegistry.SEAHORSE_VARIANT.getRandom(this.random)
                                                  .map(RegistryEntry::value)
                                                  .ifPresent(this::setVariant);
        }
        return super.initialize(world, difficulty, reason, data, nbt);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, SofishticatedRegistry.SEAHORSE_VARIANT.getDefaultId().toString());
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new MoveIntoWaterGoal(this));
        this.goalSelector.add(1, new SwimAroundGoal(this, 1.0D, 10));
        this.goalSelector.add(2, new LookAroundGoal(this));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
    }

    public static DefaultAttributeContainer.Builder createSeahorseAttributes() {
        return MobEntity.createMobAttributes()
                        .add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0D)
                        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.65D);
    }

    /* Getters/Setters */

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.95F;
    }

    public SeahorseVariant getVariant() {
        return SofishticatedRegistry.SEAHORSE_VARIANT.get(Identifier.tryParse(this.dataTracker.get(VARIANT)));
    }

    public void setVariant(SeahorseVariant variant) {
        this.dataTracker.set(VARIANT, variant.toString());
    }

    /* Miscellaneous Overrides */

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(SofishticatedItems.SEAHORSE_BUCKET);
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_FISH_SWIM;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SofishticatedSoundEvents.ENTITY_SEAHORSE_DEATH;
    }

    @Override
    public SoundEvent getAmbientSound() {
        if (this.isSubmergedInWater()) {
            if (this.random.nextInt(3) == 0) return SofishticatedSoundEvents.ENTITY_SEAHORSE_GROWL;
            return SofishticatedSoundEvents.ENTITY_SEAHORSE_CLICK;
        }
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SofishticatedSoundEvents.ENTITY_SEAHORSE_HURT;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SofishticatedSoundEvents.ENTITY_FISH_FLOP;
    }

    /* NBT */

    @Override
    public void copyDataToStack(ItemStack stack) {
        super.copyDataToStack(stack);
        NbtCompound nbt = stack.getOrCreateNbt();
        this.getVariant().writeNbt(nbt, KEY_BUCKET_VARIANT_TAG);
    }

    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        super.copyDataFromNbt(nbt);
        this.setVariant(SeahorseVariant.readNbt(nbt, KEY_BUCKET_VARIANT_TAG));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.getVariant().writeNbt(nbt, KEY_VARIANT);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setVariant(SeahorseVariant.readNbt(nbt, KEY_VARIANT));
    }
}
