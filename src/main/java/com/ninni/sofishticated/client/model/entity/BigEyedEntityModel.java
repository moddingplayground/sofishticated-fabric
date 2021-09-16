package com.ninni.sofishticated.client.model.entity;


import com.google.common.collect.ImmutableList;
import com.ninni.sofishticated.entity.BigEyedEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("FieldCanBeLocal, unused")
public class BigEyedEntityModel extends AnimalModel<BigEyedEntity> {
    private final ModelPart root;

    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart tailFlukes;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart leftArm;
    private final ModelPart rightArm;

    public BigEyedEntityModel(ModelPart root) {
        this.root = root;

        this.body       = root.getChild("body");

        this.tail       = body.getChild("tail");
        this.leftArm    = body.getChild("leftArm");
        this.leftLeg    = body.getChild("leftLeg");
        this.rightArm   = body.getChild("rightArm");
        this.rightLeg   = body.getChild("rightLeg");

        this.tailFlukes       = tail.getChild("tailFlukes");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
            "body",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(-4.0F, -5.0F, -11.0F, 8.0F, 10.0F, 22.0F, new Dilation(0.0F))
                            .uv(36, 37)
                            .mirrored(false)
                            .cuboid(-1.0F, 2.0F, -19.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F))
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(0.0F, -10.0F, -2.0F, 0.0F, 5.0F, 9.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 19.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData tail = body.addChild(
            "tail",
            ModelPartBuilder.create()
                            .uv(18, 32)
                            .mirrored(false)
                            .cuboid(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 9.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, -2.0F, 11.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData tailFlukes = tail.addChild(
            "tailFlukes",
            ModelPartBuilder.create()
                            .uv(0, 23)
                            .mirrored(false)
                            .cuboid(0.0F, -8.0F, -1.0F, 0.0F, 14.0F, 9.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 7.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftLeg = body.addChild(
            "leftLeg",
            ModelPartBuilder.create()
                            .uv(12, 0)
                            .mirrored(false)
                            .cuboid(0.0F, 0.0F, -2.0F, 3.0F, 0.0F, 4.0F, new Dilation(0.0F)),
            ModelTransform.of(4.0F, 5.0F, 7.0F, 0.0F, 0.0F, 0.7854F)
        );

        ModelPartData rightLeg = body.addChild(
            "rightLeg",
            ModelPartBuilder.create()
                            .uv(12, 0)
                            .mirrored(true)
                            .cuboid(-3.0F, 0.0F, -2.0F, 3.0F, 0.0F, 4.0F, new Dilation(0.0F)),
            ModelTransform.of(-4.0F, 5.0F, 7.0F, 0.0F, 0.0F, -0.7854F)
        );

        ModelPartData leftArm = body.addChild(
            "leftArm",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(0.0F, 0.0F, -3.0F, 5.0F, 0.0F, 6.0F, new Dilation(0.0F)),
            ModelTransform.of(4.0F, 5.0F, -3.0F, 0.0F, 0.0F, 1.1781F)
        );

        ModelPartData rightArm = body.addChild(
            "rightArm",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(true)
                            .cuboid(-5.0F, 0.0F, -3.0F, 5.0F, 0.0F, 6.0F, new Dilation(0.0F)),
            ModelTransform.of(-4.0F, 5.0F, -3.0F, 0.0F, 0.0F, -1.1781F)
        );

        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public void setAngles(BigEyedEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float speed = 1.0f;
        float degree = 1.25f;
        this.body.pitch = headPitch * 0.01F;
        this.body.yaw = headYaw * 0.01F;
        this.body.yaw += MathHelper.cos(-1.0F + animationProgress * speed * 0.2F) * degree * 0.4F * 0.25F;
        this.tail.yaw = MathHelper.cos(-2.0F + animationProgress * speed * 0.4F) * degree * 0.8F * 0.25F;
        this.tailFlukes.yaw = MathHelper.cos(-3.0F + animationProgress * speed * 0.4F) * degree * 1F * 0.25F;
        this.leftArm.roll = MathHelper.cos(animationProgress * speed * 0.2F) * degree * 1.8F * 0.25F + 1F;
        this.rightArm.roll = MathHelper.cos(animationProgress * speed * 0.2F) * degree * -1.8F * 0.25F - 1F;
        this.leftLeg.roll = MathHelper.cos(-2.0F + animationProgress * speed * 0.2F) * degree * 1.6F * 0.25F + 0.6F;
        this.rightLeg.roll = MathHelper.cos(-2.0F + animationProgress * speed * 0.2F) * degree * -1.6F * 0.25F - 0.6F;
        this.body.pivotY = MathHelper.cos(animationProgress * speed * 0.2F) * degree * 2.5F * 0.25F + 19.0F;
        this.body.pitch += MathHelper.cos(animationProgress * speed * 0.2F) * degree * 0.2F * 0.25F;
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
