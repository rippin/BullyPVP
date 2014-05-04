package me.bullyscraft.com.Listeners;

import me.bullyscraft.com.AssistHandler;
import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Events.CustomHealthEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CustomHealthListener implements Listener {

	private BullyPVP plugin;
	
	public CustomHealthListener(BullyPVP plugin){
		this.plugin = plugin;
		
	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void onMushroomSoupConsume(CustomHealthEvent event){
		
		Player player = event.getPlayer();
		
		if (player.getHealth() == 20){
			if (plugin.damage.containsKey(player.getName())){
			AssistHandler n = plugin.damage.get(player.getName());
			n.clear();
			}
		}
	}
}
