package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import net.fabricmc.example.Client;
import net.fabricmc.example.ClientSupport;
import net.fabricmc.example.HackSupport;
import net.fabricmc.example.utils.PlayerUtils;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity extends AbstractClientPlayerEntity implements ClientSupport {
	
	public MixinClientPlayerEntity(ClientWorld world, GameProfile profile) {
		super(world, profile);
	}
	
	private void sendSneakPacket(Mode mode)
	{
		ClientPlayerEntity player = mc.player;
		ClientCommandC2SPacket packet =
			new ClientCommandC2SPacket(player, mode);
		player.networkHandler.sendPacket(packet);
	}
	
	@Inject(at = {@At("HEAD")}, method = {"sendMovementPackets()V"})
	private void onSendMovementPacketsHEAD(CallbackInfo ci)
	{
		if(HackSupport.sneak) {
			sendSneakPacket(Mode.PRESS_SHIFT_KEY);
			sendSneakPacket(Mode.RELEASE_SHIFT_KEY);
		}
	}
	
	@Inject(at = {@At("TAIL")}, method = {"sendMovementPackets()V"})
	private void onSendMovementPacketsTAIL(CallbackInfo ci)
	{
		if(HackSupport.sneak) {
			sendSneakPacket(Mode.RELEASE_SHIFT_KEY);
			sendSneakPacket(Mode.PRESS_SHIFT_KEY);
		}
	}
	
	@Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
	public void pushOutOfBlocks(double x, double z, CallbackInfo ci) {
		if(HackSupport.freecam) {
			ci.cancel();
		}
	}

	@Inject(method = "move", at = @At("HEAD"), cancellable = true)
	public void move(MovementType type, Vec3d movement, CallbackInfo info) {
		mc.player.stepHeight = HackSupport.stepHeight;
		if(HackSupport.freecam) {
			mc.player.noClip = true;
		}
	}
	
}
