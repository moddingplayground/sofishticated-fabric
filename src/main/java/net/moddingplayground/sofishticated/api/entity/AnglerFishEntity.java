package net.moddingplayground.sofishticated.api.entity;

import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.moddingplayground.sofishticated.api.item.SofishticatedItems;
import net.moddingplayground.sofishticated.api.particle.SofishticatedParticleTypes;
import net.moddingplayground.sofishticated.api.sound.SofishticatedSoundEvents;
import net.moddingplayground.sofishticated.api.tag.SofishticatedItemTags;

public class AnglerFishEntity extends TiltingFishEntity implements Bucketable, DeepLurker, FlopConditionable {
    private static final Ingredient TEMPT_INGREDIENT = Ingredient.fromTag(SofishticatedItemTags.ANGLER_FISH_TEMPTS);

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

    /* Live Code */

    @Override
    public void tick() {
        super.tick();

        if (!this.world.isClient) {
            boolean deflated = this.isDeflated();
            if (deflated && deflated != this.lastDeflated) {
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
}
