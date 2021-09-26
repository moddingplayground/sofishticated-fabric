package com.ninni.sofishticated.client.model.entity;


import com.google.common.collect.ImmutableList;
import com.ninni.sofishticated.entity.HorseshoeCrabEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("FieldCanBeLocal, unused")
public class HorseshoeCrabEntityModel extends AnimalModel<HorseshoeCrabEntity> {
    private final ModelPart root;

    private final ModelPart head;
    private final ModelPart abdomen;
    private final ModelPart tail;

    public HorseshoeCrabEntityModel(ModelPart root) {
        this.root = root;

        this.head         = root.getChild("head");

        this.abdomen      = head.getChild("abdomen");

        this.tail         = abdomen.getChild("tail");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData head = root.addChild(
            "head",
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(-6.0F, -2.5F, -5.0F, 12.0F, 5.0F, 10.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 21.5F, -2.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData abdomen = head.addChild(
            "abdomen",
            ModelPartBuilder.create()
                            .uv(14, 15)
                            .mirrored(false)
                            .cuboid(-4.0F, -1.5F, 0.0F, 8.0F, 3.0F, 4.0F, new Dilation(0.0F))
                            .uv(0, 26)
                            .mirrored(false)
                            .cuboid(-5.0F, 0.5F, 0.0F, 10.0F, 0.0F, 6.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 1.0F, 5.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData tail = abdomen.addChild(
            "tail",
            ModelPartBuilder.create()
                            .uv(0, 15)
                            .mirrored(false)
                            .cuboid(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 10.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(data, 48, 32);
    }

    @Override
    public void setAngles(HorseshoeCrabEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float speed = 1.0f;
        float degree = 1.25f;
        if (entity.isSubmergedInWater()) {
            this.head.pitch = headPitch * 0.01F;
            this.head.yaw = headYaw * 0.01F;
            this.head.yaw += MathHelper.cos(-1.0F + animationProgress * speed * 0.4F) * degree * 0.3F * 0.25F;
            this.abdomen.pitch = MathHelper.cos(animationProgress * speed * 0.4F) * degree * 0.8F * 0.25F;
            this.abdomen.yaw = MathHelper.cos(-2.0F + animationProgress * speed * 0.4F) * degree * 0.4F * 0.25F;
            this.tail.yaw = MathHelper.cos(-3.0F + animationProgress * speed * 0.2F) * degree * 0.1F * 0.25F;
            this.tail.pitch = MathHelper.cos(-1.0F + animationProgress * speed * 0.4F) * degree * 1.8F * 0.25F;
        } else {
            this.head.pitch = headPitch * 0.0F;
            this.head.yaw = headYaw * 0.0F;
            this.head.yaw += MathHelper.cos(-1.0F + limbAngle * speed * 0.4F) * degree * 0.3F * limbDistance;
            this.abdomen.yaw = MathHelper.cos(-2.0F + limbAngle * speed * 0.4F) * degree * 0.4F * limbDistance;
            this.tail.yaw = MathHelper.cos(-3.0F + limbAngle * speed * 0.4F) * degree * 0.4F * limbDistance;
            this.tail.pitch = 0.0F;
            this.abdomen.pitch = 0.0F;
        }
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.head);
    }
}