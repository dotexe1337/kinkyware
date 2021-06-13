package net.fabricmc.example.mixin;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.example.ClientSupport;
import net.fabricmc.example.HackSupport;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(InGameHud.class)
public class MixinIngameHud implements ClientSupport {
	
	@Inject(method = "render", at = @At("RETURN"), cancellable = true)
	public void render(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
		mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Kinkyware v0.0.1", 2, 2, 0xffffffff);
		mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "flySpeed: " + HackSupport.flySpeed, 2, 12, 0xffffffff);
		mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "speedSpeed: " + HackSupport.speedSpeed, 2, 22, 0xffffffff);
		mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Protect: " + (HackSupport.protect ? "\247a" : "\247c") + HackSupport.protect, 2, 32, 0xffffffff);
		mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Step: " + (HackSupport.step ? "\247a" : "\247c") + HackSupport.step, 2, 42, 0xffffffff);
		mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Flight: " + (HackSupport.flight ? "\247a" : "\247c") + HackSupport.flight, 2, 52, 0xffffffff);
		mc.inGameHud.getFontRenderer().drawWithShadow(matrixStack, "Speed: " + (HackSupport.speed ? "\247a" : "\247c") + HackSupport.speed, 2, 62, 0xffffffff);
	}
	
}
