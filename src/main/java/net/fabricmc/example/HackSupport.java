package net.fabricmc.example;

import net.fabricmc.example.utils.PlayerCopyEntity;
import net.minecraft.entity.Entity;

public class HackSupport {
	
	public static int gamma = 1;
	
	public static boolean hud = true;
	public static boolean protect = false;
	
	public static float flySpeed = 1f;
	public static float speedSpeed = 1f;
	
	public static boolean flight = false;
	public static boolean speed = false;
	public static boolean step = false;
	public static boolean nofall = false;
	public static boolean killAura = false;
	public static boolean speedMine = false;
	public static boolean sneak = false;
	public static boolean freecam = false;
	
	public static boolean esp = false;
	public static boolean tracers = false;
	
	/* Freecam */
	public static PlayerCopyEntity dummy;
	public static double[] playerPos;
	public static float[] playerRot;
	public static Entity riding;

	public static boolean prevFlying;
	public static float prevFlySpeed;
	
}
