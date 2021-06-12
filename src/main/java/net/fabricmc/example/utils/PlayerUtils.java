package net.fabricmc.example.utils;

import net.fabricmc.example.ClientSupport;
import net.minecraft.util.math.MathHelper;

public class PlayerUtils implements ClientSupport {
	
	public static boolean isMoving() {
		return mc.player.forwardSpeed != 0.0f || mc.player.sidewaysSpeed != 0.0f;
	}
	
	public static float getDirection()
    {
    	float yaw = mc.player.headYaw;
      	float forward = mc.player.forwardSpeed;
      	float strafe = mc.player.sidewaysSpeed;
      	yaw += (forward < 0.0F ? 180 : 0);
      	if (strafe < 0.0F) {
      		yaw += (forward == 0.0F ? 90 : forward < 0.0F ? -45 : 45);
      	}
      	if (strafe > 0.0F) {
      		yaw -= (forward == 0.0F ? 90 : forward < 0.0F ? -45 : 45);
      	}
      	return yaw * 0.017453292F;
    }
	
	public static void setSpeed(double speed) {
	    mc.player.setVelocity(-MathHelper.sin(getDirection()) * speed, mc.player.getVelocity().getY(), MathHelper.cos(getDirection()) * speed);
	}
	
}
