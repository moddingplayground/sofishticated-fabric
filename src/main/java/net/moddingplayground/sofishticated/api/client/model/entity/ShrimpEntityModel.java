package net.moddingplayground.sofishticated.api.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.math.MathHelper;
import net.moddingplayground.sofishticated.api.entity.ShrimpEntity;

import static net.minecraft.client.render.entity.model.EntityModelPartNames.*;
import static net.moddingplayground.sofishticated.api.client.model.SofishticatedEntityModelPartNames.TAIL_FIN;
import static net.moddingplayground.sofishticated.api.client.model.SofishticatedEntityModelPartNames.*;

@Environment(EnvType.CLIENT)
public class ShrimpEntityModel<E extends ShrimpEntity> extends AnimalModel<E> {
    public static final float
        STATIONARY_ANIMATION_SPEED = 8.0f,
        STATIONARY_ANIMATION_DEGREE = 3.5f,
        ANIMATION_SPEED = 4.5f,
        ANIMATION_DEGREE = 1.5f;

    private final ModelPart
        root,
        body,
        face,
        tail,
        tailFin,
        antennae,
        rightLeg,
        leftLeg,
        hands;

    public ShrimpEntityModel(ModelPart root) {
        super(RenderLayer::getEntityTranslucent, true, 16.0F, 0.0F, 2.0F, 2.0F, 24.0F);

        this.root     = root;
        this.body     = this.root.getChild(BODY);
        this.tail     = this.body.getChild(TAIL);
        this.tailFin  = this.tail.getChild(TAIL_FIN);
        this.hands    = this.body.getChild(HANDS);
        this.leftLeg  = this.body.getChild(LEFT_LEG);
        this.rightLeg = this.body.getChild(RIGHT_LEG);
        this.face     = this.body.getChild(FACE);
        this.antennae = this.face.getChild(ANTENNAE);
    }

    public static TexturedModelData getTexturedModelData() {

        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
            BODY,
            ModelPartBuilder.create()
                            .uv(0, 3)
                            .cuboid(-1.5F, 0.0F, -2.5F, 3.0F, 3.0F, 6.0F),
            ModelTransform.pivot(0.0F, 19.5F, -2.0F)
        );

        ModelPartData face = body.addChild(
            FACE,
            ModelPartBuilder.create()
                            .uv(14, 6)
                            .cuboid(-1.5F, -1.0F, -2.0F, 3.0F, 1.0F, 2.0F, new Dilation(-0.01F)),
            ModelTransform.of(0.0F, 1.5F, -2.5F, -0.1745F, 0.0F, 0.0F)
        );

        face.addChild(
            ANTENNAE,
            ModelPartBuilder.create()
                            .uv(0, 12)
                            .cuboid(-1.0F, -7.0F, -2.0F, 2.0F, 7.0F, 3.0F),
            ModelTransform.of(0.0F, -0.9F, -2.4F, -1.1345F, 0.0F, 0.0F)
        );

