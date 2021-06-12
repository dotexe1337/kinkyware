package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.friend.FriendManager;

/**
 * 
 * @author Michael "dotexe" Shipley
 * Copypasta client works. www.dotexe.cf
 *
 */
public class Client implements ModInitializer {
	
	public static FriendManager friends;
	
	@Override
	public void onInitialize() {
		friends = new FriendManager();
	}
	
}
