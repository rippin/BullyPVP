package me.bullyscraft.com.Listeners;

import me.bullyscraft.com.BullyPVP;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodChangeListener implements Listener {

	public BullyPVP plugin;

	public FoodChangeListener(BullyPVP plugin){
	this.plugin = plugin;
	
	}
	
	@EventHandler
	public void hungerChange(FoodLevelChangeEvent event){
		if (event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			event.setCancelled(true);
			player.setFoodLevel(20);
			
		}
		
	}


}
