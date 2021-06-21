package net.fabricmc.example;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.example.utils.PlayerCopyEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;

public class HackSupport {
	
	public static boolean scoreboard = true;
	
	public static int gamma = 1;
	public static float reach = 5f;
	
	public static boolean hud = true;
	public static boolean protect = false;
	
	public static float flyHSpeed = 1f;
	public static float flyVSpeed = 1f;
	public static float speedSpeed = 1f;
	public static float freecamHSpeed = 1f;
	public static float freecamVSpeed = 1f;
	
	public static boolean flight = false;
	public static boolean speed = false;
	public static boolean step = false;
	public static boolean nofall = false;
	public static boolean killAura = false;
	public static boolean speedMine = false;
	public static boolean sneak = false;
	public static boolean freecam = false;
	public static boolean torch = false;
	
	public static boolean esp = false;
	public static boolean tracers = false;
	public static boolean xray = false;
	public static boolean wallhack = false;
	
	public static boolean isVisibleXray(Block block) {
		return xrayBlocks.contains(block);
	}
	public static boolean isVisibleWallhack(Block block) {
		return wallhackBlocks.contains(block);
	}
	
	public static List<Block> xrayBlocks = new ArrayList<>();
	public static List<Block> wallhackBlocks = new ArrayList<>();
	
	/* Freecam */
	public static PlayerCopyEntity dummy;
	public static double[] playerPos;
	public static float[] playerRot;
	public static Entity riding;

	public static boolean prevFlying;
	public static float prevFlySpeed;
	
}
