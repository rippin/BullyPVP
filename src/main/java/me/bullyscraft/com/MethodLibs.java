package me.bullyscraft.com;


import me.bullyscraft.com.Classes.Kit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;


public class MethodLibs {
    public static final String prefix = (ChatColor.DARK_RED + "[" + ChatColor.GOLD + "BullyPVP" + ChatColor.DARK_RED + "]" + ChatColor.RESET);
    public static String msg = Config.getConfig().getString("New-Player-Message");
    public static int startcoins = Config.getConfig().getInt("New-Player-Coins");

public static int calculatePriceBuffEnchant(int level, Kit k, String buff ){

    LinkedHashMap<String, Integer> h = k.getBuffsandPrice();

    int p = h.get(buff);

        if (level == 0 ){
            return p;
        }
        else{
            return (p + (level * (level * 50)));
        }
    }

    public static String newPlayerMessage( Player player){
        String message = msg;
        message = message.replace("%player%", player.getName());
        return ChatColor.translateAlternateColorCodes('&', message);
    }


}
