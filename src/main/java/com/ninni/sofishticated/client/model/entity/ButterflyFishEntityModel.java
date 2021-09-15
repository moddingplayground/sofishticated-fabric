package com.ninni.sofishticated.client.model.entity;

import com.ninni.sofishticated.entity.ButterflyFishEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("FieldCanBeLocal, unused")
public class ButterflyFishEntityModel extends SinglePartEntityModel<ButterflyFishEntity> {
	private final ModelPart root;

	private final ModelPart body;
	private final ModelPart tailFin;
	private final ModelPart leftBottomFin;
	private final ModelPart rightBottomFin;
	private final ModelPart leftFin;
	private final ModelPart rightFin;

    public ButterflyFishEntityModel(ModelPart root) {
		super(RenderLayer::getEntityTranslucent);
		this.root = root;

		this.body       = root.getChild("body");

		this.tailFin       = body.getChild("tailFin");
		this.leftBottomFin    = body.getChild("leftBottomFin");
		this.rightBottomFin   		= body.getChild("rightBottomFin");
		this.leftFin    = body.getChild("leftFin");
		this.rightFin   = body.getChild("rightFin");
    }

	public static TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		ModelPartData body = root.addChild(
			"body",
			ModelPartBuilder.create()
							.uv(10, 10)
							.mirrored(false)
							.cuboid(-0.5F, 0.5F, -5.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
							.uv(0, 0)
							.mirrored(false)
							.cuboid(-0.5F, 0.5F, -4.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
							.uv(0, 0)
							.mirrored(false)
							.cuboid(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F))
							.uv(0, 4)
							.mirrored(false)
							.cuboid(0.0F, -4.0F, -3.0F, 0.0F, 2.0F, 6.0F, new Dilation(0.0F)),
			ModelTransform.of(0.0F, 22.0F, 0.0F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData tailFin = body.addChild(
			"tailFin",
			ModelPartBuilder.create()
							.uv(10, 0)
							.mirrored(false)
							.cuboid(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(0.0F, 0.5F, 3.0F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData leftBottomFin = body.addChild(
			"leftBottomFin",
			ModelPartBuilder.create()
							.uv(0, 0)
							.mirrored(false)
							.cuboid(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(1.0F, 2.0F, 0.0F, 0.0F, 0.0F, -0.3927F)
		);

		ModelPartData rightBottomFin = body.addChild(
			"rightBottomFin",
			ModelPartBuilder.create()
							.uv(0, 0)
							.mirrored(true)
							.cuboid(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 2.0F, new Dilation(0.0F)),
			ModelTransform.of(-1.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.3927F)
		);

		ModelPartData leftFin = body.addChild(
			"leftFin",
			ModelPartBuilder.create()
							.uv(4, 0)
							.mirrored(false)
							.cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.of(1.0F, 1.0F, -1.0F, 0.0F, 0.7854F, 0.0F)
		);

		ModelPartData rightFin = body.addChild(
			"rightFin",
			ModelPartBuilder.create()
							.uv(4, 0)
							.mirrored(true)
							.cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)),
			ModelTransform.of(-1.0F, 1.0F, -1.0F, 0.0F, -0.7854F, 0.0F)
		);

		return TexturedModelData.of(data, 16, 16);
	}

    @Override
    public void setAngles(ButterflyFishEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		float speed = 1.5f;
		float degree = 1.5f;
		if (entity.isTouchingWater()) {
			this.body.pitch = headPitch * 0.015F;
			this.body.yaw = headYaw * 0.01F;
		}
		this.body.yaw += MathHelper.cos(animationProgress * speed * 0.2F) * degree * 0.4F * 0.25F;
		this.tailFin.yaw = MathHelper.cos(animationProgress * speed * 0.4F) * degree * 0.8F * 0.25F;
		this.leftFin.yaw = MathHelper.cos(animationProgress * speed * 0.4F) * degree * 0.8F * 0.25F + 0.8F;
		this.rightFin.yaw = MathHelper.cos(3.0F + animationProgress * speed * 0.4F) * degree * 0.8F * 0.25F - 0.8F;
		this.leftBottomFin.roll = MathHelper.cos(-2.0F + animationProgress * speed * 0.2F) * degree * 0.8F * 0.25F - 0.6F;
		this.rightBottomFin.roll = MathHelper.cos(-2.0F + animationProgress * speed * 0.2F) * degree * -0.8F * 0.25F + 0.6F;
	}

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}
