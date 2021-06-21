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
		HackSupport.xrayBlocks.add(Blocks.FURNACE);
		HackSupport.xrayBlocks.add(Blocks.CRAFTING_TABLE);
		HackSupport.xrayBlocks.add(Blocks.CHEST);
		HackSupport.xrayBlocks.add(Blocks.ENDER_CHEST);
		HackSupport.xrayBlocks.add(Blocks.TRAPPED_CHEST);
		HackSupport.xrayBlocks.add(Blocks.HOPPER);
		HackSupport.xrayBlocks.add(Blocks.DISPENSER);
		HackSupport.xrayBlocks.add(Blocks.SHULKER_BOX);
		HackSupport.xrayBlocks.add(Blocks.BLACK_SHULKER_BOX);
		HackSupport.xrayBlocks.add(Blocks.BLUE_SHULKER_BOX);
		HackSupport.xrayBlocks.add(Blocks.BROWN_SHULKER_BOX);
		HackSupport.xrayBlocks.add(Blocks.CYAN_SHULKER_BOX);
		HackSupport.xrayBlocks.add(Blocks.GRAY_SHULKER_BOX);
		HackSupport.xrayBlocks.add(Blocks.GREEN_SHULKER_BOX);
		HackSupport.xrayBlocks.add(Blocks.LIGHT_BLUE_SHULKER_BOX);
		HackSupport.xrayBlocks.add(Blocks.LIGHT_GRAY_SHULKER_BOX);
		HackSupport.xrayBlocks.add(Blocks.LIME_SHULKER_BOX);
		HackSupport.xrayBlocks.add(Blocks.MAGENTA_SHULKER_BOX);
		HackSupport.xrayBlocks.add(Blocks.ORANGE_SHULKER_BOX);
		HackSupport.xrayBlocks.add(Blocks.PINK_SHULKER_BOX);
		HackSupport.xrayBlocks.add(Blocks.PURPLE_SHULKER_BOX);
		HackSupport.xrayBlocks.add(Blocks.RED_SHULKER_BOX);
		HackSupport.xrayBlocks.add(Blocks.WHITE_SHULKER_BOX);
		HackSupport.xrayBlocks.add(Blocks.YELLOW_SHULKER_BOX);
		HackSupport.xrayBlocks.add(Blocks.BARREL);
		
		HackSupport.wallhackBlocks.add(Blocks.FURNACE);
		HackSupport.wallhackBlocks.add(Blocks.CRAFTING_TABLE);
		HackSupport.wallhackBlocks.add(Blocks.CHEST);
		HackSupport.wallhackBlocks.add(Blocks.ENDER_CHEST);
		HackSupport.wallhackBlocks.add(Blocks.TRAPPED_CHEST);
		HackSupport.wallhackBlocks.add(Blocks.HOPPER);
		HackSupport.wallhackBlocks.add(Blocks.DISPENSER);
		HackSupport.wallhackBlocks.add(Blocks.SHULKER_BOX);
		HackSupport.wallhackBlocks.add(Blocks.BLACK_SHULKER_BOX);
		HackSupport.wallhackBlocks.add(Blocks.BLUE_SHULKER_BOX);
		HackSupport.wallhackBlocks.add(Blocks.BROWN_SHULKER_BOX);
		HackSupport.wallhackBlocks.add(Blocks.CYAN_SHULKER_BOX);
		HackSupport.wallhackBlocks.add(Blocks.GRAY_SHULKER_BOX);
		HackSupport.wallhackBlocks.add(Blocks.GREEN_SHULKER_BOX);
		HackSupport.wallhackBlocks.add(Blocks.LIGHT_BLUE_SHULKER_BOX);
		HackSupport.wallhackBlocks.add(Blocks.LIGHT_GRAY_SHULKER_BOX);
		HackSupport.wallhackBlocks.add(Blocks.LIME_SHULKER_BOX);
		HackSupport.wallhackBlocks.add(Blocks.MAGENTA_SHULKER_BOX);
		HackSupport.wallhackBlocks.add(Blocks.ORANGE_SHULKER_BOX);
		HackSupport.wallhackBlocks.add(Blocks.PINK_SHULKER_BOX);
		HackSupport.wallhackBlocks.add(Blocks.PURPLE_SHULKER_BOX);
		HackSupport.wallhackBlocks.add(Blocks.RED_SHULKER_BOX);
		HackSupport.wallhackBlocks.add(Blocks.WHITE_SHULKER_BOX);
		HackSupport.wallhackBlocks.add(Blocks.YELLOW_SHULKER_BOX);
		HackSupport.wallhackBlocks.add(Blocks.BARREL);
	}
	
}

