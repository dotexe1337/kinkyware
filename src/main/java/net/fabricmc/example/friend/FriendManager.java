package net.fabricmc.example.friend;

import java.util.ArrayList;

import net.fabricmc.example.ClientSupport;
import net.fabricmc.example.utils.StringUtils;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;

public class FriendManager implements ClientSupport {
	  
	  public static ArrayList<Friend> friendsList = new ArrayList<>();
	  
	  public static void addFriend(String name, String alias) {
	    friendsList.add(new Friend(name, alias));
	  }
	  
	  public static String getAliasName(String name) {
	    String alias = "";
	    for (Friend friend : friendsList) {
	      if (friend.name.equalsIgnoreCase(StringUtils.stripControlCodes(name))) {
	        alias = friend.alias;
	        break;
	      } 
	    } 
	    if ((mc.player != null && mc.player.getGameProfile().getName() == name))
	      return name; 
	    return alias;
	  }
	  
	  public static void removeFriend(String name) {
	    for (Friend friend : friendsList) {
	      if (friend.name.equalsIgnoreCase(name)) {
	        friendsList.remove(friend);
	        break;
	      } 
	    } 
	  }
	  
	  public static String replaceText(String text) {
	    for (Friend friend : friendsList) {
	      if (text.contains(friend.name))
	        text = friend.alias; 
	    } 
	    return text;
	  }
	  
	  public static boolean isFriend(String name) {
	    boolean isFriend = false;
	    if (mc.getSession().getUsername().equalsIgnoreCase(StringUtils.stripControlCodes(name))) {
		      return true;
	    }
	    for (Friend friend : friendsList) {
	      if (friend.name.equalsIgnoreCase(StringUtils.stripControlCodes(name))) {
	        isFriend = true;
	        break;
	      } 
	    } 
	    return isFriend;
	  }
	  
		public String protect(String string)
		{
			if(mc.player == null)
				return string;
			
			String me = mc.getSession().getUsername();
			if(string.contains(me))
				string = string.replace(me, "\2479Biggus Dickus\247r");
			
			int i = 0;
			for(PlayerListEntry info : mc.player.networkHandler.getPlayerList())
			{
				i++;
				String name =
					info.getProfile().getName().replaceAll("\u00a7(?:\\w|\\d)", "");
				
				if(string.contains(name))
					if(this.isFriend(name))
						string = string.replace(name, "\2479" + this.getAliasName(name) + "\247r");
			}
			
			for(AbstractClientPlayerEntity player : mc.world.getPlayers())
			{
				i++;
				String name = player.getName().getString();
				
				if(string.contains(name))
					if(this.isFriend(name))
						string = string.replace(name, "\2479" + this.getAliasName(name) + "\247r");
			}
			
			return string;
		}
}
