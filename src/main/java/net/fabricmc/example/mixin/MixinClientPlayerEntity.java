package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import net.fabricmc.example.Client;
import net.fabricmc.example.ClientSupport;
import net.fabricmc.example.HackSupport;
import net.fabricmc.example.utils.BlockUtils;
import net.fabricmc.example.utils.PlayerUtils;
import net.minecraft.block.AirBlock;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
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
			if(PlayerUtils.isMoving() && !mc.player.input.sneaking)
				PlayerUtils.setSpeed(HackSupport.flySpeed);
			if(Client.flyTimer.hasPassed(75)) {
				if(BlockUtils.getBlock(mc.player.getPos().getX(), mc.player.getPos().getY() - 0.034, mc.player.getPos().getZ()) instanceof AirBlock) {
					mc.getNetworkHandler().getConnection().send(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getPos().getX(), mc.player.getPos().getY() - 0.035, mc.player.getPos().getZ(), true));
					Client.flyTimer.updateLastTime();
				}
			} else {
				if(BlockUtils.getBlock(mc.player.getPos().getX(), mc.player.getPos().getY() + 0.034, mc.player.getPos().getZ()) instanceof AirBlock) {
					mc.getNetworkHandler().getConnection().send(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getPos().getX(), mc.player.getPos().getY() + 0.035, mc.player.getPos().getZ(), true));
				}
			}
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
		if(HackSupport.killAura) {
			for(Entity e: mc.world.getEntities()) {
				if(e instanceof PlayerEntity) {
					PlayerEntity pe = (PlayerEntity) e;
					if(pe.distanceTo(mc.player) <= 6 && mc.player.getAttackCooldownProgress(0.0f) >= 1.0f) {
						mc.player.attack(pe);
						mc.player.swingHand(Hand.MAIN_HAND);
					}
				}
			}
		}
	}
	
}
