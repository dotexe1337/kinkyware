package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.example.ClientSupport;
import net.fabricmc.example.HackSupport;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

@Mixin(InGameHud.class)
public class MixinIngameHud implements ClientSupport {
	
	@Inject(method = "render", at = @At("RETURN"), cancellable = true)
	public void render(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
		if(HackSupport.hud) {
			matrixStack.push();
			matrixStack.scale(0.5f, 0.5f, 0.5f);
			
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Kinkyware \2477v0.0.4", 2, 2, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "flyHSpeed: \247e" + HackSupport.flyHSpeed, 2, 12, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "flyVSpeed: \247e" + HackSupport.flyVSpeed, 2, 22, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "speedSpeed: \247e" + HackSupport.speedSpeed, 2, 32, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "freecamHSpeed: \247e" + HackSupport.freecamHSpeed, 2, 42, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "freecamVSpeed: \247e" + HackSupport.freecamVSpeed, 2, 52, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "reach: \247e" + HackSupport.reach, 2, 62, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "gamma: \247e" + HackSupport.gamma, 2, 72, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Protect: " + (HackSupport.protect ? "\247a" : "\247c") + HackSupport.protect, 2, 82, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "ESP: " + (HackSupport.esp ? "\247a" : "\247c") + HackSupport.esp, 2, 92, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Tracers: " + (HackSupport.tracers ? "\247a" : "\247c") + HackSupport.tracers, 2, 102, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "NoFall: " + (HackSupport.nofall ? "\247a" : "\247c") + HackSupport.nofall, 2, 112, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Step: " + (HackSupport.step ? "\247a" : "\247c") + HackSupport.step, 2, 122, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Flight: " + (HackSupport.flight ? "\247a" : "\247c") + HackSupport.flight, 2, 132, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Speed: " + (HackSupport.speed ? "\247a" : "\247c") + HackSupport.speed, 2, 142, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Sneak: " + (HackSupport.sneak ? "\247a" : "\247c") + HackSupport.sneak, 2, 152, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Freecam: " + (HackSupport.freecam ? "\247a" : "\247c") + HackSupport.freecam, 2, 162, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "SpeedMine: " + (HackSupport.speedMine ? "\247a" : "\247c") + HackSupport.speedMine, 2, 172, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Torch: " + (HackSupport.torch ? "\247a" : "\247c") + HackSupport.torch, 2, 182, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "KillAura: " + (HackSupport.killAura ? "\247a" : "\247c") + HackSupport.killAura, 2, 192, 0xffffffff);
			
			matrixStack.pop();
		}
	}
}
