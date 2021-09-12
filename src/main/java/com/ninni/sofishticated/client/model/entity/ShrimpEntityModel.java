package com.ninni.sofishticated.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.ninni.sofishticated.entity.ShrimpEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("FieldCanBeLocal, unused")
public class ShrimpEntityModel extends AnimalModel<ShrimpEntity> {
	private final ModelPart root;

	private final ModelPart body;
	private final ModelPart face;
	private final ModelPart tail;
	private final ModelPart tailfin;
	private final ModelPart antemnas;
	private final ModelPart legs_right;
	private final ModelPart legs_left;
	private final ModelPart hands;

    public ShrimpEntityModel(ModelPart root) {
		super(RenderLayer::getEntityTranslucent, true, 16.0F, 0.0F, 2.0F, 2.0F, 24.0F);

		this.root = root;

		this.body       = root.getChild("body");

		this.tail       = body.getChild("tail");
		this.hands    = body.getChild("hands");
		this.legs_left   		= body.getChild("legs_left");
		this.legs_right    = body.getChild("legs_right");
		this.face   = body.getChild("face");

		this.antemnas   = face.getChild("antemnas");

		this.tailfin   = tail.getChild("tailfin");
    }

	public static TexturedModelData getTexturedModelData() {

		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		ModelPartData body = root.addChild(
			"body",
			ModelPartBuilder.create()
							.uv(0, 3)
							.mirrored(false)
							.cuboid(-1.5F, 0.0F, -2.5F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F)),
			ModelTransform.of(0.0F, 19.5F, -2.0F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData face = body.addChild(
			"face",
			ModelPartBuilder.create()
							.uv(14, 6)
							.mirrored(false)
							.cuboid(-1.5F, -1.0F, -2.0F, 3.0F, 1.0F, 2.0F, new Dilation(-0.01F)),
			ModelTransform.of(0.0F, 1.5F, -2.5F, -0.1745F, 0.0F, 0.0F)
		);

		ModelPartData antemnas = face.addChild(
			"antemnas",
			ModelPartBuilder.create()
							.uv(0, 12)
							.mirrored(false)
							.cuboid(-1.0F, -7.0F, -2.0F, 2.0F, 7.0F, 3.0F, new Dilation(0.0F)),
			ModelTransform.of(0.0F, -0.9F, -2.4F, -1.1345F, 0.0F, 0.0F)
		);

		ModelPartData tail = body.addChild(
			"tail",
			ModelPartBuilder.create()
							.uv(13, 9)
							.mirrored(false)
							.cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 5.0F, new Dilation(0.01F)),
			ModelTransform.of(0.0F, 0.0F, 2.0F, 0.2618F, 0.0F, 0.0F)
		);

		ModelPartData tailfin = tail.addChild(
			"tailfin",
			ModelPartBuilder.create()
							.uv(16, 2)
							.mirrored(false)
							.cuboid(-2.5F, 0.0F, 0.0F, 5.0F, 1.0F, 3.0F, new Dilation(0.0F)),
			ModelTransform.of(0.0F, 0.0F, 4.8F, -0.5236F, 0.0F, 0.0F)
		);

		ModelPartData legs_right = body.addChild(
			"legs_right",
			ModelPartBuilder.create()
							.uv(16, 10)
							.mirrored(true)
							.cuboid(0.0F, 0.0F, -2.5F, 0.0F, 2.0F, 6.0F, new Dilation(0.0F)),
			ModelTransform.of(-1.0F, 3.0F, -0.5F, 0.0F, 0.0F, 0.7854F)
		);

		ModelPartData legs_left = body.addChild(
			"legs_left",
			ModelPartBuilder.create()
							.uv(16, 10)
							.mirrored(false)
							.cuboid(0.0F, 0.0F, -2.5F, 0.0F, 2.0F, 6.0F, new Dilation(0.0F)),
			ModelTransform.of(1.0F, 3.0F, -0.5F, 0.0F, 0.0F, -0.7854F)
		);

		ModelPartData hands = body.addChild(
			"hands",
			ModelPartBuilder.create()
							.uv(22, 6)
							.mirrored(false)
							.cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.of(0.0F, 2.5F, -2.5F, 0.0F, 0.0F, 0.0F)
		);

		return TexturedModelData.of(data, 32, 32);
	}

	@Override
	protected Iterable<ModelPart> getHeadParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> getBodyParts() {
		return ImmutableList.of(this.body);
	}

	@Override
	public void setAngles(ShrimpEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		if (entity.getVelocity().length() <= 0.1D && entity.isSubmergedInWater()) {
			float speed = 8.0f;
			float degree = 3.5f;
			this.tail.pitch = MathHelper.cos(limbAngle * speed * 0.2F) * degree * 0.4F * limbDistance + 0.25F;
			this.tailfin.pitch = MathHelper.cos(limbAngle * speed * 0.2F) * degree * 0.4F * limbDistance - 0.25F;
			this.antemnas.pitch = MathHelper.cos(limbAngle * speed * 0.2F) * degree * 0.6F * limbDistance - 1.0F;
			this.body.pitch = MathHelper.cos(limbAngle * speed * 0.2F) * degree * 0.1F * limbDistance;
			this.body.yaw = MathHelper.cos(1.5F + limbAngle * speed * 0.2F) * degree * 0.2F * limbDistance;
			this.hands.pitch = MathHelper.cos(limbAngle * speed * 0.2F) * degree * 0.6F * limbDistance;
			this.legs_right.roll = MathHelper.cos(3.0F + limbAngle * speed * 0.6F) * degree * 0.4F * limbDistance + 0.8F;
			this.legs_left.roll = MathHelper.cos(3.0F + limbAngle * speed * 0.6F) * degree * 0.4F * limbDistance - 0.8F;
			this.legs_right.yaw = MathHelper.cos(3.0F + limbAngle * speed * 0.6F) * degree * 0.1F * limbDistance;
			this.legs_left.yaw = MathHelper.cos(3.0F + limbAngle * speed * 0.6F) * degree * 0.1F * limbDistance;
		} else {
			float speed = 4.5f;
			float degree = 1.5f;
			this.tail.pitch = MathHelper.cos(limbAngle * speed * 0.2F) * degree * 0.4F * limbDistance + 0.25F;
			this.tailfin.pitch = MathHelper.cos(limbAngle * speed * 0.2F) * degree * 0.4F * limbDistance - 0.25F;
			this.antemnas.pitch = MathHelper.cos(limbAngle * speed * 0.2F) * degree * 0.6F * limbDistance - 1.0F;
			this.body.pitch = MathHelper.cos(limbAngle * speed * 0.2F) * degree * 0.1F * limbDistance;
			this.body.yaw = MathHelper.cos(1.5F + limbAngle * speed * 0.2F) * degree * 0.2F * limbDistance;
			this.hands.pitch = MathHelper.cos(limbAngle * speed * 0.2F) * degree * 0.6F * limbDistance;
			this.legs_right.roll = MathHelper.cos(3.0F + limbAngle * speed * 0.6F) * degree * 0.4F * limbDistance + 0.8F;
			this.legs_left.roll = MathHelper.cos(3.0F + limbAngle * speed * 0.6F) * degree * 0.4F * limbDistance - 0.8F;
			this.legs_right.yaw = MathHelper.cos(3.0F + limbAngle * speed * 0.6F) * degree * 0.1F * limbDistance;
			this.legs_left.yaw = MathHelper.cos(3.0F + limbAngle * speed * 0.6F) * degree * 0.1F * limbDistance;
		}
	}
}