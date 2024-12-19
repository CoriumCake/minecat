package g4.comsci.minecat.entity.client.LuciferCat;


import g4.comsci.minecat.entity.custom.LuciferCatEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class LuciferCatModel <T extends LuciferCatEntity> extends SinglePartEntityModel<T> {
	private final ModelPart LuciferCat;
	private final ModelPart head;

    public LuciferCatModel(ModelPart root) {
		this.LuciferCat = root.getChild("LuciferCat");
		this.head = this.LuciferCat.getChild("head");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData LuciferCat = modelPartData.addChild("LuciferCat", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData head = LuciferCat.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 5.0F, new Dilation(0.0F))
				.uv(0, 24).cuboid(-1.5F, -0.02F, -4.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 10).cuboid(-2.0F, -3.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(6, 10).cuboid(1.0F, -3.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -9.0F, -9.0F));

		ModelPartData body = LuciferCat.addChild("body", ModelPartBuilder.create().uv(20, 0).cuboid(-2.0F, 3.0F, -8.0F, 4.0F, 16.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -12.0F, -10.0F, 1.5708F, 0.0F, 0.0F));

		ModelPartData front_left_leg = LuciferCat.addChild("front_left_leg", ModelPartBuilder.create().uv(40, 0).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.1F, -9.9F, -5.0F));

		ModelPartData front_right_leg = LuciferCat.addChild("front_right_leg", ModelPartBuilder.create().uv(40, 0).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.1F, -9.9F, -5.0F));

		ModelPartData back_left_leg = LuciferCat.addChild("back_left_leg", ModelPartBuilder.create().uv(8, 13).cuboid(-1.0F, 0.0F, 1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.1F, -6.0F, 5.0F));

		ModelPartData back_right_leg = LuciferCat.addChild("back_right_leg", ModelPartBuilder.create().uv(8, 13).cuboid(-1.0F, 0.0F, 1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.1F, -6.0F, 5.0F));

		ModelPartData tail = LuciferCat.addChild("tail", ModelPartBuilder.create().uv(0, 15).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.5F, 8.0F, 1.5708F, 0.0F, 0.0F));

		ModelPartData tail2 = LuciferCat.addChild("tail2", ModelPartBuilder.create().uv(4, 15).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.5F, 16.0F, 1.5708F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 32);
	}
	@Override
	public void setAngles(LuciferCatEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform); // Reset transforms
		this.setHeadAngles(netHeadYaw, headPitch); // Update head angles
	}


	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		LuciferCat.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	private void setHeadAngles(float headYaw, float headPitch){
		headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
		headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}

	@Override
	public ModelPart getPart() {
		return LuciferCat;
	}
}