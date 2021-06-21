package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.friend.FriendManager;
import net.fabricmc.example.utils.Timer;
import net.minecraft.block.Blocks;

/**
 * 
 * @author Michael "dotexe" Shipley
 * Copypasta client works. www.dotexe.cf
 *
 */
public class Client implements ModInitializer {
	
	public static FriendManager friends;
	
	public static Timer flyTimer;
	public static Timer torchTimer;
	
	@Override
	public void onInitialize() {
		friends = new FriendManager();
		flyTimer = new Timer();
		torchTimer = new Timer();
		
		HackSupport.xrayBlocks.add(Blocks.COPPER_ORE);
		HackSupport.xrayBlocks.add(Blocks.IRON_ORE);
		HackSupport.xrayBlocks.add(Blocks.GOLD_ORE);
		HackSupport.xrayBlocks.add(Blocks.LAPIS_ORE);
		HackSupport.xrayBlocks.add(Blocks.REDSTONE_ORE);
		HackSupport.xrayBlocks.add(Blocks.DIAMOND_ORE);
		HackSupport.xrayBlocks.add(Blocks.EMERALD_ORE);
		HackSupport.xrayBlocks.add(Blocks.COPPER_BLOCK);
		HackSupport.xrayBlocks.add(Blocks.IRON_BLOCK);
		HackSupport.xrayBlocks.add(Blocks.GOLD_BLOCK);
		HackSupport.xrayBlocks.add(Blocks.LAPIS_BLOCK);
		HackSupport.xrayBlocks.add(Blocks.REDSTONE_BLOCK);
		HackSupport.xrayBlocks.add(Blocks.DIAMOND_BLOCK);
		HackSupport.xrayBlocks.add(Blocks.EMERALD_BLOCK);
		HackSupport.xrayBlocks.add(Blocks.NETHER_GOLD_ORE);
		HackSupport.xrayBlocks.add(Blocks.ANCIENT_DEBRIS);
	}
	
}
