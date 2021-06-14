package net.fabricmc.example.utils;

import net.fabricmc.example.ClientSupport;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class BlockUtils implements ClientSupport {
	
	 public static Block getBlock(double x, double y, double z) {
	        return mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
	 }
	
}
