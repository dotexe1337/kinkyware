package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.example.ClientSupport;
import net.fabricmc.example.HackSupport;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient implements ClientSupport {

	@Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
	public void setScreen(Screen screen, CallbackInfo info) {
		if(HackSupport.freecam) {
			if (screen instanceof InventoryScreen) {
				mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.OPEN_INVENTORY));
				info.cancel();
			}
		}
	}
}