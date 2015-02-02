package me.bullyscraft.com.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class CacheRankingEvent extends Event {
    private static final HandlerList handlers = new HandlerList();


    public CacheRankingEvent(){


    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
