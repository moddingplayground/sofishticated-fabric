package net.moddingplayground.sofishticated.api.entity;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MoveIntoWaterGoal;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
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
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.moddingplayground.sofishticated.api.Sofishticated;
import net.moddingplayground.sofishticated.api.item.SofishticatedItems;
import net.moddingplayground.sofishticated.api.sound.SofishticatedSoundEvents;
import net.moddingplayground.sofishticated.api.tag.SofishticatedBlockTags;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

@SuppressWarnings("deprecation")
public class ShrimpEntity extends AnimalEntity implements Bucketable, VariantHelper {
    public static final TrackedData<String> VARIANT = DataTracker.registerData(ShrimpEntity.class, TrackedDataHandlerRegistry.STRING);
    public static final TrackedData<Boolean> FROM_BUCKET = DataTracker.registerData(ShrimpEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Blocks.SEAGRASS);

    public ShrimpEntity(EntityType<? extends ShrimpEntity> type, World world) {
        super(type, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        this.stepHeight = 1;
    }

    /* Initialization */

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason reason, @Nullable EntityData data, @Nullable NbtCompound nbt) {
        if (reason != SpawnReason.BUCKET) this.setVariant(VariantHelper.getRandom(Variant.class, this.random));
        return super.initialize(world, difficulty, reason, data, nbt);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new MoveIntoWaterGoal(this));
        this.goalSelector.add(2, new EscapeDangerGoal(this, 0.5));
        this.goalSelector.add(3, new AnimalMateGoal(this, 1));
        this.goalSelector.add(4, new TemptGoal(this, 0.5, Ingredient.ofItems(Blocks.SEAGRASS.asItem()), false));
        this.goalSelector.add(4, new TemptGoal(this, 0.5, BREEDING_INGREDIENT, false));
        this.goalSelector.add(5, new FollowParentGoal(this, 0.5));
        this.goalSelector.add(6, new SwimAroundGoal(this, 0.4D, 10));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        // TODO this.goalSelector.add(8, new FleeEntityGoal<>(this, PiranhaEntity.class, 8.0F, 0.45, 0.5));
        this.goalSelector.add(9, new FleeEntityGoal<>(this, PlayerEntity.class, 8.0F, 0.25, 0.5));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FROM_BUCKET, false);
        this.dataTracker.startTracking(VARIANT, VariantHelper.getDefault(Variant.class).toString());
    }

    /* Ticking */

    @Override
    public void baseTick() {
        int air = this.getAir();
        super.baseTick();
        if (!this.isAiDisabled()) this.tickAir(air);

    }

    protected void tickAir(int air) {
        if (this.isAlive() && !this.canBreathe()) {
            this.setAir(air - 1);
            if (this.getAir() == -20) {
                this.setAir(0);
                this.damage(DamageSource.DRYOUT, 1.0F);
            }
        } else this.setAir(this.getMaxAir());
    }

    public boolean canBreathe() {
        if (this.isWet()) return true;
        BlockPos landing = this.getLandingPos();
        BlockState landingState = world.getBlockState(landing);
        return landingState.isIn(SofishticatedBlockTags.MOIST_BLOCKS);
    }

    @Override
    public void travel(Vec3d input) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.updateVelocity(this.getMovementSpeed(), input);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.8D));
            this.setVelocity(this.getVelocity().add(0.0D, -0.025D, 0.0D));
        } else super.travel(input);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() == Items.WATER_BUCKET && this.isAlive() && !this.isBaby()) {
            this.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0F, 1.0F);
            stack.decrement(1);

            ItemStack bucket = this.getBucketItem();
            this.copyDataToStack(bucket);

            if (player instanceof ServerPlayerEntity serverPlayer) Criteria.FILLED_BUCKET.trigger(serverPlayer, bucket);

            if (stack.isEmpty()) {
                player.setStackInHand(hand, bucket);
            } else if (!player.getInventory().insertStack(bucket)) {
                player.dropItem(bucket, false);
            }

            this.discard();
            return ActionResult.success(this.world.isClient);
        }
        return super.interactMob(player, hand);
    }

    /* Getters/Setters */

    public Variant getVariant() {
        return VariantHelper.safeValueOf(Variant.class, this.dataTracker.get(VARIANT));
    }

    public void setVariant(Variant variant) {
        this.dataTracker.set(VARIANT, variant.toString());
    }

    @Override
    public boolean isFromBucket() {
        return this.dataTracker.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.dataTracker.set(FROM_BUCKET, fromBucket);
    }

    /* Miscellaneous Overrides */

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(SofishticatedItems.SHRIMP_BUCKET);
    }

    @Override
    public SoundEvent getBucketedSound() {
        return SoundEvents.ITEM_BUCKET_FILL_FISH;
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public boolean canSpawn(WorldView world) {
        return world.doesNotIntersectEntities(this);
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public int getMaxAir() {
        return 900;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.AQUATIC;
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBucket();
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isFromBucket() && !this.hasCustomName();
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    @Override
    public ShrimpEntity createChild(ServerWorld world, PassiveEntity other) {
        ShrimpEntity entity = SofishticatedEntityType.SHRIMP.create(world);
        return Optional.ofNullable(entity).map(e -> {
            e.setVariant(this.getVariant());
            return e;
        }).orElse(null);
    }

    @Override
    protected float getSoundVolume() {
        return 0.18f;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_FISH_SWIM;
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

    /* NBT */

    @Override
    public void copyDataToStack(ItemStack stack) {
        Bucketable.copyDataToStack(this, stack);
        NbtCompound nbt = stack.getOrCreateNbt();
        VariantHelper.toBucket(this.getVariant(), nbt);
    }

    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        Bucketable.copyDataFromNbt(this, nbt);
        this.setVariant(VariantHelper.fromBucket(Variant.class, nbt, this.random));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        VariantHelper.toNbt(this.getVariant(), nbt);
        nbt.putBoolean(KEY_FROM_BUCKET, this.isFromBucket());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setVariant(VariantHelper.fromNbt(Variant.class, nbt, this.random));
        this.setFromBucket(nbt.getBoolean(KEY_FROM_BUCKET));
    }

    /* Static */

    public static DefaultAttributeContainer.Builder createShrimpAttributes() {
        return MobEntity.createMobAttributes()
                        .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0D)
                        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D);
    }

    public static boolean canSpawn(EntityType<? extends ShrimpEntity> type, WorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        return world.getBlockState(pos).isOf(Blocks.WATER) && world.getBlockState(pos.up()).isOf(Blocks.WATER);
    }

    public enum Variant {
        METALLIC,
        SNOW_WHITE,
        AMBER_PEARL,
        WHITE_PEARL,
        RED_TIGER,
        RED_WINE,
        RED_BEE,
        BLACK_BEE,
        PURPLE_ZEBRA,
        RED_ZEBRA,
        RED_YELLOW_TIGER,
        TANGERINE_TIGER,
        GREEN_APPLE,
        SUPER_WILD_TIGER,
        CHOCOLATE,
        DEEP_BLUE_TIGER,
        ROYAL_BLUE_OE,
        RED_FANCY,
        BLACK_FANCY,
        GOLDEN_BOA,
        RUSTY_RED_TIFER_OE,
        SHOKO,
        GOLDBACK_CARBON_RILI,
        GREEN_EMERALD,
        GREEN_RILI,
        GOLDENBACK_JADE,
        GREEN_JADE,
        GREEN,
        BABAULTI_GREEN,
        TIGER_OE,
        KANOKO,
        RED_ONYX,
        DRAGON_BLOOD,
        BLUE_FANTASY,
        BLUE_DREAM,
        BLUE_DIAMOND,
        BLUE_JELLY,
        BLUE_BOLT,
        SULAWESI_TIGER_CARDINAL,
        ORANGE,
        YELLOW_NEON,
        BLACK_ROSE,
        CARBON_RILI,
        YELLOW_RILI,
        ORANGE_RILI,
        BLUE_RILI,
        CARBON_BLUE_RILI,
        RED_BOLT,
        RED_DEVIL_OE,
        RED_RILI,
        RED_BLUE_RILI,
        BLOODY_MARY;

        private final Identifier texture;

        Variant() {
            String id = this.name().toLowerCase();
            this.texture = new Identifier(Sofishticated.MOD_ID, "textures/entity/shrimp/shrimp_%s.png".formatted(id));
        }

        public Identifier getTexture() {
            return texture;
        }
    }
}
