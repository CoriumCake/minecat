package g4.comsci.minecat.entity.client.ThailandCat;


import g4.comsci.minecat.entity.custom.ThailandCatEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;


public class ThailandCatModel <T extends ThailandCatEntity> extends SinglePartEntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart ThailandCat;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart front_left_leg;
	private final ModelPart front_right_leg;
	private final ModelPart back_left_leg;
	private final ModelPart back_right_leg;
	private final ModelPart tail;
	private final ModelPart tail2;

	public ThailandCatModel(ModelPart root) {
		this.ThailandCat = root.getChild("ThailandCat");
		this.head = this.ThailandCat.getChild("head");
		this.body = this.ThailandCat.getChild("body");
		this.front_left_leg = this.ThailandCat.getChild("front_left_leg");
		this.front_right_leg = this.ThailandCat.getChild("front_right_leg");
		this.back_left_leg = this.ThailandCat.getChild("back_left_leg");
		this.back_right_leg = this.ThailandCat.getChild("back_right_leg");
		this.tail = this.ThailandCat.getChild("tail");
		this.tail2 = this.ThailandCat.getChild("tail2");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData ThailandCat = modelPartData.addChild("ThailandCat", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData head = ThailandCat.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 5.0F, new Dilation(0.0F))
				.uv(0, 24).cuboid(-1.5F, -0.02F, -4.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 10).cuboid(-2.0F, -3.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(6, 10).cuboid(1.0F, -3.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -9.0F, -9.0F));

		ModelPartData body = ThailandCat.addChild("body", ModelPartBuilder.create().uv(20, 0).cuboid(-2.0F, 3.0F, -8.0F, 4.0F, 16.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -12.0F, -10.0F, 1.5708F, 0.0F, 0.0F));

		ModelPartData front_left_leg = ThailandCat.addChild("front_left_leg", ModelPartBuilder.create().uv(40, 0).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.1F, -9.9F, -5.0F));

		ModelPartData front_right_leg = ThailandCat.addChild("front_right_leg", ModelPartBuilder.create().uv(40, 0).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.1F, -9.9F, -5.0F));

		ModelPartData back_left_leg = ThailandCat.addChild("back_left_leg", ModelPartBuilder.create().uv(8, 13).cuboid(-1.0F, 0.0F, 1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.1F, -6.0F, 5.0F));

		ModelPartData back_right_leg = ThailandCat.addChild("back_right_leg", ModelPartBuilder.create().uv(8, 13).cuboid(-1.0F, 0.0F, 1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.1F, -6.0F, 5.0F));

		ModelPartData tail = ThailandCat.addChild("tail", ModelPartBuilder.create().uv(0, 15).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.5F, 8.0F, 1.5708F, 0.0F, 0.0F));

		ModelPartData tail2 = ThailandCat.addChild("tail2", ModelPartBuilder.create().uv(4, 15).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.5F, 16.0F, 1.5708F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	public void setAngles(ThailandCatEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		ThailandCat.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return ThailandCat;
	}
}