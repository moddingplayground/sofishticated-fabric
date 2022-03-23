package net.moddingplayground.sofishticated.api.entity;

import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.moddingplayground.sofishticated.api.item.SofishticatedItems;
import net.moddingplayground.sofishticated.api.sound.SofishticatedSoundEvents;

public class AnglerFishEntity extends FishEntity implements Bucketable {
    public AnglerFishEntity(EntityType<? extends AnglerFishEntity> type, World world) {
        super(type, world);
    }

    /* Initialization */

    public static DefaultAttributeContainer.Builder createAnglerFishAttributes() {
        return MobEntity.createMobAttributes()
                        .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0D);
    }

    /* Ticking */

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.world.isClient && this.isSubmergedInWater()) {
            this.world.addParticle(ParticleTypes.GLOW, this.getParticleX(0.15D), this.getBodyY(1.25D), this.getParticleZ(0.15D), 0.0D, 0.0D, 0.0D);
        }
    }

    /* Miscellaneous Overrides */

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(SofishticatedItems.ANGLER_FISH_BUCKET);
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

    @Override
    protected SoundEvent getFlopSound() {
        return SofishticatedSoundEvents.ENTITY_FISH_FLOP;
    }
}
