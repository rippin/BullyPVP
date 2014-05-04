package me.bullyscraft.com.Listeners;


import me.bullyscraft.com.BullyPVP;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

public class InventoryClickListener implements Listener {

	@SuppressWarnings("unused")
	private BullyPVP plugin;

	public InventoryClickListener(BullyPVP plugin){
		this.plugin = plugin;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		if(event.getWhoClicked() instanceof Player){
			Player player = (Player) event.getWhoClicked();
			
			if(event.getSlotType() == SlotType.ARMOR || event.getSlot() == 0){
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED + "Nope!");
				}
			}
	}
}
