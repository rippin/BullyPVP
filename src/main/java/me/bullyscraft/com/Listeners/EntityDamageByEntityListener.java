package me.bullyscraft.com.Listeners;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.bullyscraft.com.AbilityCountdowns.BleedCountdown;
import me.bullyscraft.com.AssistHandler;
import me.bullyscraft.com.BullyPVP;

import me.bullyscraft.com.Particles.ParticlesHandler;
import me.bullyscraft.com.Stats.PlayerStatsObject;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;

import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;

import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
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
	@EventHandler
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
		else if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow && !checkiFPlayerInWGRegion(event.getEntity().getLocation())) {
			Player player = (Player) event.getEntity();
			
			Arrow a = (Arrow) event.getDamager();
			Location arrowLoc = a.getLocation();
			if (a.getShooter() instanceof Player){
				Player damager = (Player) a.getShooter();
                PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(damager, plugin);
                if (pso.getKitClass().equalsIgnoreCase("Medic")){
                    if (player.getHealth() != player.getMaxHealth()){
                    double heal = event.getDamage()/3;
                        if (player.getHealth() + heal < player.getMaxHealth()) {
                    player.setHealth(player.getHealth() + heal);
                    }
                      else {
                            player.setHealth(player.getMaxHealth());
                        }
                        Location l = player.getLocation();
                        l.setY(400);
                    player.sendMessage(ChatColor.AQUA + "Healed " + String.format( "%.2f", heal) + " by " + ChatColor.GREEN + damager.getName() + "'s ability.");
                        try {
                            ParticlesHandler.sendParticles(arrowLoc, "heart", plugin);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    event.setDamage(0.0);
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
            if (!checkiFPlayerInWGRegion(event.getEntity().getLocation())) {
                Player shooter = (Player) s.getShooter();
                PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(shooter, plugin);
                if (pso.getKitClass().equalsIgnoreCase("Freezer")){
                    ((Player) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 1));
                }
                else if (pso.getKitClass().equalsIgnoreCase("Gravity")){
                    ((Player) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 150, 128));
                }
                else if (pso.getKitClass().equalsIgnoreCase("Assassin")){
                 final Player damaged = (Player) event.getEntity();
                   if (damaged.hasMetadata("assassin")){
                       return;
                   }
                       final Location loc = damaged.getLocation();
                       final Material m = loc.getBlock().getType();
                        damaged.setMetadata("assassin", new FixedMetadataValue(plugin, ""));
                       loc.getBlock().setType(Material.WEB);
                       plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                           @Override
                           public void run() {
                               loc.getBlock().setType(m);
                               damaged.removeMetadata("assassin", plugin);
                           }
                       }, 100L);
                }
               }
            }
        }
        else if (event.getEntity() instanceof Player && event.getDamager() instanceof Egg){
            Egg e = (Egg) event.getDamager();

            if (e.getShooter() instanceof  Player){
                if (!checkiFPlayerInWGRegion(event.getEntity().getLocation())) {
                Player shooter = (Player) e.getShooter();
                PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(shooter, plugin);
                if (pso.getKitClass().equalsIgnoreCase("Pyro")){
                  event.getEntity().setFireTicks(80);

                }
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

    public boolean checkiFPlayerInWGRegion(Location loc){
        if (plugin.getWorldGuard() == null){
            return false;
        }
        ApplicableRegionSet ars = plugin.getWorldGuard().getRegionManager(loc.getWorld()).getApplicableRegions(loc);
        for (ProtectedRegion pr : ars){
            if (pr.getId().contains("spawn")){
                return true;
            }
        }

        return false;
    }


}
