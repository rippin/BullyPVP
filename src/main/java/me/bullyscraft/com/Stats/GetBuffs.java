package me.bullyscraft.com.Stats;

import me.bullyscraft.com.Classes.Buffs;
import me.bullyscraft.com.Classes.Kit;
import me.bullyscraft.com.Config;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;


public class GetBuffs {

    public static int getBuffs(Player player, Kit kit, String buff){
        String type = Config.getConfig().getString("Kits." + kit.getName() + ".Buffs." + buff + ".Type");
        if (type.equalsIgnoreCase("Weapon-Enchantment")) {
            int level = 0;
            if (player.getInventory().getItem(0).
                    containsEnchantment(Enchantment.getByName(Buffs.getOfficialEnchantmentName(buff.toUpperCase())))) {
                level = player.getInventory().getItem(0).
                        getEnchantmentLevel(Enchantment.getByName(Buffs.getOfficialEnchantmentName(buff.toUpperCase())));
                return level;
            }

            return level;
        }
        else if (type.equalsIgnoreCase("Armor-Enchantment")) {
            int level = 0;
            if (player.getInventory().getChestplate().
                    containsEnchantment(Enchantment.getByName(Buffs.getOfficialEnchantmentName(buff.toUpperCase())))) {
                level = player.getInventory().
                        getChestplate().getEnchantmentLevel(Enchantment.getByName(Buffs.getOfficialEnchantmentName(buff.toUpperCase())));
                return level;
            }
            return level;
        }
        return 0;
    }
    public static int getWeaponBuff(Player player, String buff){
        int level = 0;
        if (player.getInventory().getItem(0).
                containsEnchantment(Enchantment.getByName(Buffs.getOfficialEnchantmentName(buff.toUpperCase())))) {
            level = player.getInventory().getItem(0).
                    getEnchantmentLevel(Enchantment.getByName(Buffs.getOfficialEnchantmentName(buff.toUpperCase())));
            return level;
        }

        return level;
    }
    public static int getArmorBuff(Player player, String buff){
        int level = 0;
        if (player.getInventory().getChestplate().
                containsEnchantment(Enchantment.getByName(Buffs.getOfficialEnchantmentName(buff.toUpperCase())))) {
            level = player.getInventory().getChestplate().
                    getEnchantmentLevel(Enchantment.getByName(Buffs.getOfficialEnchantmentName(buff.toUpperCase())));
            return level;
        }

        return level;
    }
}
