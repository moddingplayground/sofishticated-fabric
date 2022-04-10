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
import net.minecraft.util.math.MathHelper;
import net.moddingplayground.sofishticated.api.entity.AnglerFishEntity;

import static net.minecraft.client.render.entity.model.EntityModelPartNames.*;

@Environment(EnvType.CLIENT)
public class AnglerFishEntityModel<E extends AnglerFishEntity> extends SinglePartEntityModel<E> {
    public static final String LANTERN = "lantern";

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart lantern;
    private final ModelPart jaw;
    private final ModelPart leftFin;
    private final ModelPart rightFin;

    public AnglerFishEntityModel(ModelPart root) {
        this.root     = root;
        this.body     = root.getChild(BODY);
        this.tail     = body.getChild(TAIL);
        this.lantern  = body.getChild(LANTERN);
        this.jaw      = body.getChild(JAW);
        this.leftFin  = body.getChild(LEFT_FIN);
        this.rightFin = body.getChild(RIGHT_FIN);
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

        ModelPartData tail = body.addChild(
            TAIL,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 4.0F),
            ModelTransform.pivot(-0.5F, 0.5F, 4.5F)
        );

        ModelPartData lantern = body.addChild(
            LANTERN,
            ModelPartBuilder.create()
                            .uv(0, 14)
                            .cuboid(0.0F, -3.0F, -1.0F, 0.0F, 3.0F, 2.0F)
                            .uv(22, 16)
                            .cuboid(-0.5F, -5.0F, -1.0F, 1.0F, 2.0F, 2.0F),
            ModelTransform.of(-0.5F, -3.5F, -2.5F, -0.3927F, 0.0F, 0.0F)
        );

        ModelPartData jaw = body.addChild(
            JAW,
            ModelPartBuilder.create()
                            .uv(0, 16)
                            .cuboid(-3.5F, -3.0F, -7.5F, 7.0F, 5.0F, 7.0F, new Dilation(0.25F)),
            ModelTransform.of(-0.5F, 1.0F, 3.0F, 0.1745F, 0.0F, 0.0F)
        );

        ModelPartData leftFin = body.addChild(
            LEFT_FIN,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 2.0F),
            ModelTransform.of(3.0F, 0.0F, 1.5F, 0.0F, 0.3927F, 0.0F)
        );

        ModelPartData rightFin = body.addChild(
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

        ModelPartData tail = body.addChild(
            TAIL,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 4.0F),
            ModelTransform.pivot(-0.5F, 0.5F, 4.5F)
        );

        ModelPartData lantern = body.addChild(
            LANTERN,
            ModelPartBuilder.create()
                            .uv(0, 14)
                            .cuboid(0.0F, -3.0F, -1.0F, 0.0F, 3.0F, 2.0F)
                            .uv(22, 16)
                            .cuboid(-0.5F, -5.0F, -1.0F, 1.0F, 2.0F, 2.0F),
            ModelTransform.of(-0.5F, -1.5F, -2.5F, -0.8727F, 0.0F, 0.0F)
        );

        ModelPartData jaw = body.addChild(
            JAW,
            ModelPartBuilder.create()
                            .uv(0, 16)
                            .cuboid(-3.5F, -3.0F, -7.5F, 7.0F, 5.0F, 7.0F, new Dilation(0.25F)),
            ModelTransform.pivot(-0.5F, 1.0F, 3.0F)
        );

        ModelPartData leftFin = body.addChild(
            LEFT_FIN,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(0.0F, 1.0F, 0.0F, 0.0F, 2.0F, 2.0F),
            ModelTransform.of(3.0F, 0.0F, 1.5F, 0.0F, 0.3927F, 0.0F)
        );

        ModelPartData rightFin = body.addChild(
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

        if (entity.isDeflated()) return;
        this.body.pitch = headPitch * 0.015F;
        float speed = 2.5f;
        float degree = 1.5f;
        this.body.yaw += MathHelper.cos(animationProgress * speed * 0.2F) * degree * 0.4F * 0.25F;
        this.jaw.pitch = MathHelper.cos(limbAngle * speed * 0.2F) * degree * 0.2F * limbDistance + 0.25F;
        this.tail.yaw = MathHelper.cos(animationProgress * speed * 0.4F) * degree * 0.8F * 0.25F;
        this.leftFin.yaw = MathHelper.cos(animationProgress * speed * 0.4F) * degree * 0.8F * 0.25F + 0.8F;
        this.rightFin.yaw = MathHelper.cos(3.0F + animationProgress * speed * 0.4F) * degree * 0.8F * 0.25F - 0.8F;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}
