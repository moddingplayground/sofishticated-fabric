package com.ninni.sofishticated.client.model.entity;


import com.ninni.sofishticated.entity.GarEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("FieldCanBeLocal, unused")
public class GarEntityModel extends SinglePartEntityModel<GarEntity> {
    private final ModelPart root;

    private final ModelPart body;
    private final ModelPart jaw;
    private final ModelPart tail;
    private final ModelPart leftFin;
    private final ModelPart rightFin;

    public GarEntityModel(ModelPart root) {
        this.root = root;

        this.body       = root.getChild("body");

        this.jaw        = body.getChild("jaw");
        this.tail       = body.getChild("tail");
        this.leftFin    = body.getChild("leftFin");
        this.rightFin   = body.getChild("rightFin");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
            "body",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(-2.5F, -2.0F, -7.0F, 5.0F, 4.0F, 14.0F, new Dilation(0.0F))
                            .uv(0, 18)
                            .mirrored(false)
                            .cuboid(-1.5F, -1.0F, -13.0F, 3.0F, 2.0F, 6.0F, new Dilation(0.0F))
                            .uv(0, 15)
                            .mirrored(false)
                            .cuboid(0.0F, -4.0F, 5.0F, 0.0F, 3.0F, 3.0F, new Dilation(0.0F))
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(0.0F, 1.0F, 6.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 22.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData jaw = body.addChild(
            "jaw",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(-1.0F, -1.0F, -5.0F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 1.0F, -7.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData tail = body.addChild(
            "tail",
            ModelPartBuilder.create()
                            .uv(0, 2)
                            .mirrored(false)
                            .cuboid(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 5.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 7.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftFin = body.addChild(
            "leftFin",
            ModelPartBuilder.create()
                            .uv(0, 11)
                            .mirrored(true)
                            .cuboid(0.0F, 0.0F, -1.5F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F)),
            ModelTransform.of(2.5F, 1.0F, -2.5F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData rightFin = body.addChild(
            "rightFin",
            ModelPartBuilder.create()
                            .uv(0, 11)
                            .mirrored(false)
                            .cuboid(-3.0F, 0.0F, -1.5F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F)),
            ModelTransform.of(-2.5F, 1.0F, -2.5F, 0.0F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(data, 48, 32);
    }

    @Override
    public void setAngles(GarEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float speed = 1.5f;
        float degree = 1.5f;
        this.jaw.pitch = 0.0F;
        this.tail.yaw = MathHelper.cos(animationProgress * speed * 0.4F) * degree * 1.4F * 0.25F;
        this.leftFin.roll = MathHelper.cos(animationProgress * speed * 0.4F) * degree * 0.8F * 0.25F + 0.8F;
        this.rightFin.roll = MathHelper.cos(3.0F + animationProgress * speed * 0.4F) * degree * 0.8F * 0.25F - 0.8F;
        if (entity.isTouchingWater()) {
            this.body.pitch = headPitch * 0.01F;
            this.body.yaw = headYaw * 0.01F;
            this.body.yaw += MathHelper.cos(animationProgress * speed * 0.2F) * degree * 0.4F * 0.25F;
        }
        if (entity.isAttacking()) {
            this.jaw.pitch = MathHelper.cos(animationProgress * speed * 0.4F) * degree * 0.8F * 0.25F + 0.4F;
        }

    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}
