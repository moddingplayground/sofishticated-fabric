package com.ninni.sofishticated.entity;

import com.ninni.sofishticated.init.SofishticatedItems;
import com.ninni.sofishticated.init.SofishticatedSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class ReefSharkEntity extends AbstractSubmissiveFishEntity {

    public ReefSharkEntity(EntityType<? extends AbstractSubmissiveFishEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.targetSelector.add(0, new RevengeGoal(this));
        this.goalSelector.add(1, new ReefSharkEntity.AttackGoal(1.2D, true));
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.getEquippedStack(EquipmentSlot.MAINHAND).isIn(ItemTags.FISHES) && !player.isSpectator()) {
            this.playSound(SofishticatedSoundEvents.ENTITY_SHARK_BITE, 0.6F, 0.5F);

            if (random.nextFloat() > 0.9F) {
                this.dropItem(SofishticatedItems.SHARK_TOOTH);
                this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1F, 1F);
            }

            Vec3d vec3d = this.getBoundingBox().getCenter();
            Random random = this.world.getRandom();
            for(int i = 0; i < 10; ++i) {
                double velX = random.nextGaussian() * 0.075D;
                double velY = random.nextGaussian() * 0.075D;
                double velZ = random.nextGaussian() * 0.075D;
                this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, player.getStackInHand(hand)), vec3d.x, vec3d.y, vec3d.z, velX, velY, velZ);
            }

            if (!player.isCreative()) {
                player.getStackInHand(hand).decrement(1);
            }

            return ActionResult.success(world.isClient);
        }

        return super.interactMob(player, hand);
    }

    public static DefaultAttributeContainer.Builder createReefSharkAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
                                              .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0D);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(SofishticatedItems.REEF_SHARK_BUCKET);
    }

    private class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(double speed, boolean pauseWhenIdle) {
            super(ReefSharkEntity.this, speed, pauseWhenIdle);
        }

        @Override
        protected void attack(LivingEntity target, double squaredDistance) {
            double d = this.getSquaredMaxAttackDistance(target);
            if (squaredDistance <= d && this.isCooledDown() && this.mob.isSubmergedInWater()) {
                this.resetCooldown();
                this.mob.tryAttack(target);
                ReefSharkEntity.this.playSound(SofishticatedSoundEvents.ENTITY_SHARK_BITE, 0.8F, 0.5F);
            }

        }
    }
}
