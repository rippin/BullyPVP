package me.bullyscraft.com.Stats;

import me.bullyscraft.com.BullyPVP;
import org.bukkit.entity.Player;

public class PlayerStatsObjectManager {

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
}
