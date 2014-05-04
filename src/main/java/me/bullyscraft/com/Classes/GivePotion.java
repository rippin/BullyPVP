package me.bullyscraft.com.Classes;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GivePotion {

public static void giveSpeed(Player player, int power){
	
	player.addPotionEffect(new PotionEffect(
			PotionEffectType.SPEED, 12000, power), true);
	player.sendMessage(ChatColor.GREEN + "Speed 1 has been added for 10 minutes.");
}

public static void giveStrength(Player player, int power){
	
	player.addPotionEffect(new PotionEffect(
			PotionEffectType.INCREASE_DAMAGE, 12000, power), true);
	player.sendMessage(ChatColor.GREEN + "Strength 1 has been added for 10 minutes.");
}

}
