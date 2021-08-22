package com.ninni.sofishticated.entity;

import com.ninni.sofishticated.Sofishticated;
import com.ninni.sofishticated.init.SofishticatedItems;
import com.ninni.sofishticated.init.SofishticatedSoundEvents;
import com.ninni.sofishticated.mixin.MobEntityInvoker;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.AquaticLookControl;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PiranhaEntity extends FishEntity implements Angerable {
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(WolfEntity .class, TrackedDataHandlerRegistry.INTEGER);
    private UUID targetUuid;

    public PiranhaEntity(EntityType<? extends FishEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new AquaticMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new AquaticLookControl(this, 10);
    }

    @Override
    protected void initGoals() {
        this.targetSelector.add(0, new UniversalAngerGoal<>(this, true));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.goalSelector.add(3, new PiranhaEntity.SwimToRandomPlaceGoal(this));
    }

    public static DefaultAttributeContainer.Builder createPiranhaAttributes() {
        return MobEntity.createMobAttributes()
                        .add(EntityAttributes.GENERIC_MAX_HEALTH, 3.0D)
                        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D)
                        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 80.0D)
                        .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.0D);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ANGER_TIME, 0);
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.AQUATIC;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(SofishticatedItems.PIRANHA_BUCKET);
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_FISH_SWIM;
    }

    @Override
    protected float getSoundVolume() {
        return 0.3F;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SofishticatedSoundEvents.ENTITY_FISH_DEATH;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SofishticatedSoundEvents.ENTITY_PIRANHA_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SofishticatedSoundEvents.ENTITY_FISH_HURT;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SofishticatedSoundEvents.ENTITY_FISH_FLOP;
    }



    @Override
    public boolean tryAttack(Entity target) {
        float damage = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        float g = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
        if (target instanceof LivingEntity) {
            damage += EnchantmentHelper.getAttackDamage(this.getMainHandStack(), ((LivingEntity)target).getGroup());
            g += (float)EnchantmentHelper.getKnockback(this);
        }

        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            target.setOnFireFor(i * 4);
        }

        boolean bl = target.damage(new EntityDamageSource(String.format("mob.%s", new Identifier(Sofishticated.MOD_ID, "piranha")), this), damage); // custom damage source
        if (bl) {
            if (g > 0.0F && target instanceof LivingEntity) {
                ((LivingEntity)target).takeKnockback(g * 0.5F, MathHelper.sin(this.getYaw() * 0.017453292F), -MathHelper.cos(this.getYaw() * 0.017453292F));
                this.setVelocity(this.getVelocity().multiply(0.6D, 1.0D, 0.6D));
            }

            if (target instanceof PlayerEntity playerEntity) {
                ((MobEntityInvoker) this).invoke_disablePlayerShield(playerEntity, this.getMainHandStack(), playerEntity.isUsingItem() ? playerEntity.getActiveItem() : ItemStack.EMPTY);
            }

            this.applyDamageEffects(this, target);
            this.onAttacking(target);
        }

        return bl;
    }

    @Override
    public int getAngerTime() {
        return this.dataTracker.get(ANGER_TIME);
    }

    @Override
    public void setAngerTime(int ticks) {
        this.dataTracker.set(ANGER_TIME, ticks);
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return this.targetUuid;
    }

    @Override
    public void setAngryAt(@Nullable UUID uuid) {
        this.targetUuid = uuid;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    @Override
    protected boolean hasSelfControl() {
        return true;
    }

    private static class SwimToRandomPlaceGoal extends SwimAroundGoal {
        private final PiranhaEntity fish;

        public SwimToRandomPlaceGoal(PiranhaEntity fish) {
            super(fish, 1.0D, 40);
            this.fish = fish;
        }

        @Override
        public boolean canStart() {
            return this.fish.hasSelfControl() && super.canStart();
        }
    }
}
