package me.bullyscraft.com.Stats;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class ViewStats {

	public static void viewStats(Player sender, PlayerStatsObject pso){

        int kills = pso.getKills();
		int deaths = pso.getDeaths();
		int coins = pso.getCoins();
		double kd = pso.getKd();
		int s = pso.getHigheststreak();
		int c = pso.getCurrentstreak();
        String name = pso.getUsername();

        sender.sendMessage(ChatColor.AQUA + "Stats for " + name);
		sender.sendMessage(ChatColor.GREEN + "Kills: " + kills );
		sender.sendMessage(ChatColor.GREEN + "Deaths: " + deaths );
		sender.sendMessage(ChatColor.GREEN + "K/D: " + kd );
		sender.sendMessage(ChatColor.GREEN + "Highest Streak: " + s );
		sender.sendMessage(ChatColor.GREEN + "Current Streak: " + c );
		sender.sendMessage(ChatColor.GREEN + "Coins: " + coins);
	
	}

}
