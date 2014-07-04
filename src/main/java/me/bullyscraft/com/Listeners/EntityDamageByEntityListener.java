package me.bullyscraft.com.Listeners;

import me.bullyscraft.com.AbilityCountdowns.BleedCountdown;
import me.bullyscraft.com.AssistHandler;
import me.bullyscraft.com.BullyPVP;

import me.bullyscraft.com.Stats.PlayerStatsObject;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rippin.bullyscraft.com.ArenaManager;
import java.util.Random;

public class EntityDamageByEntityListener implements Listener {

	private BullyPVP plugin;


	public EntityDamageByEntityListener(BullyPVP plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("static-access")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDamage(EntityDamageByEntityEvent event) {
        AssistHandler assist;
		if (event.getEntity() instanceof Player
				&& event.getDamager() instanceof Player) {
			Player player = (Player) event.getEntity();
			Player damager = (Player) event.getDamager();
            if (generateRandom() <= 10){
                PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(damager, plugin);
                if (pso.getKitClass().equalsIgnoreCase("Freezer")){
                    new BleedCountdown(3, plugin, damager, player).startBleedingCountdown();
                }
            }


			if (plugin.isBully1v1Enabled()){
                if (ArenaManager.isInArena(player)){
                    return;
                }
            }

            double i = 0;
			//plugin.damage is the hashmap from the main class
			//check if the player damaged is in the hashmap
			if (!plugin.damage.containsKey(player.getName())) {
				//new assist handler;
                assist = new AssistHandler();
            }
            //player is in hashmap so retrieve assist handler and add to it.
            else {
                assist = plugin.damage.get(player.getName());
                if (assist.containsKey(
						damager.getName())) {
					//get the damage previously dealt
					i = assist.getValue(damager.getName());
				}
             }

			// insert into assist object update damage dealt or if first hit i = 0 so it wont matter.
            assist.add(damager.getName(), event.getDamage() + i);
			//insert into hashmap
			plugin.damage.put(player.getName(), assist);

		}
		//Check if damaged by arrow then get shooter and add the damage
		//same thing of previous code but with arrow you have to get shooter and stuff
		else if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow) {
			Player player = (Player) event.getEntity();
			
			Arrow a = (Arrow) event.getDamager();
			
			
			
			if (a.getShooter() instanceof Player){
				Player damager = (Player) a.getShooter();
                PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(damager, plugin);
                if (pso.getKitClass().equalsIgnoreCase("Medic")){
                    if (player.getHealth() != player.getMaxHealth()){
                    player.setHealth(player.getHealth() + 1.0);
                    player.sendMessage(ChatColor.AQUA + "Healed one heart by " + ChatColor.GREEN + damager.getName() + "'s ability.");
                    a.playEffect(EntityEffect.WOLF_HEARTS);
                    }
                 }
				double i = 0;
                //plugin.damage is the hashmap from the main class
                //check if the player damaged(player) is in the hashmap
                if (!plugin.damage.containsKey(player.getName())) {
                    //new assist handler;
                    assist = new AssistHandler();
                }
                //player is in hashmap so retrieve assist handler and add to it.
                else {
                    assist = plugin.damage.get(player.getName());
                    if (assist.containsKey(
                            damager.getName())) {
                        //get the damage previously dealt
                        i = assist.getValue(damager.getName());
                    }
                }
				assist.add(damager.getName(), event.getDamage() + i);
				plugin.damage.put(player.getName(), assist);
			}
		}
        //snowball abilities
        else if (event.getEntity() instanceof Player && event.getDamager()instanceof Snowball){
            Snowball s = (Snowball) event.getDamager();

            if (s.getShooter() instanceof  Player){
                Player shooter = (Player) s.getShooter();
                PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(shooter, plugin);
                if (pso.getKitClass().equalsIgnoreCase("Freezer")){
                    ((Player) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 0));
                }
                else if (pso.getKitClass().equalsIgnoreCase("Assassin")){
                 Player damaged = (Player) event.getEntity();
                    Location loc = damaged.getLocation();
                   loc.add(loc.getX(), (loc.getY() + 1), loc.getZ());
                   if (loc.getBlock().getType() == Material.AIR){
                       final Material m = loc.getBlock().getType();
                       final Block b = loc.getBlock();
                       loc.getBlock().setType(Material.WEB);
                       plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                           @Override
                           public void run() {
                               b.setType(m);
                           }
                       }, 100L);
                   }
                }

            }
        }
        else if (event.getEntity() instanceof Player && event.getDamager() instanceof Egg){
            Egg e = (Egg) event.getDamager();

            if (e.getShooter() instanceof  Player){
                Player shooter = (Player) e.getShooter();
                PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(shooter, plugin);
                if (pso.getKitClass().equalsIgnoreCase("Pyro")){
                  event.getEntity().setFireTicks(80);

                }

            }
        }
	}



    public int generateRandom(){
        int min = 1;
        int max = 100;
        Random r = new Random();

        return r.nextInt(max - min + 1) + min;

    }




}
