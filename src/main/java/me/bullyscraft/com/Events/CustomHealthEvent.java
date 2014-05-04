package me.bullyscraft.com.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomHealthEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private Player player; 
	
	public CustomHealthEvent(Player player){
		this.player = player;
		
	}
	
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	public Player getPlayer(){
		return player;
	}
	

}
