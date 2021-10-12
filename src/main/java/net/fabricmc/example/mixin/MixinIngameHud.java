package net.fabricmc.example.mixin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.example.ClientSupport;
import net.fabricmc.example.HackSupport;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.util.math.MathHelper;

@Mixin(InGameHud.class)
public class MixinIngameHud implements ClientSupport {
	
	@Inject(method = "renderScoreboardSidebar", at = @At("HEAD"), cancellable = true)
	public void renderScoreboardSidebar(MatrixStack matrices, ScoreboardObjective objective, CallbackInfo ci) {
		if(!HackSupport.scoreboard) {
			ci.cancel();
		}
	}
	
	@Inject(method = "render", at = @At("RETURN"), cancellable = true)
	public void render(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
		if(HackSupport.hud) {
			matrixStack.push();
			matrixStack.scale(0.5f, 0.5f, 0.5f);
			
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "\247oKinkyware \2477v0.0.4_1", 2, 2, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "flyHSpeed: \247e" + HackSupport.flyHSpeed, 2, 12, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "flyVSpeed: \247e" + HackSupport.flyVSpeed, 2, 22, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "speedSpeed: \247e" + HackSupport.speedSpeed, 2, 32, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "freecamHSpeed: \247e" + HackSupport.freecamHSpeed, 2, 42, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "freecamVSpeed: \247e" + HackSupport.freecamVSpeed, 2, 52, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "reach: \247e" + HackSupport.reach, 2, 62, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "stepHeight: \247e" + HackSupport.stepHeight, 2, 72, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "gamma: \247e" + HackSupport.gamma, 2, 82, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "Scoreboard: " + (HackSupport.scoreboard ? "\247a" : "\247c") + HackSupport.scoreboard, 2, 92, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "Protect: " + (HackSupport.protect ? "\247a" : "\247c") + HackSupport.protect, 2, 102, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "ESP: " + (HackSupport.esp ? "\247a" : "\247c") + HackSupport.esp, 2, 112, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "Tracers: " + (HackSupport.tracers ? "\247a" : "\247c") + HackSupport.tracers, 2, 122, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "X-Ray: " + (HackSupport.xray ? "\247a" : "\247c") + HackSupport.xray, 2, 132, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "Wallhack: " + (HackSupport.wallhack ? "\247a" : "\247c") + HackSupport.wallhack, 2, 142, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "NoFall: " + (HackSupport.nofall ? "\247a" : "\247c") + HackSupport.nofall, 2, 152, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "Flight: " + (HackSupport.flight ? "\247a" : "\247c") + HackSupport.flight, 2, 162, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "Speed: " + (HackSupport.speed ? "\247a" : "\247c") + HackSupport.speed, 2, 172, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "Sneak: " + (HackSupport.sneak ? "\247a" : "\247c") + HackSupport.sneak, 2, 182, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "Freecam: " + (HackSupport.freecam ? "\247a" : "\247c") + HackSupport.freecam, 2, 192, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "SpeedMine: " + (HackSupport.speedMine ? "\247a" : "\247c") + HackSupport.speedMine, 2, 202, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "Torch: " + (HackSupport.torch ? "\247a" : "\247c") + HackSupport.torch, 2, 212, 0xffffffff);
			mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "KillAura: " + (HackSupport.killAura ? "\247a" : "\247c") + HackSupport.killAura, 2, 222, 0xffffffff);
			
			if(HackSupport.playerList) {
				List<PlayerEntity> entities = new ArrayList<PlayerEntity>();
				for(Entity e: mc.world.getEntities()) {
					
					if(e instanceof PlayerEntity && e != mc.player && e != mc.cameraEntity && !((PlayerEntity) e).getGameProfile().getName().equalsIgnoreCase(mc.getSession().getUsername())) {
						PlayerEntity pe = (PlayerEntity) e;
						entities.add(pe);
					}
				}
				entities.sort(new Comparator<PlayerEntity>() {
					public int compare(PlayerEntity m1, PlayerEntity m2) {
			            float f1 = m1.distanceTo(mc.player);
			            float f2 = m2.distanceTo(mc.player);
			            return Float.compare(f1, f2);
			        }
				});
				mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, "\2478\247l\247oPlayer List:", (mc.getWindow().getScaledWidth() * 2) - mc.inGameHud.getTextRenderer().getWidth("\2478\247l\247oPlayer List:") - 2, 2, 0xffffffff);
				int y = 12;
				for(PlayerEntity pe: entities) {
					mc.inGameHud.getTextRenderer().drawWithShadow(matrixStack, ((pe.distanceTo(mc.player) >= 64) ? "\2472" : (pe.isSneaking() ? "\2474" : "\247f")) + pe.getName().getString() + " \2477(D: " + MathHelper.floor(pe.distanceTo(mc.player)) + ", XYZ: " + (MathHelper.floor(pe.getPos().getX()) + " / " + MathHelper.floor(pe.getPos().getY()) + " / " + MathHelper.floor(pe.getPos().getZ())) + ")", (mc.getWindow().getScaledWidth() * 2) - (mc.inGameHud.getTextRenderer().getWidth( ((pe.distanceTo(mc.player) >= 64) ? "\2472" : (pe.isSneaking() ? "\2474" : "\247f")) + pe.getName().getString() + " \2477(D: " + MathHelper.floor(pe.distanceTo(mc.player)) + ", XYZ: " + (MathHelper.floor(pe.getPos().getX()) + " / " + MathHelper.floor(pe.getPos().getY()) + " / " + MathHelper.floor(pe.getPos().getZ())) + ")")) - 2, y, 0xffffffff);
					y+=10;
				}
			}
			
			matrixStack.pop();
		}
	}
}
