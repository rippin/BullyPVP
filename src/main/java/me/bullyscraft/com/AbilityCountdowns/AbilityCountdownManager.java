package me.bullyscraft.com.AbilityCountdowns;


import me.bullyscraft.com.BullyPVP;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AbilityCountdownManager {
    public static  HashMap<AbilityCountdown, String> abilityCountdown = new HashMap<AbilityCountdown, String>();

    public static void removeAbilityCountdown(Player player){

    Iterator it = abilityCountdown.entrySet().iterator();

    while (it.hasNext()){
        Map.Entry<AbilityCountdown, String> entry = (Map.Entry <AbilityCountdown, String>) it.next();
        if (entry.getValue().equalsIgnoreCase(player.getUniqueId().toString())){
            it.remove();
        }
    }

}


}
