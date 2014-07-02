package me.bullyscraft.com.Classes;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Config;
import me.bullyscraft.com.MethodLibs;
import me.bullyscraft.com.Stats.GetBuffs;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

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
    //Example LEATHER_HELMET ENCHANT@LEVEL LEATHERCOLO
    public static List<ItemStack> getArmor(String kitName){

        List<String> items = Config.getConfig().getStringList("Kits." + kitName + ".ArmorList");
        if (items == null){
            return null;
        }
        List<ItemStack> armor = new ArrayList<ItemStack>();
        for (String i : items){
            String splitspace[] = i.split("\\s+");
            ItemStack item = new ItemStack(Material.getMaterial(splitspace[0]));
           for (int j = 1; j < splitspace.length; j++){
               if (splitspace[j].contains("@")){
                   String split[] = splitspace[j].split("@");
                   if (split[0] != null && split[1] != null){
                       item.addUnsafeEnchantment(Enchantment.getByName(Buffs.getOfficialEnchantmentName(split[0])), Integer.parseInt(split[1]));
                   }
               }
                   else {
                       LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                       meta.setColor(getFromString(splitspace[j]));
                       item.setItemMeta(meta);
                   }
               }
            armor.add(item);
        }
        return armor;
    }

    public static Color getFromString(String string){

        if (string.equalsIgnoreCase("black")){
            return Color.BLACK;
        }
        else if (string.equalsIgnoreCase("blue")){
            return Color.BLUE;
        }

        else if (string.equalsIgnoreCase("green")){
            return Color.GREEN;
        }
        else if (string.equalsIgnoreCase("silver")){
            return Color.SILVER;
        }
        else if (string.equalsIgnoreCase("gray")){
            return Color.GRAY;
        }
        else if (string.equalsIgnoreCase("yellow")){
            return Color.YELLOW;
        }
        else if (string.equalsIgnoreCase("aqua")){
            return Color.AQUA;
        }
        else if (string.equalsIgnoreCase("fuchsia")){
            return Color.FUCHSIA;
        }
        else if (string.equalsIgnoreCase("purple")){
            return Color.PURPLE;
        }
        else if (string.equalsIgnoreCase("teal")){
            return Color.TEAL;
        }
        else if (string.equalsIgnoreCase("lime")){
            return Color.LIME;
        }
        else if (string.equalsIgnoreCase("olive")){
            return Color.OLIVE;
        }
        else if (string.equalsIgnoreCase("white")){
            return Color.WHITE;
        }
        else{
            return Color.MAROON;
        }

    }
}
