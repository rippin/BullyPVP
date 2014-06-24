package me.bullyscraft.com.Stats;

import me.bullyscraft.com.BullyPVP;
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
		sender.sendMessage(ChatColor.GREEN + "K/D: " + String.format( "%.2f", kd));
		sender.sendMessage(ChatColor.GREEN + "Highest Streak: " + s );
		sender.sendMessage(ChatColor.GREEN + "Current Streak: " + c );
		sender.sendMessage(ChatColor.GREEN + "Coins: " + coins);

        if (BullyPVP.instance.isBully1v1Enabled()){
            int wins = pso.getWins1v1();
            int losses = pso.getLosses1v1();
            int streak1v1 = pso.getCurrentStreak1v1();
            int highstreak1v1 = pso.getHighStreak1v1();
            sender.sendMessage(ChatColor.GREEN + "1v1 Wins: " + wins);
            sender.sendMessage(ChatColor.GREEN + "1v1 Losses: " + losses);
            sender.sendMessage(ChatColor.GREEN + "1v1 Current Streak: " + streak1v1);
            sender.sendMessage(ChatColor.GREEN + "1v1 Highest Streak: " + highstreak1v1);

        }
	
	}

}
