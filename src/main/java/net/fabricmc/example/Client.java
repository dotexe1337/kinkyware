package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.friend.FriendManager;
import net.fabricmc.example.utils.Timer;

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
	}
	
}
