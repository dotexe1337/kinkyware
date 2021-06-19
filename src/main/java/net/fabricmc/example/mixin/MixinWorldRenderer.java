package net.fabricmc.example.mixin;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.example.Client;
import net.fabricmc.example.ClientSupport;
import net.fabricmc.example.utils.RenderUtils;
import net.fabricmc.example.utils.render.color.LineColor;
import net.fabricmc.example.utils.render.color.QuadColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer implements ClientSupport {
	
	/** Fixes that the outline framebuffer only resets if any glowing entities are drawn **/
	@ModifyConstant(method = "render", require = 1, constant = @Constant(intValue = 0),
			slice = @Slice(
					from = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/Framebuffer;beginWrite(Z)V", ordinal = 1),
					to = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/BufferBuilderStorage;getEntityVertexConsumers()Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;")))
	public int render_modifyBoolean(int old) {
		return 1;
	}
	
	@Inject(method = "render", at = @At("RETURN"))
	private void render_return(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo info) {
		RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, MinecraftClient.IS_SYSTEM_MAC);
		// put da code here
		
		for (Entity e : mc.world.getEntities()) {
			Vec3d vec = e.getPos().subtract(RenderUtils.getInterpolationOffset(e));

			Vec3d vec2 = new Vec3d(0, 0, 75)
					.rotateX(-(float) Math.toRadians(mc.gameRenderer.getCamera().getPitch()))
					.rotateY(-(float) Math.toRadians(mc.gameRenderer.getCamera().getYaw()))
					.add(mc.cameraEntity.getPos().add(0, mc.cameraEntity.getEyeHeight(mc.cameraEntity.getPose()), 0));

			float[] col = null;

			if (e instanceof PlayerEntity && e != mc.player && e != mc.cameraEntity) {
				col = getColorByDistance(e);
			}

			if (col != null) {
				// esp
				RenderUtils.drawLine(e.getBoundingBox().minX, e.getBoundingBox().minY, e.getBoundingBox().minZ, e.getBoundingBox().maxX, e.getBoundingBox().minY, e.getBoundingBox().minZ, LineColor.single(col[0], col[1], col[2], 255 / 255), 1.2f);
				RenderUtils.drawLine(e.getBoundingBox().maxX, e.getBoundingBox().minY, e.getBoundingBox().maxZ, e.getBoundingBox().minX, e.getBoundingBox().minY, e.getBoundingBox().maxZ, LineColor.single(col[0], col[1], col[2], 255 / 255), 1.2f);
				RenderUtils.drawLine(e.getBoundingBox().minX, e.getBoundingBox().minY, e.getBoundingBox().minZ, e.getBoundingBox().minX, e.getBoundingBox().maxY, e.getBoundingBox().minZ, LineColor.single(col[0], col[1], col[2], 255 / 255), 1.2f);
				RenderUtils.drawLine(e.getBoundingBox().maxX, e.getBoundingBox().maxY, e.getBoundingBox().minZ, e.getBoundingBox().maxX, e.getBoundingBox().maxY, e.getBoundingBox().maxZ, LineColor.single(col[0], col[1], col[2], 255 / 255), 1.2f);
				RenderUtils.drawLine(e.getBoundingBox().minX, e.getBoundingBox().maxY, e.getBoundingBox().maxZ, e.getBoundingBox().minX, e.getBoundingBox().maxY, e.getBoundingBox().minZ, LineColor.single(col[0], col[1], col[2], 255 / 255), 1.2f);
				RenderUtils.drawLine(e.getBoundingBox().minX, e.getBoundingBox().minY, e.getBoundingBox().minZ, e.getBoundingBox().minX, e.getBoundingBox().maxY, e.getBoundingBox().minZ, LineColor.single(col[0], col[1], col[2], 255 / 255), 1.2f);
				RenderUtils.drawLine(e.getBoundingBox().maxX, e.getBoundingBox().minY, e.getBoundingBox().minZ, e.getBoundingBox().maxX, e.getBoundingBox().maxY, e.getBoundingBox().minZ, LineColor.single(col[0], col[1], col[2], 255 / 255), 1.2f);
				RenderUtils.drawLine(e.getBoundingBox().maxX, e.getBoundingBox().minY, e.getBoundingBox().maxZ, e.getBoundingBox().maxX, e.getBoundingBox().maxY, e.getBoundingBox().maxZ, LineColor.single(col[0], col[1], col[2], 255 / 255), 1.2f);
				RenderUtils.drawLine(e.getBoundingBox().minX, e.getBoundingBox().minY, e.getBoundingBox().maxZ, e.getBoundingBox().minX, e.getBoundingBox().maxY, e.getBoundingBox().maxZ, LineColor.single(col[0], col[1], col[2], 255 / 255), 1.2f);
			    
				RenderUtils.drawLine(e.getBoundingBox().minX, e.getBoundingBox().minY, e.getBoundingBox().minZ, e.getBoundingBox().minX, e.getBoundingBox().maxY, e.getBoundingBox().maxZ, LineColor.single(col[0], col[1], col[2], 255 / 255), 1.2f);
				RenderUtils.drawLine(e.getBoundingBox().maxX, e.getBoundingBox().minY, e.getBoundingBox().minZ, e.getBoundingBox().minX, e.getBoundingBox().maxY, e.getBoundingBox().maxZ, LineColor.single(col[0], col[1], col[2], 255 / 255), 1.2f);
				RenderUtils.drawLine(e.getBoundingBox().maxX, e.getBoundingBox().minY, e.getBoundingBox().maxZ, e.getBoundingBox().minX, e.getBoundingBox().maxY, e.getBoundingBox().maxZ, LineColor.single(col[0], col[1], col[2], 255 / 255), 1.2f);
				RenderUtils.drawLine(e.getBoundingBox().maxX, e.getBoundingBox().minY, e.getBoundingBox().minZ, e.getBoundingBox().minX, e.getBoundingBox().maxY, e.getBoundingBox().minZ, LineColor.single(col[0], col[1], col[2], 255 / 255), 1.2f);
				RenderUtils.drawLine(e.getBoundingBox().maxX, e.getBoundingBox().minY, e.getBoundingBox().minZ, e.getBoundingBox().minX, e.getBoundingBox().minY, e.getBoundingBox().maxZ, LineColor.single(col[0], col[1], col[2], 255 / 255), 1.2f);
				RenderUtils.drawLine(e.getBoundingBox().maxX, e.getBoundingBox().maxY, e.getBoundingBox().minZ, e.getBoundingBox().minX, e.getBoundingBox().maxY, e.getBoundingBox().maxZ, LineColor.single(col[0], col[1], col[2], 255 / 255), 1.2f);
				
				// tracers
				RenderUtils.drawLine(vec2.x, vec2.y, vec2.z, vec.x, vec.y, vec.z, LineColor.single(col[0], col[1], col[2], 255 / 255), 1.2f); 
			}
		}
	}
	
	public float[] getColorByDistance(final Entity entity) {
        if (entity instanceof PlayerEntity && Client.friends.isFriend(entity.getName().getString())) {
            return new float[] { (85 / 255), (255 / 255), (255 / 255), 1.0f };
        }
        final Color col = new Color(Color.HSBtoRGB((float)(Math.max(0.0, Math.min(mc.player.squaredDistanceTo(entity), 2500.0f) / 2500.0f) / 3.0), 1.0f, 1.0f) | 0x000000);
        return new float[] { col.getRed() / 255.0f, col.getGreen() / 255.0f, col.getBlue() / 255.0f };
    }
	
}
