package me.bullyscraft.com.Listeners;

import me.bullyscraft.com.BullyPVP;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropListener implements Listener {

	
	@SuppressWarnings("unused")
	private BullyPVP plugin;

	public PlayerDropListener(BullyPVP plugin){
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event){
		Player player = event.getPlayer();
		
		event.setCancelled(true);
		player.sendMessage(ChatColor.RED + "You may not drop items!");
		
		}

}