        ModelPartData tail = body.addChild(
            TAIL,
            ModelPartBuilder.create()
                            .uv(13, 9)
                            .cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 5.0F, new Dilation(0.01F)),
            ModelTransform.of(0.0F, 0.0F, 2.0F, 0.2618F, 0.0F, 0.0F)
        );

        tail.addChild(
            TAIL_FIN,
            ModelPartBuilder.create()
                            .uv(16, 2)
                            .cuboid(-2.5F, 0.0F, 0.0F, 5.0F, 1.0F, 3.0F),
            ModelTransform.of(0.0F, 0.0F, 4.8F, -0.5236F, 0.0F, 0.0F)
        );

        body.addChild(
            RIGHT_LEG,
            ModelPartBuilder.create()
                            .uv(16, 10)
                            .mirrored(true)
                            .cuboid(0.0F, 0.0F, -2.5F, 0.0F, 2.0F, 6.0F),
            ModelTransform.of(-1.0F, 3.0F, -0.5F, 0.0F, 0.0F, 0.7854F)
        );

        body.addChild(
            LEFT_LEG,
            ModelPartBuilder.create()
                            .uv(16, 10)
                            .cuboid(0.0F, 0.0F, -2.5F, 0.0F, 2.0F, 6.0F),
            ModelTransform.of(1.0F, 3.0F, -0.5F, 0.0F, 0.0F, -0.7854F)
        );

        body.addChild(
            HANDS,
            ModelPartBuilder.create()
                            .uv(22, 6)
                            .cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 1.0F, 1.0F),
            ModelTransform.pivot(0.0F, 2.5F, -2.5F)
        );

        return TexturedModelData.of(data, 32, 32);
    }

    @Override
    public void setAngles(E entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        if (entity.getVelocity().length() <= 0.1D && !entity.isSubmergedInWater()) {
            this.tail.pitch = MathHelper.cos(limbAngle * STATIONARY_ANIMATION_SPEED * 0.2F) * STATIONARY_ANIMATION_DEGREE * 0.4F * limbDistance + 0.25F;
            this.tailFin.pitch = MathHelper.cos(limbAngle * STATIONARY_ANIMATION_SPEED * 0.2F) * STATIONARY_ANIMATION_DEGREE * 0.4F * limbDistance - 0.25F;
            this.antennae.pitch = MathHelper.cos(limbAngle * STATIONARY_ANIMATION_SPEED * 0.2F) * STATIONARY_ANIMATION_DEGREE * 0.6F * limbDistance - 1.0F;
            this.body.pitch = MathHelper.cos(limbAngle * STATIONARY_ANIMATION_SPEED * 0.2F) * STATIONARY_ANIMATION_DEGREE * 0.1F * limbDistance;
            this.body.yaw = MathHelper.cos(1.5F + limbAngle * STATIONARY_ANIMATION_SPEED * 0.2F) * STATIONARY_ANIMATION_DEGREE * 0.2F * limbDistance;
            this.hands.pitch = MathHelper.cos(limbAngle * STATIONARY_ANIMATION_SPEED * 0.2F) * STATIONARY_ANIMATION_DEGREE * 0.6F * limbDistance;
            this.rightLeg.roll = MathHelper.cos(3.0F + limbAngle * STATIONARY_ANIMATION_SPEED * 0.6F) * STATIONARY_ANIMATION_DEGREE * 0.4F * limbDistance + 0.8F;
            this.leftLeg.roll = MathHelper.cos(3.0F + limbAngle * STATIONARY_ANIMATION_SPEED * 0.6F) * STATIONARY_ANIMATION_DEGREE * 0.4F * limbDistance - 0.8F;
            this.rightLeg.yaw = MathHelper.cos(3.0F + limbAngle * STATIONARY_ANIMATION_SPEED * 0.6F) * STATIONARY_ANIMATION_DEGREE * 0.1F * limbDistance;
            this.leftLeg.yaw = MathHelper.cos(3.0F + limbAngle * STATIONARY_ANIMATION_SPEED * 0.6F) * STATIONARY_ANIMATION_DEGREE * 0.1F * limbDistance;
        } else {
            this.tail.pitch = MathHelper.cos(limbAngle * ANIMATION_SPEED * 0.2F) * ANIMATION_DEGREE * 0.4F * limbDistance + 0.25F;
            this.tailFin.pitch = MathHelper.cos(limbAngle * ANIMATION_SPEED * 0.2F) * ANIMATION_DEGREE * 0.4F * limbDistance - 0.25F;
            this.antennae.pitch = MathHelper.cos(limbAngle * ANIMATION_SPEED * 0.2F) * ANIMATION_DEGREE * 0.6F * limbDistance - 1.0F;
            this.body.pitch = MathHelper.cos(limbAngle * ANIMATION_SPEED * 0.2F) * ANIMATION_DEGREE * 0.1F * limbDistance;
            this.body.yaw = MathHelper.cos(1.5F + limbAngle * ANIMATION_SPEED * 0.2F) * ANIMATION_DEGREE * 0.2F * limbDistance;
            this.hands.pitch = MathHelper.cos(limbAngle * ANIMATION_SPEED * 0.2F) * ANIMATION_DEGREE * 0.6F * limbDistance;
            this.rightLeg.roll = MathHelper.cos(3.0F + limbAngle * ANIMATION_SPEED * 0.6F) * ANIMATION_DEGREE * 0.4F * limbDistance + 0.8F;
            this.leftLeg.roll = MathHelper.cos(3.0F + limbAngle * ANIMATION_SPEED * 0.6F) * ANIMATION_DEGREE * 0.4F * limbDistance - 0.8F;
            this.rightLeg.yaw = MathHelper.cos(3.0F + limbAngle * ANIMATION_SPEED * 0.6F) * ANIMATION_DEGREE * 0.1F * limbDistance;
            this.leftLeg.yaw = MathHelper.cos(3.0F + limbAngle * ANIMATION_SPEED * 0.6F) * ANIMATION_DEGREE * 0.1F * limbDistance;
        }

        this.root.visible = true;
        this.face.visible = true;
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body);
    }
}
