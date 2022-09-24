package net.moddingplayground.sofishticated.api.entity;

import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
import net.moddingplayground.sofishticated.api.util.EntityHelper;

public class AnglerFishEntity extends TiltingFishEntity implements Bucketable, DeepLurker, Flops {
    public static final Ingredient TEMPT_INGREDIENT = Ingredient.fromTag(SofishticatedItemTags.ANGLER_FISH_TEMPTS);
    public static final String LAST_EFFECT_GIVEN_KEY = "LastEffectGiven";

    public static final int
        EFFECT_DURATION = 40,
        EFFECT_DELAY = EFFECT_DURATION + 20;

    private boolean lastDeflated;
    private long lastDeflationSound;
    private long lastEffectGiven;

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
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D);
    }

    /* Live Code */

    @Override
    public void tick() {
        super.tick();

        if (!this.world.isClient) {
            boolean deflated = this.isDeflated();
            if (deflated && !this.lastDeflated) {
                long time = this.world.getTime();
                if (this.lastDeflationSound == 0 || time - this.lastDeflationSound >= 15) {
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
            EntityHelper.addParticle(this, SofishticatedParticleTypes.ANGLER_FISH_LANTERN_GLOW, 0.15D, 1.25D);
        }
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!this.world.isClient) {
            if (stack.isIn(SofishticatedItemTags.ANGLER_FISH_FEEDS) && !this.isDeflated() && !player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
                long time = this.world.getTime();
                if (this.lastEffectGiven == 0 || time - this.lastEffectGiven > EFFECT_DELAY * 20) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, EFFECT_DURATION * 20, 0, false, true));
                    if (!player.getAbilities().creativeMode) stack.decrement(1);

                    // add visual effects
                    EntityHelper.playSoundFromEntity(this, SofishticatedSoundEvents.ENTITY_ANGLER_FISH_EFFECT_GIVE);
                    EntityHelper.spawnParticles(player, SofishticatedParticleTypes.ANGLER_FISH_LANTERN_GLOW, 0.1D, 0.5D, 0.25D, 40, 1.0D);

                    this.lastEffectGiven = time;
                } else {
                    EntityHelper.playSoundFromEntity(this, SofishticatedSoundEvents.ENTITY_ANGLER_FISH_EFFECT_DENY);
                    EntityHelper.spawnParticles(this, SofishticatedParticleTypes.ANGLER_FISH_LANTERN_GLOW, 0.05D, 0.5D, 0.25D, 10, 0.0D);
                }

                return ActionResult.SUCCESS;
            }
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

    /* NBT */

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putLong(LAST_EFFECT_GIVEN_KEY, this.lastEffectGiven);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.lastEffectGiven = nbt.getLong(LAST_EFFECT_GIVEN_KEY);
    }
}
