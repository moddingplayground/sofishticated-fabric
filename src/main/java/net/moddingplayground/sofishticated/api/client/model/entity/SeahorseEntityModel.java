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
import net.moddingplayground.sofishticated.api.entity.SeahorseEntity;

import static net.minecraft.client.render.entity.model.EntityModelPartNames.*;

@Environment(EnvType.CLIENT)
public class SeahorseEntityModel<E extends SeahorseEntity> extends SinglePartEntityModel<E> {
    public static final String TAIL_BASE = "tail_base";
    public static final String SNOUT = "snout";
    public static final String CREST = "crest";

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart tailBase;
    private final ModelPart tail;
    private final ModelPart leftFin;
    private final ModelPart rightFin;
    private final ModelPart head;
    private final ModelPart snout;
    private final ModelPart crest;

    public SeahorseEntityModel(ModelPart root) {
        this.root     = root;
        this.body     = root.getChild(BODY);
        this.tailBase = body.getChild(TAIL_BASE);
        this.tail     = tailBase.getChild(TAIL);
        this.leftFin  = body.getChild(LEFT_FIN);
        this.rightFin = body.getChild(RIGHT_FIN);
        this.head     = body.getChild(HEAD);
        this.snout    = head.getChild(SNOUT);
        this.crest    = head.getChild(CREST);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
            BODY,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 17.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData tailBase = body.addChild(
            TAIL_BASE,
            ModelPartBuilder.create()
                            .uv(22, 1)
                            .mirrored(false)
                            .cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 3.0F, 0.5F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData tail = tailBase.addChild(
            TAIL,
            ModelPartBuilder.create()
                            .uv(14, 0)
                            .mirrored(false)
                            .cuboid(-1.0F, 0.0F, -3.0F, 2.0F, 3.0F, 4.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftFin = body.addChild(
            LEFT_FIN,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)),
            ModelTransform.of(-1.5F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData rightFin = body.addChild(
            RIGHT_FIN,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .mirrored(false)
                            .cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)),
            ModelTransform.of(1.5F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData head = body.addChild(
            HEAD,
            ModelPartBuilder.create()
                            .uv(0, 14)
                            .mirrored(false)
                            .cuboid(-2.0F, -2.0F, -2.5F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData snout = head.addChild(
            SNOUT,
            ModelPartBuilder.create()
                            .uv(16, 17)
                            .mirrored(false)
                            .cuboid(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)),
            ModelTransform.of(0.1F, 0.5F, -2.5F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData crest = head.addChild(
            CREST,
            ModelPartBuilder.create()
                            .uv(0, 4)
                            .mirrored(false)
                            .cuboid(0.0F, -4.5F, -2.5F, 0.0F, 5.0F, 5.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(data, 32, 32);
    }

    @Override
    public void setAngles(E entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float speed = 2.0f;
        float degree = 1.0f;
        if (entity.isSubmergedInWater()) {
            this.head.pitch = headPitch * (float) (Math.PI / 180);
            this.head.yaw = headYaw * (float) (Math.PI / 180);
        }
        this.body.pivotY = MathHelper.cos(animationProgress * speed * 0.1F) * degree * 0.5F * 0.25F + 17.0F;
        this.leftFin.yaw = MathHelper.cos(animationProgress * speed * 0.4F) * degree * -1.8F * 0.25F - 0.6F;
        this.rightFin.yaw = MathHelper.cos(animationProgress * speed * 0.4F) * degree * 1.8F * 0.25F + 0.6F;
        this.tailBase.pitch = MathHelper.cos(-1.0F + animationProgress * speed * 0.1F) * degree * 0.6F * 0.25F;
        this.tail.pitch = MathHelper.cos(-2.0F + animationProgress * speed * 0.1F) * degree * 0.6F * 0.25F;
        this.body.pitch = MathHelper.cos(animationProgress * speed * 0.1F) * degree * 0.4F * 0.25F;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}
