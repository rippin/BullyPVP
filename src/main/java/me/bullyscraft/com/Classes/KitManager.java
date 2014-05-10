package me.bullyscraft.com.Classes;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Config;
import me.bullyscraft.com.MethodLibs;
import me.bullyscraft.com.Stats.GetBuffs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class KitManager {

public static List<Kit> getAllKits = new ArrayList<Kit>();
private static Kit defaultKit;


public static void loadKits(BullyPVP plugin){
    for (String key : Config.getConfig().getConfigurationSection("Kits").getKeys(false)){
        Kit k = new Kit(key);

        if (k.getKitType().equalsIgnoreCase("Default")){
            defaultKit = k;
            System.out.println(" default kit is " + k.getName());
        }

        getAllKits.add(k);
        plugin.logger.info(k.getName() + " kit has been loaded from file");

    }
}
 public static void reloadKits(BullyPVP plugin){
     getAllKits.clear();
     for (String key : Config.getConfig().getConfigurationSection("Kits").getKeys(false)){
         Kit k = new Kit(key);

         if (k.getKitType().equalsIgnoreCase("Default")){
             defaultKit = k;
             System.out.println(" default kit is " + k.getName());
         }

         getAllKits.add(k);
         plugin.logger.info(k.getName() + " kit has been loaded from file");

     }
 }


public static Kit getKit(String name){
    Kit kit = null;

   for (Kit k : getAllKits()){
       if (k.getName().equalsIgnoreCase(name)){
           kit = k;
           break;
       }

   }
    return kit;
}
    public static Kit getDefaultKit(){
        return defaultKit;
    }



    public static List<Kit> getAllKits(){
        return getAllKits;
    }

    public static void getBuffMessages(Player player, Kit kit){
        String name = kit.getName();

        LinkedHashMap<String, Integer> bp = kit.getBuffsandPrice();
        Iterator itBP = bp.entrySet().iterator();
        LinkedHashMap<String, String> bd = kit.getBuffsandDesc();
        Iterator itBD = bd.entrySet().iterator();
        player.sendMessage(ChatColor.RED + "================== " + ChatColor.GOLD + "Powerups"
                + ChatColor.RED +" ==================");
        while (itBP.hasNext()){
            Map.Entry<String, Integer> entry = (Map.Entry) itBP.next();
            String buff = entry.getKey();
            int price = entry.getValue();
            Map.Entry<String, String> entry2 = (Map.Entry) itBD.next();
            String desc = entry2.getValue();
            String typeofbuff = Config.getConfig().getString("Kits." + name + ".Buffs." + buff + ".Type");
      if (typeofbuff.equalsIgnoreCase("Weapon-Enchantment") || typeofbuff.equalsIgnoreCase("Armor-Enchantment")){
            int bufflevel = GetBuffs.getBuffs(player, kit, buff);
            price = MethodLibs.calculatePriceBuffEnchant(bufflevel, kit, buff);

            if (Config.getConfig().getInt("Kits." + name + ".Buffs." + buff + ".Max") ==  GetBuffs.getBuffs(player, kit, buff)) {

                player.sendMessage(ChatColor.RED + "/" + buff + ChatColor.GOLD + " - " + ChatColor.GOLD + desc
                    + ": " + ChatColor.RED + "Max Level");
            } else{
                player.sendMessage(ChatColor.RED + "/" + buff + ChatColor.GOLD + " - " + ChatColor.GOLD + desc
                        + ": " + ChatColor.RED + price + ChatColor.GOLD + " coins.");
            }

            }
            else {
                player.sendMessage(ChatColor.RED + "/" + buff + ChatColor.GOLD + " - " + ChatColor.GOLD + desc
                        + ": " + ChatColor.RED + price + ChatColor.GOLD + " coins.");
            }

        }

    }
}
