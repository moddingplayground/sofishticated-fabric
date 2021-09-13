package com.ninni.sofishticated.client.model.entity;

import com.ninni.sofishticated.entity.AnglerFishEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("FieldCanBeLocal, unused")
public class AnglerFishEntityModel extends SinglePartEntityModel<AnglerFishEntity> {
    private final ModelPart root;

    private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart lantern;
	private final ModelPart jaw;
	private final ModelPart leftFin;
	private final ModelPart rightFin;

    public AnglerFishEntityModel(ModelPart root) {
        this.root = root;

		this.body       = root.getChild("body");

		this.tail       = body.getChild("tail");
		this.lantern    = body.getChild("lantern");
		this.jaw   		= body.getChild("jaw");
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
						.cuboid(-4.0F, -4.0F, -4.5F, 7.0F, 7.0F, 9.0F, new Dilation(0.0F)),
		ModelTransform.of(0.5F, 19.0F, -1.0F, 0.0F, 0.0F, 0.0F)
	);

	ModelPartData tail = body.addChild(
		"tail",
		ModelPartBuilder.create()
						.uv(0, 0)
						.mirrored(false)
						.cuboid(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 4.0F, new Dilation(0.0F)),
		ModelTransform.of(-0.5F, 0.5F, 4.5F, 0.0F, 0.0F, 0.0F)
	);

	ModelPartData lantern = body.addChild(
		"lantern",
		ModelPartBuilder.create()
						.uv(0, 14)
						.mirrored(false)
						.cuboid(0.0F, -3.0F, -1.0F, 0.0F, 3.0F, 2.0F, new Dilation(0.0F))
						.uv(22, 16)
						.mirrored(false)
						.cuboid(-0.5F, -5.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)),
		ModelTransform.of(-0.5F, -3.5F, -2.5F, -0.3927F, 0.0F, 0.0F)
	);

	ModelPartData jaw = body.addChild(
		"jaw",
		ModelPartBuilder.create()
						.uv(0, 16)
						.mirrored(false)
						.cuboid(-3.5F, -3.0F, -7.5F, 7.0F, 5.0F, 7.0F, new Dilation(0.25F)),
		ModelTransform.of(-0.5F, 1.0F, 3.0F, 0.1745F, 0.0F, 0.0F)
	);

	ModelPartData leftFin = body.addChild(
		"leftFin",
		ModelPartBuilder.create()
						.uv(0, 0)
						.mirrored(false)
						.cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F)),
		ModelTransform.of(3.0F, 0.0F, 1.5F, 0.0F, 0.3927F, 0.0F)
	);

	ModelPartData rightFin = body.addChild(
		"rightFin",
		ModelPartBuilder.create()
						.uv(0, 0)
						.mirrored(false)
						.cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F)),
		ModelTransform.of(-4.0F, 0.0F, 1.5F, 0.0F, -0.3927F, 0.0F)
	);

        return TexturedModelData.of(data, 32, 32);
    }

    @Override
    public void setAngles(AnglerFishEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		float speed = 2.5f;
		float degree = 1.5f;
		this.body.yaw = MathHelper.cos(animationProgress * speed * 0.2F) * degree * 0.4F * 0.25F;
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
