// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class cat1model extends EntityModel<Entity> {
	private final ModelPart head;
	private final ModelPart bone;
	private final ModelPart Tophat;
	private final ModelPart body;
	private final ModelPart bone2;
	private final ModelPart front_left_leg;
	private final ModelPart front_right_leg;
	private final ModelPart back_left_leg;
	private final ModelPart back_right_leg;
	private final ModelPart tail;
	private final ModelPart tail2;
	public cat1model(ModelPart root) {
		this.head = root.getChild("head");
		this.bone = this.head.getChild("bone");
		this.Tophat = this.head.getChild("Tophat");
		this.body = root.getChild("body");
		this.bone2 = this.body.getChild("bone2");
		this.front_left_leg = root.getChild("front_left_leg");
		this.front_right_leg = root.getChild("front_right_leg");
		this.back_left_leg = root.getChild("back_left_leg");
		this.back_right_leg = root.getChild("back_right_leg");
		this.tail = root.getChild("tail");
		this.tail2 = root.getChild("tail2");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(20, 0).cuboid(-2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 5.0F, new Dilation(0.0F))
		.uv(0, 22).cuboid(-1.5F, -0.02F, -4.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 15.0F, -9.0F));

		ModelPartData bone = head.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 9.0F, 9.0F));

		ModelPartData Tophat = head.addChild("Tophat", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -3.0F, -4.0F, 6.0F, 1.0F, 7.0F, new Dilation(0.0F))
		.uv(0, 8).cuboid(-2.0F, -8.0F, -3.0F, 4.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, 3.0F, -8.0F, 4.0F, 16.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 12.0F, -10.0F, 1.5708F, 0.0F, 0.0F));

		ModelPartData bone2 = body.addChild("bone2", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -11.0F, -17.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 12.0F, 10.0F));

		ModelPartData front_left_leg = modelPartData.addChild("front_left_leg", ModelPartBuilder.create().uv(20, 9).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.1F, 14.1F, -5.0F));

		ModelPartData front_right_leg = modelPartData.addChild("front_right_leg", ModelPartBuilder.create().uv(20, 9).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.1F, 14.1F, -5.0F));

		ModelPartData back_left_leg = modelPartData.addChild("back_left_leg", ModelPartBuilder.create().uv(20, 21).cuboid(-1.0F, 0.0F, 1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.1F, 18.0F, 5.0F));

		ModelPartData back_right_leg = modelPartData.addChild("back_right_leg", ModelPartBuilder.create().uv(20, 21).cuboid(-1.0F, 0.0F, 1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.1F, 18.0F, 5.0F));

		ModelPartData tail = modelPartData.addChild("tail", ModelPartBuilder.create().uv(10, 22).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 15.5F, 8.0F, 1.5708F, 0.0F, 0.0F));

		ModelPartData tail2 = modelPartData.addChild("tail2", ModelPartBuilder.create().uv(14, 22).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 15.5F, 16.0F, 1.5708F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		front_left_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		front_right_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		back_left_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		back_right_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tail.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tail2.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}