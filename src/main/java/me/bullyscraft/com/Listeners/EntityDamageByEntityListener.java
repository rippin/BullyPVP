package me.bullyscraft.com.Listeners;

import me.bullyscraft.com.AssistHandler;
import me.bullyscraft.com.BullyPVP;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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
	}
 }
