package net.moddingplayground.sofishticated.api.entity;

import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.moddingplayground.sofishticated.api.item.SofishticatedItems;
import net.moddingplayground.sofishticated.api.particle.SofishticatedParticleTypes;
import net.moddingplayground.sofishticated.api.sound.SofishticatedSoundEvents;
import net.moddingplayground.sofishticated.api.tag.SofishticatedItemTags;

public class AnglerFishEntity extends TiltingFishEntity implements Bucketable, DeepLurker, FlopConditionable {
    private static final Ingredient TEMPT_INGREDIENT = Ingredient.fromTag(SofishticatedItemTags.ANGLER_FISH_TEMPTS);
    private static final TrackedData<Integer> EFFECT_TICKS = DataTracker.registerData(AnglerFishEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private boolean lastDeflated;
    private long lastDeflationSound = 100;

    public AnglerFishEntity(EntityType<? extends AnglerFishEntity> type, World world) {
        super(type, world);
    }

    public boolean isDeflated() {
        return this.getAir() < this.getMaxAir() - 4 || !this.isAlive();
    }

    /* Initialization */

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new TemptGoal(this, 1.25F, TEMPT_INGREDIENT, false));
    }

    public static DefaultAttributeContainer.Builder createAnglerFishAttributes() {
        return MobEntity.createMobAttributes()
                        .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D);
    }

    public int getEffectTicks() {
        return this.dataTracker.get(EFFECT_TICKS);
    }
    public void setEffectTicks(int effectTicks) {
        this.dataTracker.set(EFFECT_TICKS, effectTicks);
    }

    /* Live Code */

    @Override
    public void tick() {
        super.tick();

        if (!this.world.isClient) {
            boolean deflated = this.isDeflated();
            if (deflated && !this.lastDeflated) {
                long time = this.world.getTime();
                if (time - this.lastDeflationSound >= 15) {
                    this.world.playSoundFromEntity(null, this, SofishticatedSoundEvents.ENTITY_ANGLER_FISH_DEFLATE, this.getSoundCategory(), this.getSoundVolume(), this.getSoundPitch());
                    this.lastDeflationSound = time;
                }
            }
            this.lastDeflated = deflated;
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.world.isClient && this.isSubmergedInWater() && this.age % 2 == 0) {
            this.world.addParticle(SofishticatedParticleTypes.ANGLER_FISH_LANTERN_GLOW,
                this.getParticleX(0.15D), this.getBodyY(1.25D), this.getParticleZ(0.15D),
                0.0D, 0.0D, 0.0D
            );
        }
        if(getEffectTicks() > 0) {
            this.setEffectTicks(this.getEffectTicks() - 1);
        }
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.GLOW_INK_SAC) && this.getEffectTicks() == 0 && this.isSubmergedInWater()){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 40 * 20, 0, false, true));
            this.setEffectTicks(60 * 20);
            //ToDo: add sound effect and particles when the player tries to get night vision and the fish is on cooldown
            if (!player.isCreative()) itemStack.decrement(1);
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    @Override
    public boolean doesFlopWhileOutOfWater() {
        int air = this.getAir();
        return air % (air > 40 ? 15 : 10) + this.random.nextInt(5) == 0 && this.isAlive();
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return this.getLurkingPathfindingFavor(pos, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(EFFECT_TICKS, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("EffectTicks", this.getEffectTicks());
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setEffectTicks(nbt.getInt("EffectTicks"));
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
