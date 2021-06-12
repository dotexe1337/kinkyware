package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import net.fabricmc.example.ClientSupport;
import net.fabricmc.example.HackSupport;
import net.fabricmc.example.utils.PlayerUtils;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.MovementType;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.util.math.Vec3d;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity extends AbstractClientPlayerEntity implements ClientSupport {
	
	public MixinClientPlayerEntity(ClientWorld world, GameProfile profile) {
		super(world, profile);
	}

	@Inject(method = "move", at = @At("HEAD"), cancellable = true)
	public void move(MovementType type, Vec3d movement, CallbackInfo info) {
		if(HackSupport.flight) {
			if(mc.player.input.jumping) {
				mc.player.setVelocity(mc.player.getVelocity().getX(), 1f, mc.player.getVelocity().getZ());
			} else if(mc.player.input.sneaking) {
				mc.player.setVelocity(mc.player.getVelocity().getX(), -1f, mc.player.getVelocity().getZ());
			} else {
				mc.player.setVelocity(mc.player.getVelocity().getX(), 0f, mc.player.getVelocity().getZ());
			}
			if(PlayerUtils.isMoving())
				PlayerUtils.setSpeed(HackSupport.flySpeed);
		}
		if(HackSupport.speed) {
			if(PlayerUtils.isMoving())
				PlayerUtils.setSpeed(HackSupport.speedSpeed);
		}
		if(HackSupport.step) {
			mc.player.stepHeight = 1f;
		} else {
			mc.player.stepHeight = 0.5f;
		}
	}
	
}
