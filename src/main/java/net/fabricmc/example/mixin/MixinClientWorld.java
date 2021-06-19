package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.example.ClientSupport;
import net.fabricmc.example.HackSupport;
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
		mc.options.gamma = HackSupport.gamma;
	}
	
}
