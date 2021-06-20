package net.fabricmc.example.mixin;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.example.ClientSupport;
import net.fabricmc.example.HackSupport;
import net.minecraft.client.Keyboard;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;

@Mixin(Keyboard.class)
public class MixinKeyboard implements ClientSupport {
	
	@Inject(method = "onKey", at = @At(value = "INVOKE", target = "net/minecraft/client/util/InputUtil.isKeyPressed(JI)Z", ordinal = 5), cancellable = true)
	private void onKeyEvent_1(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo callbackInfo) {
		// TODO: bh setting 
		switch(key) {
		case GLFW.GLFW_KEY_GRAVE_ACCENT:
			HackSupport.hud = !HackSupport.hud;
			break;
		case GLFW.GLFW_KEY_M:
			HackSupport.flight = !HackSupport.flight;
			break;
		case GLFW.GLFW_KEY_G:
			HackSupport.speed = !HackSupport.speed;
			break;
		case GLFW.GLFW_KEY_R:
			HackSupport.killAura = !HackSupport.killAura;
			break;
		case GLFW.GLFW_KEY_H:
			HackSupport.speedMine = !HackSupport.speedMine;
			break;
		case GLFW.GLFW_KEY_Z:
			HackSupport.sneak = !HackSupport.sneak;
			if(!HackSupport.sneak) {
				sendSneakPacket(Mode.RELEASE_SHIFT_KEY);
			}
			break;
		}
	}
	
	private void sendSneakPacket(Mode mode)
	{
		ClientPlayerEntity player = mc.player;
		ClientCommandC2SPacket packet =
			new ClientCommandC2SPacket(player, mode);
		player.networkHandler.sendPacket(packet);
	}
}