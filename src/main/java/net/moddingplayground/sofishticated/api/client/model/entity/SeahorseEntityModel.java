package net.moddingplayground.sofishticated.api.client.model.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.moddingplayground.sofishticated.api.entity.SeahorseEntity;

import static net.minecraft.client.render.entity.model.EntityModelPartNames.*;
import static net.minecraft.util.math.MathHelper.*;
import static net.moddingplayground.sofishticated.api.client.model.SofishticatedEntityModelPartNames.*;

@Environment(EnvType.CLIENT)
public class SeahorseEntityModel<E extends SeahorseEntity> extends SinglePartEntityModel<E> {
    public static final float
        ANIMATION_SPEED = 2.0f,
        ANIMATION_DEGREE = 1.0f;

    private final ModelPart
        root,
        body,
        tailBase,
        tail,
        leftFin,
        rightFin,
        head,
        snout,
        crest;

    public SeahorseEntityModel(ModelPart root) {
        this.root     = root;
        this.body     = this.root.getChild(BODY);
        this.tailBase = this.body.getChild(TAIL_BASE);
        this.tail     = this.tailBase.getChild(TAIL);
        this.leftFin  = this.body.getChild(LEFT_FIN);
        this.rightFin = this.body.getChild(RIGHT_FIN);
        this.head     = this.body.getChild(HEAD);
        this.snout    = this.head.getChild(SNOUT);
        this.crest    = this.head.getChild(CREST);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
            BODY,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 4.0F, 3.0F),
            ModelTransform.pivot(0.0F, 17.0F, 0.0F)
        );

        ModelPartData tailBase = body.addChild(
            TAIL_BASE,
            ModelPartBuilder.create()
                            .uv(22, 1)
                            .cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F),
            ModelTransform.pivot(0.0F, 3.0F, 0.5F)
        );

        tailBase.addChild(
            TAIL,
            ModelPartBuilder.create()
                            .uv(14, 0)
                            .cuboid(-1.0F, 0.0F, -3.0F, 2.0F, 3.0F, 4.0F),
            ModelTransform.pivot(0.0F, 1.0F, 0.0F)
        );

        body.addChild(
            LEFT_FIN,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 1.0F),
            ModelTransform.pivot(-1.5F, 1.0F, 0.0F)
        );

        body.addChild(
            RIGHT_FIN,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 1.0F),
            ModelTransform.pivot(1.5F, 1.0F, 0.0F)
        );

        ModelPartData head = body.addChild(
            HEAD,
            ModelPartBuilder.create()
                            .uv(0, 14)
                            .cuboid(-2.0F, -2.0F, -2.5F, 4.0F, 3.0F, 4.0F),
            ModelTransform.pivot(0.0F, -2.0F, 0.0F)
        );

        head.addChild(
            SNOUT,
            ModelPartBuilder.create()
                            .uv(16, 17)
                            .cuboid(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F),
            ModelTransform.pivot(0.1F, 0.5F, -2.5F)
        );

        head.addChild(
            CREST,
            ModelPartBuilder.create()
                            .uv(0, 4)
                            .cuboid(0.0F, -4.5F, -2.5F, 0.0F, 5.0F, 5.0F),
            ModelTransform.pivot(0.0F, 0.5F, 0.0F)
        );

        return TexturedModelData.of(data, 32, 32);
    }

    @Override
    public void setAngles(E entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        if (entity.isSubmergedInWater()) {
            this.head.pitch = headPitch * (float) (Math.PI / 180);
            this.head.yaw = headYaw * (float) (Math.PI / 180);
        }

        this.body.pivotY = cos(animationProgress * ANIMATION_SPEED * 0.1F) * ANIMATION_DEGREE * 0.5F * 0.25F + 17.0F;
        this.leftFin.yaw = cos(animationProgress * ANIMATION_SPEED * 0.4F) * ANIMATION_DEGREE * -1.8F * 0.25F - 0.6F;
        this.rightFin.yaw = cos(animationProgress * ANIMATION_SPEED * 0.4F) * ANIMATION_DEGREE * 1.8F * 0.25F + 0.6F;
        this.tailBase.pitch = cos(-1.0F + animationProgress * ANIMATION_SPEED * 0.1F) * ANIMATION_DEGREE * 0.6F * 0.25F;
        this.tail.pitch = cos(-2.0F + animationProgress * ANIMATION_SPEED * 0.1F) * ANIMATION_DEGREE * 0.6F * 0.25F;
        this.body.pitch = cos(animationProgress * ANIMATION_SPEED * 0.1F) * ANIMATION_DEGREE * 0.4F * 0.25F;

        this.snout.visible = true;
        this.crest.visible = true;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}
