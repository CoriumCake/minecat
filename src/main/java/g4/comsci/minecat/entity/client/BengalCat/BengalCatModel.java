package g4.comsci.minecat.entity.client.BengalCat;


import g4.comsci.minecat.entity.custom.BengalCatEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;


public class BengalCatModel<T extends BengalCatEntity> extends SinglePartEntityModel<T> {
		// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
		private final ModelPart BengalCat;
		private final ModelPart head;
		private final ModelPart body;
		private final ModelPart front_left_leg;
		private final ModelPart front_right_leg;
		private final ModelPart back_left_leg;
		private final ModelPart back_right_leg;
		private final ModelPart tail;
		private final ModelPart tail2;

		public BengalCatModel(ModelPart root) {
			this.BengalCat = root.getChild("BengalCat");
			this.head = this.BengalCat.getChild("head");
			this.body = this.BengalCat.getChild("body");
			this.front_left_leg = this.BengalCat.getChild("front_left_leg");
			this.front_right_leg = this.BengalCat.getChild("front_right_leg");
			this.back_left_leg = this.BengalCat.getChild("back_left_leg");
			this.back_right_leg = this.BengalCat.getChild("back_right_leg");
			this.tail = this.BengalCat.getChild("tail");
			this.tail2 = this.BengalCat.getChild("tail2");
		}

		public static TexturedModelData getTexturedModelData() {
			ModelData modelData = new ModelData();
			ModelPartData modelPartData = modelData.getRoot();
			ModelPartData BengalCat = modelPartData.addChild("BengalCat", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

			ModelPartData head = BengalCat.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 5.0F, new Dilation(0.0F))
					.uv(0, 24).cuboid(-1.5F, -0.02F, -4.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F))
					.uv(0, 10).cuboid(-2.0F, -3.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
					.uv(6, 10).cuboid(1.0F, -3.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -9.0F, -9.0F));

			ModelPartData body = BengalCat.addChild("body", ModelPartBuilder.create().uv(20, 0).cuboid(-2.0F, 3.0F, -8.0F, 4.0F, 16.0F, 6.0F, new Dilation(0.0F)),  ModelTransform.of(0.0F, -12.0F, -10.0F, 1.5708F, 0.0F, 0.0F));

			ModelPartData front_left_leg = BengalCat.addChild("front_left_leg", ModelPartBuilder.create().uv(40, 0).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.1F, -9.9F, -5.0F));

			ModelPartData front_right_leg = BengalCat.addChild("front_right_leg", ModelPartBuilder.create().uv(40, 0).cuboid(-1.0F, 0.0F, 0.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.1F, -9.9F, -5.0F));

			ModelPartData back_left_leg = BengalCat.addChild("back_left_leg", ModelPartBuilder.create().uv(8, 13).cuboid(-1.0F, 0.0F, 1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.1F, -6.0F, 5.0F));

			ModelPartData back_right_leg = BengalCat.addChild("back_right_leg", ModelPartBuilder.create().uv(8, 13).cuboid(-1.0F, 0.0F, 1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.1F, -6.0F, 5.0F));

			ModelPartData tail = BengalCat.addChild("tail", ModelPartBuilder.create().uv(0, 15).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.0F)),  ModelTransform.of(0.0F, -8.5F, 8.0F, 1.5708F, 0.0F, 0.0F));

			ModelPartData tail2 = BengalCat.addChild("tail2", ModelPartBuilder.create().uv(4, 15).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, new Dilation(0.0F)),  ModelTransform.of(0.0F, -8.5F, 16.0F, 1.5708F, 0.0F, 0.0F));
			return TexturedModelData.of(modelData, 64, 32);
		}

		@Override
		public void setAngles(BengalCatEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

		}

		@Override
		public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
			BengalCat.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		}

		@Override
		public ModelPart getPart() {
			return BengalCat;
		}
}
