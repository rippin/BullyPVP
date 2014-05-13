package me.bullyscraft.com;
import java.util.HashMap;
import java.util.Map;

import me.bullyscraft.com.Stats.PlayerStatsObject;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CoinAlgorithm {

	public static double calculateWorth(double kd){
		if (kd > 4.5){
			return 60;
		}
		else if (kd < 0.5){
			return 10;
		}
		else{
			return (double) kd * 14;
		}
	}
	
	
	public static void splitAssist(HashMap<String, Double> n, double worth, String dead, String killer, BullyPVP plugin){
		
	double values = 0;
	for (double v: n.values()){
		values = (values + v);
	}
	//removes killer from hashmap because he got the kill so it wasnt an assist	
	if (n.containsKey(killer)){
		
		n.remove(killer);
	}
	//if player that died did damage to himself remove from hashmap
	if (n.containsKey(dead)){
		n.remove(dead);
	}
	//loop through the hashmap
	for (Map.Entry<String, Double> e: n.entrySet()){
		//get the player name and damage done.
		String name = e.getKey();
		double dam = e.getValue();
		
		if (Bukkit.getPlayerExact(name) != null){
			Player p = Bukkit.getPlayerExact(name);
			float percentage = (float) (dam/values);
			
			int coins = (int) (worth * percentage);

            PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(p, plugin);
            pso.addCoins(coins);
			p.sendMessage(ChatColor.GREEN + "You received " + ChatColor.AQUA 
					+ coins + ChatColor.GREEN  + " coins for doing " + ChatColor.GREEN + String.format( "%.3f",(percentage * 100)) + "%"
					+ " of total damage to " + ChatColor.AQUA + dead);
			}
		}
	}
}
