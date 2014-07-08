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
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
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
                    if (player.getHealth() + event.getDamage() < player.getMaxHealth()) {
                    player.setHealth(player.getHealth() + event.getDamage());
                    }
                      else {
                            player.setHealth(player.getMaxHealth());
                        }
                        Location l = player.getLocation();
                        l.setY(400);
                    player.sendMessage(ChatColor.AQUA + "Healed " + event.getDamage() + " by " + ChatColor.GREEN + damager.getName() + "'s ability.");
                    final Wolf w = (Wolf) a.getWorld().spawnEntity(l, EntityType.WOLF);
                    w.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 0));
                    w.teleport(player.getLocation());
                        plugin.getServer().getScheduler().runTaskLater(plugin, new BukkitRunnable() {
                            @Override
                            public void run() {
                                w.playEffect(EntityEffect.WOLF_HEARTS);
                                w.remove();
                            }
                        }, 3L);
                    }
                    event.setDamage(0);
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
                    ((Player) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 0));
                }
                else if (pso.getKitClass().equalsIgnoreCase("Assassin")){
                 Player damaged = (Player) event.getEntity();
                   final Location loc = damaged.getLocation();
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
