package net.fabricmc.example.utils;

import java.lang.reflect.Field;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.example.ClientSupport;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3f;

public class WorldRenderUtils implements ClientSupport {

	private static Field shaderLightField;

	/** Draws text in the world. **/
	public static void drawText(Text text, double x, double y, double z, double scale, boolean shadow) {
		drawText(text, x, y, z, 0, 0, scale, shadow);
	}

	/** Draws text in the world. **/
	public static void drawText(Text text, double x, double y, double z, double offX, double offY, double scale, boolean fill) {
		MatrixStack matrix = matrixFrom(x, y, z);

		Camera camera = mc.gameRenderer.getCamera();
		matrix.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-camera.getYaw()));
		matrix.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(camera.getPitch()));

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		matrix.translate(offX, offY, 0);
		matrix.scale(-0.025f * (float) scale, -0.025f * (float) scale, 1);

		int halfWidth = mc.textRenderer.getWidth(text) / 2;

		VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());

		if (fill) {
			int opacity = (int) (MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F) * 255.0F) << 24;
			mc.textRenderer.draw(text, -halfWidth, 0f, 553648127, true, matrix.peek().getModel(), immediate, true, opacity, 0xf000f0);
			immediate.draw();
		} else {
			matrix.push();
			matrix.translate(1, 1, 0);
			mc.textRenderer.draw(text.copy(), -halfWidth, 0f, 0x202020, true, matrix.peek().getModel(), immediate, true, 0, 0xf000f0);
			immediate.draw();
			matrix.pop();
		}

		mc.textRenderer.draw(text, -halfWidth, 0f, -1, false, matrix.peek().getModel(), immediate, true, 0, 0xf000f0);
		immediate.draw();

		RenderSystem.disableBlend();
	}

	/** Draws a 2D gui items somewhere in the world. **/
	public static void drawGuiItem(double x, double y, double z, double offX, double offY, double scale, ItemStack item) {
		if (item.isEmpty()) {
			return;
		}

		MatrixStack matrix = matrixFrom(x, y, z);

		Camera camera = mc.gameRenderer.getCamera();
		matrix.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-camera.getYaw()));
		matrix.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(camera.getPitch()));

		matrix.translate(offX, offY, 0);
		matrix.scale((float) scale, (float) scale, 0.001f);

		matrix.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180f));

		//mc.getBufferBuilders().getEntityVertexConsumers().draw();

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		Vec3f[] currentLight = getCurrentLight();
		DiffuseLighting.disableGuiDepthLighting();

		mc.getBufferBuilders().getEntityVertexConsumers().draw();

		mc.getItemRenderer().renderItem(item, ModelTransformation.Mode.GUI, 0xF000F0,
				OverlayTexture.DEFAULT_UV, matrix, mc.getBufferBuilders().getEntityVertexConsumers(), 0);

		mc.getBufferBuilders().getEntityVertexConsumers().draw();

		RenderSystem.setShaderLights(currentLight[0], currentLight[1]);
		RenderSystem.disableBlend();
	}

	public static MatrixStack matrixFrom(double x, double y, double z) {
		MatrixStack matrix = new MatrixStack();

		Camera camera = mc.gameRenderer.getCamera();
		matrix.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(camera.getPitch()));
		matrix.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(camera.getYaw() + 180.0F));

		matrix.translate(x - camera.getPos().x, y - camera.getPos().y, z - camera.getPos().z);

		return matrix;
	}

	public static Vec3f[] getCurrentLight() {
		if (shaderLightField == null) {
			shaderLightField = FieldUtils.getField(RenderSystem.class, "shaderLightDirections", true);
		}

		try {
			return (Vec3f[]) shaderLightField.get(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}