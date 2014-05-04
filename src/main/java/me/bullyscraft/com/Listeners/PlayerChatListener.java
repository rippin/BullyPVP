package me.bullyscraft.com.Listeners;

import me.bullyscraft.com.BullyPVP;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

private BullyPVP plugin;



	public PlayerChatListener(BullyPVP plugin){
	
		this.plugin = plugin;
	}
	
	@EventHandler
	public void playerChatEvent(AsyncPlayerChatEvent event){
	
		Player player = event.getPlayer();
	
	String s = event.getMessage();
	
	if (s.charAt(0) != '/'){
	event.setFormat(plugin.prefix + s);
	
	}
	
}






}
