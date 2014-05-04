package me.bullyscraft.com;


import me.confuser.barapi.BarAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BarScheduler {

public static void setBarMessage(final Player player, String message, int duration, BullyPVP plugin){
    message = translateColor(message);
    BarAPI.setMessage(player, message, (float) 100.0);
     plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
         @Override
         public void run() {
             BarAPI.removeBar(player);
         }
     },duration * 20);


}

    public static String translateColor(String s){

        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
