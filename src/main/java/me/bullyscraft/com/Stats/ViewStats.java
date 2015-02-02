package me.bullyscraft.com.Stats;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Scoreboards.BullyScoreBoard;
import me.bullyscraft.com.Scoreboards.BullyScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;


public class ViewStats {
    private static Map<String, String> top10RankCache = new LinkedHashMap<String, String>();
	public static void viewStats(CommandSender sender, PlayerStatsObject pso){

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
        sender.sendMessage(ChatColor.GREEN + "Ranking: " + pso.getRanking());

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

    public static void resetStats(Player player, BullyPVP plugin){
        PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(player, plugin);
        pso.setKills(0);
        pso.setDeaths(0);
        pso.setHigheststreak(0);
        pso.setCurrentstreak(0);
        pso.setRanking("0");
        if (plugin.isBully1v1Enabled()){
            pso.setWins1v1(0);
            pso.setLosses1v1(0);
            pso.setHighStreak1v1(0);
            pso.setCurrentStreak1v1(0);
        }
        BullyScoreBoard b = BullyScoreboardManager.getBullyScoreboard(player.getUniqueId().toString());
        b.setPSO(pso);
        b.updateWithoutPrefixes();
        player.sendMessage(ChatColor.GREEN + "Stats reset.");
    }


    public static void top10RankCache(){
        top10RankCache.clear();
        List<PlayerStatsObject> keys = new ArrayList<PlayerStatsObject>(PlayerStatsObjectManager.sorted.keySet());
        ListIterator<PlayerStatsObject> it = keys.listIterator(keys.size());
        int i = 0;
        while (it.hasPrevious()){
            if (i > 10){
                return;
            }
            PlayerStatsObject pso = it.previous();
            OfflinePlayer player = Bukkit.getOfflinePlayer(pso.getUUID());
            top10RankCache.put(player.getName(), pso.getRanking());
        }
    }

    public static void printTop10Rank(CommandSender sender){
        sender.sendMessage(ChatColor.GOLD + "========" + ChatColor.RED + " TOP 10 RANK " + ChatColor.GOLD + "========");
        Iterator it = top10RankCache.entrySet().iterator();
        int i = 1;
        while (it.hasNext()){
            if (i > 10){
                return;
            }
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            sender.sendMessage(ChatColor.AQUA + entry.getKey() + ChatColor.GREEN + " Rank : " + i);
            ++i;

        }

    }

}
