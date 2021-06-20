package net.fabricmc.example.mixin;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.example.ClientSupport;
import net.fabricmc.example.HackSupport;
import net.fabricmc.example.utils.PlayerCopyEntity;
import net.minecraft.client.Keyboard;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.util.math.Vec3d;

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
		case GLFW.GLFW_KEY_V:
			HackSupport.freecam = !HackSupport.freecam;
			if(HackSupport.freecam) {
				mc.chunkCullingEnabled = false;

				HackSupport.playerPos = new double[] { mc.player.getX(), mc.player.getY(), mc.player.getZ() };
				HackSupport.playerRot = new float[] { mc.player.getYaw(), mc.player.getPitch() };

				HackSupport.dummy = new PlayerCopyEntity(mc.player);

				HackSupport.dummy.spawn();

				if (mc.player.getVehicle() != null) {
					HackSupport.riding = mc.player.getVehicle();
					mc.player.getVehicle().removeAllPassengers();
				}

				if (mc.player.isSprinting()) {
					mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, Mode.STOP_SPRINTING));
				}

				HackSupport.prevFlying = mc.player.getAbilities().flying;
				HackSupport.prevFlySpeed = mc.player.getAbilities().getFlySpeed();
			} else {
				mc.chunkCullingEnabled = true;

				HackSupport.dummy.despawn();
				mc.player.noClip = false;
				mc.player.getAbilities().flying = HackSupport.prevFlying;
				mc.player.getAbilities().setFlySpeed(HackSupport.prevFlySpeed);

				mc.player.refreshPositionAndAngles(HackSupport.playerPos[0], HackSupport.playerPos[1], HackSupport.playerPos[2], HackSupport.playerRot[0], HackSupport.playerRot[1]);
				mc.player.setVelocity(Vec3d.ZERO);

				if (HackSupport.riding != null && mc.world.getEntityById(HackSupport.riding.getId()) != null) {
					mc.player.startRiding(HackSupport.riding);
				}
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