package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.example.Client;
import net.fabricmc.example.ClientSupport;
import net.fabricmc.example.HackSupport;
import net.fabricmc.example.utils.BlockUtils;
import net.fabricmc.example.utils.PlayerUtils;
import net.minecraft.block.AirBlock;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

@Mixin(ClientWorld.class)
public class MixinClientWorld implements ClientSupport {
	
	@Inject(method = "tickEntities", at = @At("HEAD"), cancellable = true)
	public void tickEntities(CallbackInfo info) {
		if(HackSupport.nofall) {
			if(mc.player.fallDistance > 2.5f) {
				mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
			}
		}
		if(HackSupport.flight) {
			if(mc.player.input.jumping) {
				mc.player.setVelocity(mc.player.getVelocity().getX(), 1f, mc.player.getVelocity().getZ());
			} else if(mc.player.input.sneaking) {
				mc.player.setVelocity(mc.player.getVelocity().getX(), -1f, mc.player.getVelocity().getZ());
			} else {
				mc.player.setVelocity(mc.player.getVelocity().getX(), 0f, mc.player.getVelocity().getZ());
			}
			if(PlayerUtils.isMoving() && !mc.player.input.sneaking)
				PlayerUtils.setSpeed(HackSupport.flySpeed);
			else
				PlayerUtils.setSpeed(0.0f);
			if(Client.flyTimer.hasPassed(75)) {
				if(BlockUtils.getBlock(mc.player.getPos().getX(), mc.player.getPos().getY() - 0.069, mc.player.getPos().getZ()) instanceof AirBlock) {
					mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() - 0.069, mc.player.getZ(), false));
					Client.flyTimer.updateLastTime();
				}
			} else {
				if(BlockUtils.getBlock(mc.player.getPos().getX(), mc.player.getPos().getY() + 0.069, mc.player.getPos().getZ()) instanceof AirBlock) {
					mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() + 0.069, mc.player.getZ(), true));
				}
			}
		}
		mc.options.gamma = HackSupport.gamma;
	}
	
}
