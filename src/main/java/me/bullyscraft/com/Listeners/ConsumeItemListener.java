package me.bullyscraft.com.Listeners;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Events.CustomHealthEvent;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ConsumeItemListener implements Listener {

	@SuppressWarnings("unused")
	private BullyPVP plugin;

	public ConsumeItemListener(BullyPVP plugin) {
		this.plugin = plugin;

	}

	@EventHandler
	public void itemConsume(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_AIR
				|| (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			if (event.getItem() != null) {
				if (event.getItem().getType() == Material.MUSHROOM_SOUP) {
					event.setCancelled(true);
					if (player.getHealth() != 20) {
						player.setItemInHand(null);
						double ph = player.getHealth() + 6;
						if (ph > 20) {
							ph = 20;
						}
						player.setHealth(ph);
						CustomHealthEvent e = new CustomHealthEvent(player);
						Bukkit.getServer().getPluginManager().callEvent(e);
					} else {
						return;
					}
				}
			}
		}
	}

}
