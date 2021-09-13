package com.ninni.sofishticated.client.model.entity;


import com.ninni.sofishticated.entity.ReefSharkEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

@SuppressWarnings("FieldCanBeLocal, unused")
public class ReefSharkEntityModel extends SinglePartEntityModel<ReefSharkEntity> {
    private final ModelPart root;

    private final ModelPart body;
	private final ModelPart head;
	private final ModelPart tail;
	private final ModelPart tailFin;
	private final ModelPart leftFin;
	private final ModelPart rightFin;
	private final ModelPart leftBackFin;
	private final ModelPart rightBackFin;

    public ReefSharkEntityModel(ModelPart root) {

		this.root = root;

		this.body       = root.getChild("body");

		this.head       = body.getChild("head");
		this.tail       = body.getChild("tail");
		this.leftFin    = body.getChild("leftFin");
		this.leftBackFin    = body.getChild("leftBackFin");
		this.rightFin   = body.getChild("rightFin");
		this.rightBackFin   = body.getChild("rightBackFin");

		this.tailFin       = tail.getChild("tailFin");
	}

    public static TexturedModelData getTexturedModelData() {

        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
		    "body",
		    ModelPartBuilder.create()
							.uv(0, 5)
							.mirrored(false)
							.cuboid(0.0F, -8.0F, -1.0F, 0.0F, 3.0F, 5.0F, new Dilation(0.0F))
							.uv(0, 0)
							.mirrored(false)
							.cuboid(-2.5F, -5.0F, -5.0F, 5.0F, 5.0F, 13.0F, new Dilation(0.0F)),
		    ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData head = body.addChild(
		    "head",
		    ModelPartBuilder.create()
		        .uv(0, 18)
		        .mirrored(false)
		        .cuboid(-2.5F, -2.0F, -6.0F, 5.0F, 4.0F, 6.0F, new Dilation(0.0F)),
		    ModelTransform.of(0.0F, -2.0F, -5.0F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData tail = body.addChild(
		    "tail",
		    ModelPartBuilder.create()
		        .uv(16, 22)
		        .mirrored(false)
		        .cuboid(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F))
		.uv(0, 5)
		        .mirrored(false)
		        .cuboid(0.0F, -3.5F, 0.0F, 0.0F, 2.0F, 3.0F, new Dilation(0.0F)),
		    ModelTransform.of(0.0F, -3.5F, 8.0F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData tailFin = tail.addChild(
		    "tailFin",
		    ModelPartBuilder.create()
		        .uv(28, 12)
		        .mirrored(false)
		        .cuboid(0.0F, -6.5F, -1.0F, 0.0F, 10.0F, 6.0F, new Dilation(0.0F)),
		    ModelTransform.of(0.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData leftFin = body.addChild(
		    "leftFin",
		    ModelPartBuilder.create()
		        .uv(0, 0)
		        .mirrored(false)
		        .cuboid(0.0F, 0.0F, -2.5F, 4.0F, 0.0F, 5.0F, new Dilation(0.0F)),
		    ModelTransform.of(2.5F, -1.0F, -0.5F, 0.0F, 0.0F, 0.7854F)
		);

		ModelPartData rightFin = body.addChild(
		    "rightFin",
		    ModelPartBuilder.create()
		        .uv(0, 0)
		        .mirrored(true)
		        .cuboid(-4.0F, 0.0F, -2.5F, 4.0F, 0.0F, 5.0F, new Dilation(0.0F)),
		    ModelTransform.of(-2.5F, -1.0F, -0.5F, 0.0F, 0.0F, -0.7854F)
		);

		ModelPartData leftBackFin = body.addChild(
		    "leftBackFin",
		    ModelPartBuilder.create()
		        .uv(2, 5)
		        .mirrored(false)
		        .cuboid(0.0F, 0.0F, -1.5F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)),
		    ModelTransform.of(2.5F, 0.0F, 5.5F, 0.0F, 0.0F, 0.3927F)
		);

		ModelPartData rightBackFin = body.addChild(
		    "rightBackFin",
		    ModelPartBuilder.create()
		        .uv(2, 5)
		        .mirrored(true)
		        .cuboid(-2.0F, 0.0F, -1.5F, 2.0F, 0.0F, 3.0F, new Dilation(0.0F)),
		    ModelTransform.of(-2.5F, 0.0F, 5.5F, 0.0F, 0.0F, -0.3927F)
		);

        return TexturedModelData.of(data, 48, 32);
    }

    @Override
    public void setAngles(ReefSharkEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		float speed = 1.5f;
		float degree = 0.75f;
		this.body.pitch = headPitch * 0.01F;
		this.body.yaw = headYaw * 0.01F;
	}

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    
}