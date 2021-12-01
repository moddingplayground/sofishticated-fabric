package com.ninni.sofishticated.entity;

import com.ninni.sofishticated.entity.common.TiltingFishEntity;
import com.ninni.sofishticated.init.SofishticatedItems;
import com.ninni.sofishticated.init.SofishticatedSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class GarEntity extends TiltingFishEntity {

    public GarEntity(EntityType<? extends TiltingFishEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, ButterflyFishEntity.class, false));
        this.goalSelector.add(1, new GarEntity.AttackGoal(1.5D, true));
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SofishticatedSoundEvents.ENTITY_FISH_FLOP;
    }

    public static DefaultAttributeContainer.Builder createGarAttributes() {
        return MobEntity.createMobAttributes()
                        .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
                        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0D)
                        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.75D);
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(SofishticatedItems.PIRANHA_BUCKET);
    }

    private class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(double speed, boolean pauseWhenIdle) {
            super(GarEntity.this, speed, pauseWhenIdle);
        }

        @Override
        protected void attack(LivingEntity target, double squaredDistance) {
            double d = this.getSquaredMaxAttackDistance(target);
            if (squaredDistance <= d && this.isCooledDown()) {
                this.resetCooldown();
                this.mob.tryAttack(target);
            }

        }
    }
}
