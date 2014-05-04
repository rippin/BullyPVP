package me.bullyscraft.com.Listeners;

import me.bullyscraft.com.BullyPVP;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupListener implements Listener {

	@SuppressWarnings("unused")
	private BullyPVP plugin;

	public PlayerPickupListener(BullyPVP plugin){
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerDropItem(PlayerPickupItemEvent event){
		
		event.setCancelled(true);
	}
}