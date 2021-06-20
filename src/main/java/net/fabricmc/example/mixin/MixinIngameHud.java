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
			
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Kinkyware \2477v0.0.3", 2, 2, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "flySpeed: \247e" + HackSupport.flySpeed, 2, 12, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "speedSpeed: \247e" + HackSupport.speedSpeed, 2, 22, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "gamma: \247e" + HackSupport.gamma, 2, 32, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Protect: " + (HackSupport.protect ? "\247a" : "\247c") + HackSupport.protect, 2, 42, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "ESP: " + (HackSupport.esp ? "\247a" : "\247c") + HackSupport.esp, 2, 52, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Tracers: " + (HackSupport.tracers ? "\247a" : "\247c") + HackSupport.tracers, 2, 62, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "NoFall: " + (HackSupport.nofall ? "\247a" : "\247c") + HackSupport.nofall, 2, 72, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Step: " + (HackSupport.step ? "\247a" : "\247c") + HackSupport.step, 2, 82, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Flight: " + (HackSupport.flight ? "\247a" : "\247c") + HackSupport.flight, 2, 92, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Speed: " + (HackSupport.speed ? "\247a" : "\247c") + HackSupport.speed, 2, 102, 0xffffffff);
			mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "KillAura: " + (HackSupport.killAura ? "\247a" : "\247c") + HackSupport.killAura, 2, 112, 0xffffffff);
			
			matrixStack.pop();
		}
	}
}
