package net.moddingplayground.sofishticated.api.client.model.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.moddingplayground.sofishticated.api.entity.AnglerFishEntity;

import static net.minecraft.client.render.entity.model.EntityModelPartNames.*;
import static net.minecraft.util.math.MathHelper.*;
import static net.moddingplayground.sofishticated.api.client.model.SofishticatedEntityModelPartNames.*;

@Environment(EnvType.CLIENT)
public class AnglerFishEntityModel<E extends AnglerFishEntity> extends SinglePartEntityModel<E> {
    public static final float
        ANIMATION_SPEED = 2.5f,
        ANIMATION_DEGREE = 1.5f;

    public final ModelPart
        root,
        body,
        tail,
        lantern,
        jaw,
        leftFin,
        rightFin;

    public AnglerFishEntityModel(ModelPart root) {
        this.root     = root;
        this.body     = this.root.getChild(BODY);
        this.tail     = this.body.getChild(TAIL);
        this.lantern  = this.body.getChild(LANTERN);
        this.jaw      = this.body.getChild(JAW);
        this.leftFin  = this.body.getChild(LEFT_FIN);
        this.rightFin = this.body.getChild(RIGHT_FIN);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
            BODY,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(-4.0F, -4.0F, -4.5F, 7.0F, 7.0F, 9.0F),
            ModelTransform.pivot(0.5F, 19.0F, -1.0F)
        );

        body.addChild(
            TAIL,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 4.0F),
            ModelTransform.pivot(-0.5F, 0.5F, 4.5F)
        );

        body.addChild(
            LANTERN,
            ModelPartBuilder.create()
                            .uv(0, 14)
                            .cuboid(0.0F, -3.0F, -1.0F, 0.0F, 3.0F, 2.0F)
                            .uv(22, 16)
                            .cuboid(-0.5F, -5.0F, -1.0F, 1.0F, 2.0F, 2.0F),
            ModelTransform.of(-0.5F, -3.5F, -2.5F, -0.3927F, 0.0F, 0.0F)
        );

        body.addChild(
            JAW,
            ModelPartBuilder.create()
                            .uv(0, 16)
                            .cuboid(-3.5F, -3.0F, -7.5F, 7.0F, 5.0F, 7.0F, new Dilation(0.25F)),
            ModelTransform.of(-0.5F, 1.0F, 3.0F, 0.1745F, 0.0F, 0.0F)
        );

        body.addChild(
            LEFT_FIN,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 2.0F),
            ModelTransform.of(3.0F, 0.0F, 1.5F, 0.0F, 0.3927F, 0.0F)
        );

        body.addChild(
            RIGHT_FIN,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 2.0F),
            ModelTransform.of(-4.0F, 0.0F, 1.5F, 0.0F, -0.3927F, 0.0F)
        );

        return TexturedModelData.of(data, 32, 32);
    }

    public static TexturedModelData getDeflatedTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
            BODY,
            ModelPartBuilder.create()
                            .uv(0, 2)
                            .cuboid(-4.0F, -2.0F, -4.5F, 7.0F, 5.0F, 9.0F),
            ModelTransform.pivot(0.5F, 21.0F, -1.0F)
        );

        body.addChild(
            TAIL,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 4.0F),
            ModelTransform.pivot(-0.5F, 0.5F, 4.5F)
        );

        body.addChild(
            LANTERN,
            ModelPartBuilder.create()
                            .uv(0, 14)
                            .cuboid(0.0F, -3.0F, -1.0F, 0.0F, 3.0F, 2.0F)
                            .uv(22, 16)
                            .cuboid(-0.5F, -5.0F, -1.0F, 1.0F, 2.0F, 2.0F),
            ModelTransform.of(-0.5F, -1.5F, -2.5F, -0.8727F, 0.0F, 0.0F)
        );

        body.addChild(
            JAW,
            ModelPartBuilder.create()
                            .uv(0, 16)
                            .cuboid(-3.5F, -3.0F, -7.5F, 7.0F, 5.0F, 7.0F, new Dilation(0.25F)),
            ModelTransform.pivot(-0.5F, 1.0F, 3.0F)
        );

        body.addChild(
            LEFT_FIN,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(0.0F, 1.0F, 0.0F, 0.0F, 2.0F, 2.0F),
            ModelTransform.of(3.0F, 0.0F, 1.5F, 0.0F, 0.3927F, 0.0F)
        );

        body.addChild(
            RIGHT_FIN,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(0.0F, 1.0F, 0.0F, 0.0F, 2.0F, 2.0F),
            ModelTransform.of(-4.0F, 0.0F, 1.5F, 0.0F, -0.3927F, 0.0F)
        );

        return TexturedModelData.of(data, 32, 32);
    }

    @Override
    public void setAngles(E entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.body.yaw = headYaw * 0.01F;
        this.tail.yaw = cos(animationProgress * ANIMATION_SPEED * 0.2F) * ANIMATION_DEGREE * 1.4F * 0.25F;
        this.leftFin.yaw = cos(-1.0F + animationProgress * ANIMATION_SPEED * 0.2F) * ANIMATION_DEGREE * 0.8F * 0.25F + 0.8F;
        this.rightFin.yaw = cos(2.0F + animationProgress * ANIMATION_SPEED * 0.2F) * ANIMATION_DEGREE * 0.8F * 0.25F - 0.8F;

        if (!entity.isDeflated()) {
            this.body.pitch = headPitch * 0.015F;
            this.body.yaw += cos(animationProgress * ANIMATION_SPEED * 0.2F) * ANIMATION_DEGREE * 0.4F * 0.25F;
            this.jaw.pitch = cos(limbAngle * ANIMATION_SPEED * 0.2F) * ANIMATION_DEGREE * 0.2F * limbDistance + 0.25F;
            this.tail.yaw = cos(animationProgress * ANIMATION_SPEED * 0.4F) * ANIMATION_DEGREE * 0.8F * 0.25F;
            this.leftFin.yaw = cos(animationProgress * ANIMATION_SPEED * 0.4F) * ANIMATION_DEGREE * 0.8F * 0.25F + 0.8F;
            this.rightFin.yaw = cos(3.0F + animationProgress * ANIMATION_SPEED * 0.4F) * ANIMATION_DEGREE * 0.8F * 0.25F - 0.8F;
        }

        this.lantern.visible = true;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}
