package com.ninni.sofishticated.client.model.entity;

import com.ninni.sofishticated.entity.MantaRayEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("FieldCanBeLocal, unused")
public class MantaRayEntityModel extends SinglePartEntityModel<MantaRayEntity> {
	private final ModelPart root;

	private final ModelPart torso;
	private final ModelPart leftWing;
	private final ModelPart leftWingTip;
	private final ModelPart rightWing;
	private final ModelPart rightWingTip;
	private final ModelPart tail;
	private final ModelPart leftAntenna;
	private final ModelPart rightAntenna;

	public MantaRayEntityModel(ModelPart root) {
		this.root = root;

		this.torso        = root.getChild("torso");

		this.tail         = torso.getChild("tail");
		this.leftAntenna  = torso.getChild("leftAntenna");
		this.rightAntenna = torso.getChild("rightAntenna");
		this.leftWing     = torso.getChild("leftWing");
		this.rightWing    = torso.getChild("rightWing");

		this.leftWingTip  = leftWing.getChild("leftWingTip");
		this.rightWingTip = rightWing.getChild("rightWingTip");
	}

	public static TexturedModelData getTexturedModelData(Dilation dilation) {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		ModelPartData torso = root.addChild(
			"torso",
			ModelPartBuilder.create()
							.uv(0, 0)
							.mirrored(false)
							.cuboid(-16.0F, -5.0F, -17.5F, 32.0F, 10.0F, 35.0F, dilation),
			ModelTransform.of(0.0F, 19.0F, 1.5F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData leftWing = torso.addChild(
			"leftWing",
			ModelPartBuilder.create()
							.uv(0, 45)
							.mirrored(true)
							.cuboid(0.0F, -1.5F, -13.0F, 28.0F, 3.0F, 26.0F, dilation),
			ModelTransform.of(16.0F, -1.5F, 0.5F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData leftWingTip = leftWing.addChild(
			"leftWingTip",
			ModelPartBuilder.create()
							.uv(82, 45)
							.mirrored(true)
							.cuboid(0.0F, -1.5F, -7.5F, 12.0F, 3.0F, 15.0F, dilation),
			ModelTransform.of(28.0F, 0.0F, -5.5F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData rightWing = torso.addChild(
			"rightWing",
			ModelPartBuilder.create()
							.uv(0, 45)
							.mirrored(false)
							.cuboid(-28.0F, -1.5F, -13.0F, 28.0F, 3.0F, 26.0F, dilation),
			ModelTransform.of(-16.0F, -1.5F, 0.5F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData rightWingTip = rightWing.addChild(
			"rightWingTip",
			ModelPartBuilder.create()
							.uv(82, 45)
							.mirrored(false)
							.cuboid(-12.0F, -1.5F, -7.5F, 12.0F, 3.0F, 15.0F, dilation),
			ModelTransform.of(-28.0F, 0.0F, -5.5F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData tail = torso.addChild(
			"tail",
			ModelPartBuilder.create()
							.uv(96, 24)
							.mirrored(false)
							.cuboid(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 40.0F, dilation),
			ModelTransform.of(0.0F, -2.0F, 17.5F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData leftAntenna = torso.addChild(
			"leftAntenna",
			ModelPartBuilder.create()
							.uv(0, 0)
							.mirrored(false)
							.cuboid(0.0F, -3.0F, -14.0F, 0.0F, 6.0F, 14.0F, dilation),
			ModelTransform.of(14.0F, 0.0F, -17.5F, 0.3927F, 1.0472F, 0.0F)
		);

		ModelPartData rightAntenna = torso.addChild(
			"rightAntenna",
			ModelPartBuilder.create()
							.uv(0, 0)
							.mirrored(false)
							.cuboid(0.0F, -3.0F, -14.0F, 0.0F, 6.0F, 14.0F, dilation),
			ModelTransform.of(-14.0F, 0.0F, -17.5F, 0.3927F, -1.0472F, 0.0F)
		);

		return TexturedModelData.of(data, 192, 80);
	}

	@Override
	public void setAngles(MantaRayEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		float speed = 1.5f;
		float degree = 0.75f;
		this.torso.pitch = headPitch * 0.01F;
		this.torso.yaw = headYaw * 0.01F;
		this.torso.pitch += MathHelper.cos(limbAngle * speed * 0.2F) * degree * 0.3F * limbDistance;
		this.tail.pitch = MathHelper.cos(animationProgress * speed * 0.2F) * degree * 0.8F * 0.25F;
		this.torso.roll = MathHelper.cos(-1.0F + limbAngle * speed * 0.1F) * degree * 0.4F * limbDistance;
		this.tail.yaw = MathHelper.cos(-1.0F + animationProgress * speed * 0.4F) * degree * 0.1F * 0.25F;
		this.leftWing.roll = MathHelper.cos(-1.0F + animationProgress * speed * 0.2F) * degree * 2.8F * 0.35F;
		this.leftWing.yaw = MathHelper.cos(-1.0F + animationProgress * speed * 0.2F) * degree * -0.6F * 0.35F;
		this.rightWing.roll = MathHelper.cos(-1.0F + animationProgress * speed * 0.2F) * degree * -2.8F * 0.35F;
		this.rightWing.yaw = MathHelper.cos(-1.0F + animationProgress * speed * 0.2F) * degree * 0.6F * 0.35F;
		this.leftWingTip.roll = MathHelper.cos(-2.0F + animationProgress * speed * 0.2F) * degree * 1.4F * 0.65F;
		this.rightWingTip.roll = MathHelper.cos(-2.0F + animationProgress * speed * 0.2F) * degree * -1.4F * 0.65F;
		this.torso.yaw += MathHelper.cos(-1.0F + limbAngle * speed * 0.2F) * degree * 0.2F * limbDistance;
		this.leftAntenna.pitch = MathHelper.cos(-1.0F + limbAngle * speed * 0.2F) * degree * 0.4F * limbDistance + 0.4F;
		this.rightAntenna.pitch = MathHelper.cos(-1.0F + limbAngle * speed * 0.2F) * degree * 0.4F * limbDistance + 0.4F;
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}
