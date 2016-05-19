package me.bullyscraft.com;



import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.inventivetalent.bossbar.BossBarAPI;

public class BarScheduler {

public static void setBarMessage(final Player player, String message, int duration, BullyPVP plugin){
    message = translateColor(message);
    BossBarAPI.addBar(player, new TextComponent(message), BossBarAPI.Color.BLUE,
            BossBarAPI.Style.NOTCHED_20, 1.0F, 20, (long)duration);



}

    public static String translateColor(String s){

        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
