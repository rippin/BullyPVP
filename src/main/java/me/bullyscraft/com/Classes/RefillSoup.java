package me.bullyscraft.com.Classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class RefillSoup {

	public static void refill(Player player) {

		PlayerInventory inv = player.getInventory();

		ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP);

		inv.remove(Material.BOWL);

		for (int i = 0; i < 33; i++) {

			inv.addItem(soup);

		}
		player.sendMessage(ChatColor.GREEN + "Soup refilled.");

	}

	public static void soup(Player player) {

		PlayerInventory inv = player.getInventory();
		ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP);
		for (int i = 0; i < 15; i++) {

			inv.addItem(soup);

		}

	}
}