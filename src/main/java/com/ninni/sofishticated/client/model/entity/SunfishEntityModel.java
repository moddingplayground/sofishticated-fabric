package com.ninni.sofishticated.client.model.entity;

import com.ninni.sofishticated.entity.SunfishEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@SuppressWarnings("FieldCanBeLocal, unused")
public class SunfishEntityModel extends SinglePartEntityModel<SunfishEntity> {
    private final ModelPart root;

    private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart dorsalFin;
	private final ModelPart analFin;
	private final ModelPart leftPectoralFin;
	private final ModelPart rightPectoralFin;

    public SunfishEntityModel(ModelPart root) {

		this.root = root;

		this.body       = root.getChild("body");

		this.tail       = body.getChild("tail");
		this.dorsalFin    = body.getChild("dorsalFin");
		this.analFin   		= body.getChild("analFin");
		this.leftPectoralFin    = body.getChild("leftPectoralFin");
		this.rightPectoralFin   = body.getChild("rightPectoralFin");
    }

    public static TexturedModelData getTexturedModelData() {

        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
		    "body",
		    ModelPartBuilder.create()
							.uv(0, 0)
							.mirrored(false)
							.cuboid(-6.0F, -14.0F, -18.0F, 12.0F, 30.0F, 37.0F, new Dilation(0.0F))
							.uv(16, 0)
							.mirrored(false)
							.cuboid(-4.0F, 0.0F, -20.0F, 8.0F, 6.0F, 2.0F, new Dilation(0.0F)),
		    ModelTransform.of(0.0F, 8.0F, -1.0F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData tail = body.addChild(
		    "tail",
		    ModelPartBuilder.create()
		        .uv(89, 2)
		        .mirrored(false)
		        .cuboid(0.0F, -15.0F, 0.0F, 0.0F, 30.0F, 5.0F, new Dilation(0.0F)),
		    ModelTransform.of(0.0F, 1.0F, 19.0F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData dorsalFin = body.addChild(
		    "dorsalFin",
		    ModelPartBuilder.create()
		        .uv(61, 0)
		        .mirrored(false)
		        .cuboid(-1.0F, -18.0F, -6.0F, 2.0F, 18.0F, 12.0F, new Dilation(0.0F)),
		    ModelTransform.of(0.0F, -13.0F, 10.0F, -0.1745F, 0.0F, 0.0F)
		);

		ModelPartData analFin = body.addChild(
		    "analFin",
		    ModelPartBuilder.create()
		        .uv(0, 0)
		        .mirrored(false)
		        .cuboid(-1.0F, 0.0F, -6.0F, 2.0F, 18.0F, 12.0F, new Dilation(0.0F)),
		    ModelTransform.of(0.0F, 15.0F, 10.0F, 0.1745F, 0.0F, 0.0F)
		);

		ModelPartData leftPectoralFin = body.addChild(
		    "leftPectoralFin",
		    ModelPartBuilder.create()
		        .uv(0, 0)
		        .mirrored(false)
		        .cuboid(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 6.0F, new Dilation(0.0F)),
		    ModelTransform.of(6.0F, 0.0F, -6.0F, 0.0F, 0.3927F, 0.0F)
		);

		ModelPartData rightPectoralFin = body.addChild(
		    "rightPectoralFin",
		    ModelPartBuilder.create()
		        .uv(0, 0)
		        .mirrored(false)
		        .cuboid(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 6.0F, new Dilation(0.0F)),
		    ModelTransform.of(-6.0F, 0.0F, -6.0F, 0.0F, -0.3927F, 0.0F)
		);

        return TexturedModelData.of(data, 112, 80);
    }

    @Override
    public void setAngles(SunfishEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		float speed = 1.5f;
		float degree = 1.5f;
		this.body.roll = MathHelper.cos(-1F + animationProgress * speed * 0.2F) * degree * 0.2F * limbDistance;
		this.analFin.roll = MathHelper.cos(3F + animationProgress * speed * 0.2F) * degree * 1.6F * limbDistance;
		this.dorsalFin.roll = MathHelper.cos(animationProgress * speed * 0.2F) * degree * 1.6F * limbDistance;
		this.tail.yaw = MathHelper.cos(animationProgress * speed * 0.4F) * degree * 0.8F * limbDistance;
		this.leftPectoralFin.yaw = MathHelper.cos(animationProgress * speed * 0.4F) * degree * 0.8F * limbDistance + 0.8F;
		this.rightPectoralFin.yaw = MathHelper.cos(-1F + animationProgress * speed * 0.4F) * degree * 0.8F * limbDistance - 0.8F;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    
}