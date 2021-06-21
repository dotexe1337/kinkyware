package net.fabricmc.example.utils;

import net.fabricmc.example.ClientSupport;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;

public class BlockUtils implements ClientSupport {
	
	public static boolean breakOneBlock(double x, double y, double z)
	{
		Direction side = null;
		Direction[] sides = Direction.values();
		
		BlockPos pos = new BlockPos(x, y, z);
		
		BlockState state = BlockUtils.getBlockState(x, y, z);
		VoxelShape shape = state.getOutlineShape(mc.world, pos);
		if(shape.isEmpty())
			return false;
		
		Vec3d eyesPos = mc.player.getEyePos();
		Vec3d relCenter = shape.getBoundingBox().getCenter();
		Vec3d center = Vec3d.of(pos).add(relCenter);
		
		Vec3d[] hitVecs = new Vec3d[sides.length];
		for(int i = 0; i < sides.length; i++)
		{
			Vec3i dirVec = sides[i].getVector();
			Vec3d relHitVec = new Vec3d(relCenter.x * dirVec.getX(),
				relCenter.y * dirVec.getY(), relCenter.z * dirVec.getZ());
			hitVecs[i] = center.add(relHitVec);
		}
		
		for(int i = 0; i < sides.length; i++)
		{
			// check line of sight
			if(mc.world.raycastBlock(eyesPos, hitVecs[i], pos, shape,
				state) != null)
				continue;
			
			side = sides[i];
			break;
		}
		
		if(side == null)
		{
			double distanceSqToCenter = eyesPos.squaredDistanceTo(center);
			for(int i = 0; i < sides.length; i++)
			{
				// check if side is facing towards player
				if(eyesPos.squaredDistanceTo(hitVecs[i]) >= distanceSqToCenter)
					continue;
				
				side = sides[i];
				break;
			}
		}
		
		// player is inside of block, side doesn't matter
		if(side == null)
			side = sides[0];
		
		// damage block
		if(!mc.interactionManager.updateBlockBreakingProgress(pos, side))
			return false;
		
		// swing arm
		mc.player.networkHandler
			.sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
		
		return true;
	}
	
	 public static Block getBlock(double x, double y, double z) {
	        return mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
	 }
	 
	 public static BlockState getBlockState(double x, double y, double z) {
		 return mc.world.getBlockState(new BlockPos(x, y, z));
	 }
	
}
