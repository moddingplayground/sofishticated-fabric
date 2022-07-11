package net.moddingplayground.sofishticated.api.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;
import net.moddingplayground.sofishticated.api.entity.ShrimpEntity;
import net.moddingplayground.sofishticated.api.entity.WhaleEntity;

import static net.minecraft.client.render.entity.model.EntityModelPartNames.*;

@Environment(EnvType.CLIENT)
public class WhaleEntityModel extends SinglePartEntityModel<WhaleEntity> {
    public static final String FLUKES = "flukes";

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart jaw;
    private final ModelPart tail;
    private final ModelPart flukes;
    private final ModelPart leftFin;
    private final ModelPart rightFin;

    public WhaleEntityModel(ModelPart root) {
        this.root     = root;
        this.body     = this.root.getChild(BODY);
        this.jaw      = this.root.getChild(JAW);
        this.tail     = this.root.getChild(TAIL);
        this.leftFin  = this.root.getChild(LEFT_FIN);
        this.rightFin = this.root.getChild(RIGHT_FIN);
        this.flukes   = this.tail.getChild(FLUKES);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
            BODY,
            ModelPartBuilder.create()
                            .uv(0, 0)
                            .cuboid(-7.0F, -9.0F, -10.0F, 14.0F, 15.0F, 14.0F, new Dilation(0.0F))
                            .uv(33, 36)
                            .cuboid(-5.0F, -9.0F, -23.0F, 10.0F, 4.0F, 13.0F, new Dilation(0.0F))
                            .uv(0, 0)
                            .cuboid(-0.5F, -11.0F, -2.0F, 1.0F, 2.0F, 6.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 18.0F, 4.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData jaw = body.addChild(
            JAW,
            ModelPartBuilder.create()
                            .uv(0, 29)
                            .cuboid(-6.0F, 0.0F, -11.0F, 12.0F, 9.0F, 11.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, -5.0F, -10.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData tail = body.addChild(
            TAIL,
            ModelPartBuilder.create()
                            .uv(0, 49)
                            .mirrored(false)
                            .cuboid(-5.0F, -6.0F, -1.0F, 10.0F, 10.0F, 10.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 4.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData flukes = tail.addChild(
            FLUKES,
            ModelPartBuilder.create()
                            .uv(42, 0)
                            .cuboid(-7.0F, -1.0F, -1.0F, 14.0F, 2.0F, 9.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, 0.0F, 7.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData leftFin = body.addChild(
            LEFT_FIN,
            ModelPartBuilder.create()
                            .uv(35, 29)
                            .cuboid(0.0F, -0.5F, -2.5F, 7.0F, 1.0F, 5.0F, new Dilation(0.0F)),
            ModelTransform.of(7.0F, 2.5F, -5.5F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData rightFin = body.addChild(
            RIGHT_FIN,
            ModelPartBuilder.create()
                            .uv(35, 29)
                            .mirrored(true)
                            .cuboid(-7.0F, -0.5F, -2.5F, 7.0F, 1.0F, 5.0F, new Dilation(0.0F)),
            ModelTransform.of(-7.0F, 2.5F, -5.5F, 0.0F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(data, 96, 80);
    }

    @Override
    public void setAngles(WhaleEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }


}