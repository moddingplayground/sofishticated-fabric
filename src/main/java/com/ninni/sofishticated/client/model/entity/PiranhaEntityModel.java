package com.ninni.sofishticated.client.model.entity;

import com.ninni.sofishticated.entity.PiranhaEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("FieldCanBeLocal, unused")
public class PiranhaEntityModel extends SinglePartEntityModel<PiranhaEntity> {
	private final ModelPart root;

	private final ModelPart body;
	private final ModelPart jaw;
	private final ModelPart tail;
	private final ModelPart dorsalfin;
	private final ModelPart rightFin;
	private final ModelPart leftFin;

	public PiranhaEntityModel(ModelPart root) {

		this.root = root;

		this.body       = root.getChild("body");

		this.tail       = body.getChild("tail");
		this.dorsalfin    = body.getChild("dorsalfin");
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
							.cuboid(-1.5F, -2.85F, -3.75F, 3.0F, 5.0F, 6.0F, new Dilation(0.0F)),
			ModelTransform.of(0.0F, 21.85F, 0.75F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData jaw = body.addChild(
			"jaw",
			ModelPartBuilder.create()
							.uv(12, 0)
							.mirrored(false)
							.cuboid(-2.0F, -1.75F, -4.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F))
							.uv(0, 11)
							.mirrored(false)
							.cuboid(-2.0F, -0.75F, -4.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F)),
			ModelTransform.of(0.0F, 1.15F, -0.25F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData tail = body.addChild(
			"tail",
			ModelPartBuilder.create()
							.uv(18, 4)
							.mirrored(false)
							.cuboid(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 3.0F, new Dilation(0.0F)),
			ModelTransform.of(0.0F, 0.15F, 2.25F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData dorsalfin = body.addChild(
			"dorsalfin",
			ModelPartBuilder.create()
							.uv(12, 2)
							.mirrored(false)
							.cuboid(0.0F, -1.0F, -1.5F, 0.0F, 1.0F, 3.0F, new Dilation(0.0F)),
			ModelTransform.of(0.0F, -2.85F, 0.75F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData rightFin = body.addChild(
			"rightFin",
			ModelPartBuilder.create()
							.uv(0, 0)
							.mirrored(true)
							.cuboid(-1.0F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.of(-1.5F, 1.15F, 0.25F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData leftFin = body.addChild(
			"leftFin",
			ModelPartBuilder.create()
							.uv(0, 0)
							.mirrored(false)
							.cuboid(0.0F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.of(1.5F, 1.15F, 0.25F, 0.0F, 0.0F, 0.0F)
		);

		return TexturedModelData.of(data, 32, 32);
	}

	@Override
	public void setAngles(PiranhaEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		if (!entity.isAttacking()) {
			float speed = 2.5f;
			float degree = 1.5f;
			this.body.yaw = MathHelper.cos(animationProgress * speed * 0.2F) * degree * 0.4F * 0.25F;
			this.tail.yaw = MathHelper.cos(animationProgress * speed * 0.4F) * degree * 0.8F * 0.25F;
			this.leftFin.roll = MathHelper.cos(animationProgress * speed * 0.4F) * degree * 0.8F * 0.25F;
			this.rightFin.roll = MathHelper.cos(3.0F + animationProgress * speed * 0.4F) * degree * 0.8F * 0.25F;
		} else {
			float speed = 3.0f;
			float degree = 1.5f;
			this.body.yaw = MathHelper.cos(animationProgress * speed * 0.2F) * degree * 0.4F * 0.25F;
			this.jaw.pitch = MathHelper.cos(animationProgress * speed * 0.4F) * degree * 1.6F * 0.25F + 0.8F;
			this.tail.yaw = MathHelper.cos(animationProgress * speed * 0.4F) * degree * 0.8F * 0.25F;
			this.leftFin.roll = MathHelper.cos(animationProgress * speed * 0.4F) * degree * 0.8F * 0.25F;
			this.rightFin.roll = MathHelper.cos(3.0F + animationProgress * speed * 0.4F) * degree * 0.8F * 0.25F;
		}
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}


}