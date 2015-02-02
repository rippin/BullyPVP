package me.bullyscraft.com.Stats;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Events.CacheRankingEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerStatsObjectManager {
    private static Map<PlayerStatsObject, Integer> rank = new HashMap<PlayerStatsObject, Integer>();
    public static Map<PlayerStatsObject, Double> sorted = new LinkedHashMap<PlayerStatsObject, Double>();

    public static PlayerStatsObject getPSO(Player player, BullyPVP plugin) {
        for (PlayerStatsObject pso : plugin.playerStats){
            if (pso.getUsername().equalsIgnoreCase(player.getName())){
                return pso;
            }
            else if (pso.getUUID() != null){
                if (pso.getUUID().equals(player.getUniqueId())){
                    return pso;
                }

            }
        }
        return null;
    }

    public static PlayerStatsObject getPSO(String name, BullyPVP plugin) {
        for (PlayerStatsObject pso : plugin.playerStats){
            if (pso.getUsername().equalsIgnoreCase(name)){
                return pso;
            }
        }
        return null;
    }

    public static Map<PlayerStatsObject, Integer> getRankList(){
        return rank;
    }

    public static void cacheRanking(final long ticks){
        sorted.clear();
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(BullyPVP.instance, new Runnable() {
            Map<PlayerStatsObject, Double> ratings = new ConcurrentHashMap<PlayerStatsObject, Double>();
            @Override
            public void run() {
                   for (PlayerStatsObject pso : getAllPlayerStatsObject()){
                       ratings.put(pso, getRating(pso));
                   }

                  List<Map.Entry<PlayerStatsObject, Double>> sort = new LinkedList<Map.Entry<PlayerStatsObject, Double>>(ratings.entrySet());
                Collections.sort(sort, new Comparator<Map.Entry<PlayerStatsObject, Double>>() {
                    public int compare(Map.Entry<PlayerStatsObject, Double> o1,
                                       Map.Entry<PlayerStatsObject, Double> o2) {
                        return (o1.getValue()).compareTo(o2.getValue());
                    }
                });
                Iterator it = sort.iterator();

                int size = sort.size();
                while (it.hasNext()){
                Map.Entry<PlayerStatsObject, Double> entry = (Map.Entry<PlayerStatsObject, Double>) it.next();
                    entry.getKey().setRanking(Integer.toString(size));
                   sorted.put(entry.getKey(), entry.getValue());
                    --size;

                }
                getAllPlayerStatsObject().clear();
                getAllPlayerStatsObject().addAll(sorted.keySet());
               // CacheRankingEvent event = new CacheRankingEvent(sorted);
                ViewStats.top10RankCache(); //cache top 10
            }
        },0L, ticks);

    }

    private static Double getRating(PlayerStatsObject pso){
      double rating =  pso.getKd() *5 + (pso.getKills() - pso.getDeaths());
        if (pso.getKills() + pso.getKd() + pso.getDeaths() == 0){
            return 0.0;
        }
        if (BullyPVP.instance.isBully1v1Enabled()){
            int losses;
            int wins;
            if (pso.getLosses1v1() != 0){
               losses = pso.getLosses1v1();
            }
            else {
                losses = 1;
            }
            if (pso.getWins1v1() != 0){
                wins = pso.getWins1v1();
            }
            else {
                wins = 1;
            }
            return (rating * (wins/losses));
        }
        return rating;
    }

    public static List<PlayerStatsObject> getAllPlayerStatsObject(){
        return BullyPVP.instance.playerStats;
    }
}
