package me.bullyscraft.com.Scoreboards;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.PrefixConfig;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class BullyScoreboardManager {
    public static List<BullyScoreBoard> scoreBoards = new ArrayList<BullyScoreBoard>();
    public static HashMap<String, String> prefixPerms = new HashMap<String, String>(); // Key Perm Value Prefix


    public static BullyScoreBoard getBullyScoreboard(String uuid){
    for (BullyScoreBoard b : scoreBoards){
        if (b.getUUID().equalsIgnoreCase(uuid)){
            return b;
        }
    }
        Player p = Bukkit.getPlayer(UUID.fromString(uuid));
        BullyScoreBoard b = new BullyScoreBoard(p, PlayerStatsObjectManager.getPSO(p, BullyPVP.instance));
        return b;
    }

    public static void removeBullyScoreboard(String uuid){
        for (BullyScoreBoard b : scoreBoards){
            if (b.getUUID().equalsIgnoreCase(uuid)){
                scoreBoards.remove(b);
            }
        }
    }

    public static void loadPrefixList(){
       List<String> prefixes = PrefixConfig.getConfig().getStringList("Prefixes");
        prefixPerms.clear();
        for (String key : prefixes){
            if (key.contains(":")) {
            String split[] = key.split(":");
            prefixPerms.put(split[0], split[1]);
            }
            else {
                continue;
            }
         }

    }


    public static List<BullyScoreBoard> getAllBullyScoreboards(){
        return scoreBoards;
    }

    public static void UpdateScoreboards(){
        for (BullyScoreBoard b : scoreBoards){
            b.update();

        }
    }

    public static void addPlayerToAllScoreboards(Player player){
        for (BullyScoreBoard b : scoreBoards){
            b.addPlayerToScoreboard(player);
        }
    }

}
