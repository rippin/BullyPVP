package me.bullyscraft.com.Classes;

import me.bullyscraft.com.BullyPVP;

import me.bullyscraft.com.Stats.PlayerStatsObject;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

public class Wipe {

	

	public static void wipe(Player player){
		
		PlayerInventory inv = player.getInventory();
        inv.clear();
		inv.setArmorContents(null);
		player.setExp(0);
		player.setLevel(0);
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		player.setRemainingAir(player.getMaximumAir());
		player.setFireTicks(0);

		for(PotionEffect pe : player.getActivePotionEffects()){
			player.removePotionEffect(pe.getType());
		}
	}

	public static void removeKitItems(Player player){
		PlayerInventory inv = player.getInventory();
        PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(player, BullyPVP.instance);
		String s = pso.getKitClass();
	    Kit k = KitManager.getKit(s);
        for(PotionEffect pe : player.getActivePotionEffects()){
            player.removePotionEffect(pe.getType());
        }
        if (inv.contains(Material.getMaterial(k.getWeapon()))){
            inv.remove(Material.getMaterial(k.getWeapon()));
        }
        if (inv.getArmorContents() != null){
            inv.setArmorContents(null);
        }
        if (k.getItems() != null) {

         for (String items : k.getItems()){
           if (items.contains(":")){
               String[] split = items.split(":");
               items = split[0];
              if (inv.contains(Material.getMaterial(items.toUpperCase()))){
                if (Material.MUSHROOM_SOUP != Material.getMaterial(items)){
                    inv.remove(Material.getMaterial(items));
                }
              }
           }
        }
       }


     }






}
