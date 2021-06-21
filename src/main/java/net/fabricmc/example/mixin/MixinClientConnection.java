package net.fabricmc.example.mixin;

import java.util.concurrent.Future;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.netty.util.concurrent.GenericFutureListener;
import net.fabricmc.example.Client;
import net.fabricmc.example.ClientSupport;
import net.fabricmc.example.HackSupport;
import net.fabricmc.example.utils.Timer;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.text.LiteralText;

@Mixin(ClientConnection.class)
public class MixinClientConnection implements ClientSupport {
	
	@Inject(method = "send(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V", at = @At("HEAD"), cancellable = true)
	public void send(Packet<?> pack, GenericFutureListener<? extends Future<? super Void>> packetCallback, CallbackInfo callback) {
		if(HackSupport.freecam) {
			if (pack instanceof ClientCommandC2SPacket || pack instanceof PlayerMoveC2SPacket) {
				callback.cancel();
			}
		}
		if (pack instanceof ChatMessageC2SPacket) {
			ChatMessageC2SPacket packet = (ChatMessageC2SPacket) pack;
			if((packet).getChatMessage().startsWith("$")) {
				callback.cancel();
				String[] split = (packet).getChatMessage().substring(1).split(" ");
				if(split[0].equalsIgnoreCase("up")) {
					try {
						mc.player.setPos(mc.player.getPos().getX(), mc.player.getPos().getY() + Float.parseFloat(split[1]), mc.player.getPos().getZ());
						mc.player.setPosition(mc.player.getPos().getX(), mc.player.getPos().getY() + Float.parseFloat(split[1]), mc.player.getPos().getZ());
					} catch (Exception e) { }
				}
				else if(split[0].equalsIgnoreCase("step")) {
					HackSupport.step = !HackSupport.step;
					mc.inGameHud.getChatHud().addMessage(new LiteralText("Step: " + (HackSupport.step ? "\247a" : "\247c") + HackSupport.step));
				}
				else if(split[0].equalsIgnoreCase("protect")) {
					HackSupport.protect = !HackSupport.protect;
					mc.inGameHud.getChatHud().addMessage(new LiteralText("Protect: " + (HackSupport.protect ? "\247a" : "\247c") + HackSupport.protect));
				}
				else if(split[0].equalsIgnoreCase("nofall")) {
					HackSupport.nofall = !HackSupport.nofall;
					mc.inGameHud.getChatHud().addMessage(new LiteralText("NoFall: " + (HackSupport.nofall ? "\247a" : "\247c") + HackSupport.nofall));
				}
				else if(split[0].equalsIgnoreCase("esp")) {
					HackSupport.esp = !HackSupport.esp;
					mc.inGameHud.getChatHud().addMessage(new LiteralText("ESP: " + (HackSupport.esp ? "\247a" : "\247c") + HackSupport.esp));
				}
				else if(split[0].equalsIgnoreCase("tracers")) {
					HackSupport.tracers = !HackSupport.tracers;
					mc.inGameHud.getChatHud().addMessage(new LiteralText("Tracers: " + (HackSupport.tracers ? "\247a" : "\247c") + HackSupport.tracers));
				}
				else if(split[0].equalsIgnoreCase("torch")) {
					HackSupport.torch = !HackSupport.torch;
					mc.inGameHud.getChatHud().addMessage(new LiteralText("Torch: " + (HackSupport.torch ? "\247a" : "\247c") + HackSupport.torch));
				}
				else if(split[0].equalsIgnoreCase("flyspeed")) {
					try {
						HackSupport.flySpeed = Float.parseFloat(split[1]);
						mc.inGameHud.getChatHud().addMessage(new LiteralText("flySpeed: \247e" + HackSupport.flySpeed));
					} catch (Exception e) { }
				}
				else if(split[0].equalsIgnoreCase("speedspeed")) {
					try {
						HackSupport.speedSpeed = Float.parseFloat(split[1]);
						mc.inGameHud.getChatHud().addMessage(new LiteralText("speedSpeed: \247e" + HackSupport.speedSpeed));
					} catch (Exception e) { }
				}
				else if(split[0].equalsIgnoreCase("gamma")) {
					try {
						HackSupport.gamma = Integer.parseInt(split[1]);
						mc.inGameHud.getChatHud().addMessage(new LiteralText("gamma: \247e" + HackSupport.gamma));
					} catch (Exception e) { }
				}
				else if(split[0].equalsIgnoreCase("reach")) {
					try {
						HackSupport.reach = Integer.parseInt(split[1]);
						mc.inGameHud.getChatHud().addMessage(new LiteralText("reach: \247e" + HackSupport.reach));
					} catch (Exception e) { }
				}
				else if (split[0].equalsIgnoreCase("add")) {
				      String alias = split[1];
				      if (split.length > 3) {
				        alias = split[2];
				        if (alias.startsWith("\"") && split[split.length - 1].endsWith("\"")) {
				          alias = alias.substring(1, alias.length());
				          for (int i = 3; i < split.length; i++)
				            alias = String.valueOf(String.valueOf(alias)) + " " + split[i].replace("\"", ""); 
				        } 
				      } 
				      if (Client.friends.isFriend(split[1]) && split.length < 3) {
				        mc.inGameHud.getChatHud().addMessage(new LiteralText("\247e" + String.valueOf(String.valueOf(split[1])) + "\247r is already your friend."));
				        return;
				      } 
				      Client.friends.removeFriend(split[1]);
				      Client.friends.addFriend(split[1], alias);
				      mc.inGameHud.getChatHud().addMessage(new LiteralText("Added \247e" + split[1] + ((split.length > 3) ? (" as \247b" + alias) : "")));
				    } else if (split[0].equalsIgnoreCase("del")) {
				      if (Client.friends.isFriend(split[1])) {
				        Client.friends.removeFriend(split[1]);
				        mc.inGameHud.getChatHud().addMessage(new LiteralText("Removed friend: \247e" + split[1]));
				      } else {
				        mc.inGameHud.getChatHud().addMessage(new LiteralText("\247e" + String.valueOf(String.valueOf(split[1])) + "\247r is not your friend."));
				      } 
				    }
			}
		}
	}
	
}
