package me.bullyscraft.com.Classes;


import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Config;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Buffs {


    public static void giveBuff(final Player player, Kit k, String buff) {

        String type = Config.getConfig().getString("Kits." + k.getName() + ".Buffs." + buff + ".Type");

        if (type.equalsIgnoreCase("Potion")) {
            player.sendMessage(ChatColor.GREEN + buff + " buff added.");

            int length = Config.getConfig().getInt("Kits." + k.getName() + ".Buffs." + buff + ".Length");
            int power = Config.getConfig().getInt("Kits." + k.getName() + ".Buffs." + buff + ".Power");
             buff = Buffs.getOfficialPotionName(buff);
            player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(buff.toUpperCase()), length, power), true);

            return;
        } else if (type.equalsIgnoreCase("Weapon-Enchantment")) {
            int level = 0;
            player.sendMessage(ChatColor.GREEN + buff + " buff added.");
            buff = Buffs.getOfficialEnchantmentName(buff);
            if (player.getInventory().getItem(0).containsEnchantment(Enchantment.getByName(buff.toUpperCase()))) {
                level = player.getInventory().getItem(0).getEnchantmentLevel(Enchantment.getByName(buff.toUpperCase()));
            }
            player.getInventory().getItem(0)
                    .addUnsafeEnchantment(Enchantment.getByName(buff.toUpperCase()), ++level);


            return;
        } else if (type.equalsIgnoreCase("Armor-Enchantment")) {
            int level = 0;

            if (player.getInventory().getChestplate().containsEnchantment(Enchantment.getByName(getOfficialEnchantmentName(buff.toUpperCase())))) {
                player.sendMessage(ChatColor.GREEN + buff + " buff added.");
                buff = Buffs.getOfficialEnchantmentName(buff);
                level = player.getInventory().getChestplate().getEnchantmentLevel(Enchantment.getByName(buff.toUpperCase()));
                ItemStack[] armor = player.getInventory().getArmorContents();
                ++level;
                for (ItemStack i : armor) {
                    i.addUnsafeEnchantment(Enchantment.getByName(buff.toUpperCase()), level);
                }

                return;
            } else {
                player.sendMessage(ChatColor.GREEN + buff + " buff added.");
                ItemStack[] armor = player.getInventory().getArmorContents();
                buff = Buffs.getOfficialEnchantmentName(buff);
                ++level;
                for (ItemStack i : armor) {
                    i.addUnsafeEnchantment(Enchantment.getByName(buff.toUpperCase()), level);
                }

                return;
            }
        } else if (type.equalsIgnoreCase("Item")) {
            if (buff.equalsIgnoreCase("refill")) {
                final String finalbuff = buff;
                final Kit finalKit = k;
                final String materialItem = Config.getConfig().getString("Kits." + finalKit.getName() + ".Buffs." + finalbuff + ".Material");
                BullyPVP plugin = BullyPVP.instance;
                player.sendMessage(ChatColor.RED + "Please wait 8 seconds for your items to be refilled.");
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < Config.getConfig().getInt("Kits." + finalKit.getName() + ".Buffs." + finalbuff + ".Amount"); i++) {
                            if (player.getInventory().contains(Material.getMaterial(materialItem), 33)){
                                break;
                            }
                            player.getInventory().addItem(new ItemStack(Material.getMaterial(materialItem)));
                        }
                        player.sendMessage(ChatColor.GREEN + "Soup refilled.");
                    }
                },160L);

                return;
            }
            player.getInventory().addItem(new ItemStack(Material.getMaterial(buff.toUpperCase()),
                    Config.getConfig().getInt("Kits." + k.getName() + ".Buffs." + buff + ".Amount")));
            player.sendMessage(ChatColor.GREEN + buff + " buff added.");
            return;
        } else if (type.equalsIgnoreCase("Other")) {
            if (buff.equalsIgnoreCase("repair")) {
            for (ItemStack item : player.getInventory().getArmorContents()) {
                item.setDurability((short) 0);
            }
            ItemStack i = player.getInventory().getItem(0);
            i.setDurability((short) 0);
            player.sendMessage(ChatColor.GREEN + "Items repaired!");
            }
        }
    }

    public static String getOfficialEnchantmentName(String buff) {


        if (buff.equalsIgnoreCase("POWER")) {
            buff = "ARROW_DAMAGE";
            return buff;
        } else if (buff.equalsIgnoreCase("FLAME")) {
            buff = "ARROW_FIRE";
        } else if (buff.equalsIgnoreCase("PUNCH")) {
            buff = "ARROW_KNOCKBACK";
        } else if (buff.equalsIgnoreCase("INFINITY")) {
            buff = "ARROW_INFINITE";
        } else if (buff.equalsIgnoreCase("SHARPNESS")) {
            buff = "DAMAGE_ALL";
        } else if (buff.equalsIgnoreCase("BANEOFARTHROPODS")) {
            buff = "DAMAGE_ARTHROPODS";
        } else if (buff.equalsIgnoreCase("SMITE")) {
            buff = "DAMAGE_UNDEAD";
        } else if (buff.equalsIgnoreCase("EFFICIENCY")) {
            buff = "DIG_SPEED";
        } else if (buff.equalsIgnoreCase("UNBREAKING")) {
            buff = "DURABILITY";
        } else if (buff.equalsIgnoreCase("FIREASPECT")) {
            buff = "FIRE_ASPECT";
        } else if (buff.equalsIgnoreCase("KNOCKBACK")) {
            buff = "KNOCKBACK";
        } else if (buff.equalsIgnoreCase("FORTUNE")) {
            buff = "LOOT_BONUS_BLOCKS";
        } else if (buff.equalsIgnoreCase("LOOTING")) {
            buff = "LOOT_BONUS_MOBS";
        } else if (buff.equalsIgnoreCase("RESPIRATION")) {
            buff = "OXYGEN";
        } else if (buff.equalsIgnoreCase("PROTECTION")) {
            buff = "PROTECTION_ENVIRONMENTAL";
        } else if (buff.equalsIgnoreCase("BLASTPROTECTION")) {
            buff = "PROTECTION_EXPLOSIONS";
        } else if (buff.equalsIgnoreCase("FALLPROTECTION")) {
            buff = "PROTECTION_FALL";
        } else if (buff.equalsIgnoreCase("FIREPROTECTION")) {
            buff = "PROTECTION_FIRE";
        } else if (buff.equalsIgnoreCase("PROJECTILEPROTECTION")) {
            buff = "PROTECTION_PROJECTILE";
        } else if (buff.equalsIgnoreCase("SILKTOUCH")) {
            buff = "SILK_TOUCH";
        } else if (buff.equalsIgnoreCase("THORNS")) {
            buff = "THORNS";
        } else if (buff.equalsIgnoreCase("AQUAAFFINITY")) {
            buff = "WATER_WORKER";
        }
        return buff;

    }

    public static String getOfficialPotionName(String buff) {


        if (buff.equalsIgnoreCase("ABSORPTION")) {
            buff = "ABSORPTION";
            return buff;
        } else if (buff.equalsIgnoreCase("BLINDNESS")) {
            buff = "BLINDNESS";
        } else if (buff.equalsIgnoreCase("NAUSEA")) {
            buff = "CONFUSION";
        } else if (buff.equalsIgnoreCase("RESISTANCE")) {
            buff = "DAMAGE_RESISTANCE";
        } else if (buff.equalsIgnoreCase("HASTE")) {
            buff = "FAST_DIGGING";
        } else if (buff.equalsIgnoreCase("FIRERESISTANCE")) {
            buff = "FIRE_RESISTANCE";
        } else if (buff.equalsIgnoreCase("INSTANTDAMAGE")) {
            buff = "HARM";
        } else if (buff.equalsIgnoreCase("INSTANTHEALTH")) {
            buff = "HEAL";
        } else if (buff.equalsIgnoreCase("HEALTHBOOST")) {
            buff = "HEALTH_BOOST";
        } else if (buff.equalsIgnoreCase("HUNGER")) {
            buff = "HUNGER";
        } else if (buff.equalsIgnoreCase("STRENGTH")) {
            buff = "INCREASE_DAMAGE";
        } else if (buff.equalsIgnoreCase("INVISIBILITY")) {
            buff = "INVISIBILITY";
        } else if (buff.equalsIgnoreCase("JUMPBOOST")) {
            buff = "JUMP";
        } else if (buff.equalsIgnoreCase("NIGHT_VISION")) {
            buff = "NIGHT_VISION";
        } else if (buff.equalsIgnoreCase("POISON")) {
            buff = "POISON";
        } else if (buff.equalsIgnoreCase("REGENERATION")) {
            buff = "REGENERATION";
        } else if (buff.equalsIgnoreCase("SATURATION")) {
            buff = "SATURATION";
        } else if (buff.equalsIgnoreCase("SLOWNESS")) {
            buff = "SLOW";
        } else if (buff.equalsIgnoreCase("SLOW_DIGGING")) {
            buff = "MINING_FATIGUE";
        } else if (buff.equalsIgnoreCase("SPEED")) {
            buff = "SPEED";
        } else if (buff.equalsIgnoreCase("WATERBREATHING")) {
            buff = "WATER_BREATHING";
        } else if (buff.equalsIgnoreCase("WEAKNESS")) {
            buff = "WEAKNESS";
        } else if (buff.equalsIgnoreCase("WITHER")) {
            buff = "WITHER";
        }
        return buff;

    }

}
