package me.bullyscraft.com.AbilityCountdowns;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BleedCountdownManager {
    public static HashMap<BleedCountdown, String>  bleedCountdown = new HashMap<BleedCountdown, String>();

    public static void removeAbilityCountdown(Player player){

        Iterator it = bleedCountdown.entrySet().iterator();

        while (it.hasNext()){
            Map.Entry<BleedCountdown, String> entry = (Map.Entry <BleedCountdown, String>) it.next();
            if (entry.getValue().equalsIgnoreCase(player.getUniqueId().toString())){
                it.remove();
            }
        }

    }

    public static void hasCountdownAndCancel(Player player){
        Iterator it = bleedCountdown.entrySet().iterator();

        while (it.hasNext()){
            Map.Entry<BleedCountdown, String> entry = (Map.Entry <BleedCountdown, String>) it.next();
            if (entry.getValue().equalsIgnoreCase(player.getUniqueId().toString())){
                entry.getKey().cancelTask();
            }
        }
    }
}
