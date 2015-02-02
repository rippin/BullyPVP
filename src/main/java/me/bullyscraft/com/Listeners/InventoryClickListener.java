package me.bullyscraft.com.Listeners;


import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Classes.KitGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

	@SuppressWarnings("unused")
	private BullyPVP plugin;

	public InventoryClickListener(BullyPVP plugin){
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent event){
		if(event.getWhoClicked() instanceof Player){
			Player player = (Player) event.getWhoClicked();
            if (event.getInventory().getType() == InventoryType.PLAYER || event.getInventory().getType() == InventoryType.CRAFTING  ) {
			if(event.getSlotType() == SlotType.ARMOR || event.getSlot() == 0){
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED + "Nope!");
				}
			}
            else if (event.getInventory().getName().contains("Kit Selector")){
                if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT){
                    event.setCancelled(true);
                    return;
                }
                if (event.getCurrentItem() != null || event.getCurrentItem().getType() != Material.AIR){
                    String split[] = event.getInventory().getName().split(" ");
                    if (event.getSlot() == 40){
                        event.getWhoClicked().openInventory(KitGUI.getGuiList().get(Integer.parseInt(split[2])));
                    }
                    else if (event.getSlot() == 39){
                        event.getWhoClicked().openInventory(KitGUI.getGuiList().get(Integer.parseInt(split[2]) - 2));
                    }
                    else {
                        if (event.getCurrentItem().getItemMeta() != null) {
                        String name = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
                        Bukkit.getServer().dispatchCommand(player, "kit " + name);
                        player.closeInventory();
                        }
                    }

                }
                event.setCancelled(true);
            }
        }
	}
}
