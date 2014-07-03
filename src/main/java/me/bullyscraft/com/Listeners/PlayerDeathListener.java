package me.bullyscraft.com.Listeners;

import me.bullyscraft.com.*;
import me.bullyscraft.com.Classes.Kit;
import me.bullyscraft.com.Classes.KitManager;
import me.bullyscraft.com.Scoreboards.BullyScoreBoard;
import me.bullyscraft.com.Scoreboards.BullyScoreboardManager;
import me.bullyscraft.com.Stats.PlayerStatsObject;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import rippin.bullyscraft.com.ArenaManager;

import java.util.ArrayList;
import java.util.List;

public class PlayerDeathListener implements Listener {

	public BullyPVP plugin;

	public PlayerDeathListener(BullyPVP plugin) {
		this.plugin = plugin;

	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void playerDeathEvent(PlayerDeathEvent event) {

        if (event.getEntity() instanceof Player
				&& event.getEntity().getKiller() instanceof Player) {

			// Getting the killer and player that died from the event.

			Player dead = event.getEntity();
			Player killer = event.getEntity().getKiller();
            PlayerStatsObject psoDead = PlayerStatsObjectManager.getPSO(dead, plugin);
            PlayerStatsObject psoKiller = PlayerStatsObjectManager.getPSO(killer, plugin);
			/*

			Abilities Start


			 */
            Kit deadKit = KitManager.getKit(psoDead.getKitClass());
            Kit killerKit = KitManager.getKit(psoKiller.getKitClass());

            //Ability Tank

            if (killerKit.getAbility().equalsIgnoreCase("Tank")){
                killer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 80, 0));
            }
            // Ability Pro
            if (killerKit.getAbility().equalsIgnoreCase("Pro")){
                psoKiller.addCoins(5);
                killer.sendMessage(ChatColor.AQUA + "You have received an extra " + ChatColor.GREEN + " coins");
            }

            // Ability Exo
            if (killerKit.getAbility().equalsIgnoreCase("Exo")){
                killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 1));
                killer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 2));
                killer.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 80, 1));
            }




            /*

            Abilities End


             */




            //set stats for 1v1
            if (plugin.isBully1v1Enabled()){
                if (ArenaManager.isInArena(event.getEntity())){
                    event.setDeathMessage(null);
                   return;
                }
            }


			event.setDeathMessage(null);
            //getting kills and deaths;
			int deadDeaths = psoDead.getDeaths();
			int deadKills = psoDead.getKills();

			int killerKills = psoKiller.getKills();
			int killerDeaths = psoKiller.getDeaths();
			int streak = psoKiller.getCurrentstreak();
			int highStreak = psoKiller.getHigheststreak();

			// setting the kills and deaths
			psoDead.setDeaths(++deadDeaths);
            psoKiller.setKills(++killerKills);

			// Set Streak
			psoKiller.setCurrentstreak(++streak);
			psoDead.setCurrentstreak(0);
			
			//if played killed himself
			if (killer.getName().equalsIgnoreCase(dead.getName())){
				return;
			}
			
			if (streak > highStreak) {
				psoKiller.setHigheststreak(streak);
			}
			
			if (streak == 5){
			 String s  = ChatColor.AQUA + killer.getName() + ChatColor.RED +" is on a 5 kill streak!!!";
                for (Player p : Bukkit.getOnlinePlayers())
                BarScheduler.setBarMessage(p, s, 5, plugin);
			}
			
			if (streak == 10){
				String s = ChatColor.AQUA + killer.getName() + ChatColor.RED +" is on a 10 kill streak!!!";
                for (Player p : Bukkit.getOnlinePlayers())
                    BarScheduler.setBarMessage(p, s, 5, plugin);
			}
			
			if (streak == 15){
				String s = ChatColor.AQUA + killer.getName() + ChatColor.RED +" is on a 15 kill streak!!! eZZZZZZ";
                for (Player p : Bukkit.getOnlinePlayers())
                    BarScheduler.setBarMessage(p, s, 5, plugin);
			}
			
			if (streak == 20){
				String s = ChatColor.RED + "OH MY GOD! " + ChatColor.AQUA + killer.getName() + ChatColor.RED + " is on a 20 killstreak!";
                for (Player p : Bukkit.getOnlinePlayers())
                    BarScheduler.setBarMessage(p, s, 5, plugin);
            }
			
			if (streak == 25){
				String s = ChatColor.RED + "If this was COD " + ChatColor.AQUA + killer.getName() + ChatColor.RED +  " would be calling in a NUKE! 25 killstreak!";
                for (Player p : Bukkit.getOnlinePlayers())
                    BarScheduler.setBarMessage(p, s, 5, plugin);
            }
            if (streak == 35){
                String s = ChatColor.RED + "Oh my... " + ChatColor.AQUA + killer.getName() + ChatColor.RED +  " is on a 35 killstreak!";
                for (Player p : Bukkit.getOnlinePlayers())
                    BarScheduler.setBarMessage(p, s, 5, plugin);
            }

            if (streak == 50){
               String s = ChatColor.RED + "Sharkeisha nooo! " + ChatColor.AQUA + killer.getName() + ChatColor.RED +  " is on a 50 killstreak!";
                for (Player p : Bukkit.getOnlinePlayers())
                    BarScheduler.setBarMessage(p, s, 5, plugin);
            }
            if (streak == 100){
                String s = ChatColor.RED + "HOLY SHIT. " + ChatColor.AQUA + killer.getName() + ChatColor.RED +  " is on a 100 killstreak!";
                for (Player p : Bukkit.getOnlinePlayers())
                    BarScheduler.setBarMessage(p, s, 5, plugin);
            }
			
			// Set ASSIST AND GIVE COINS.
			//if player that died has his name in the hashmap
			if (plugin.damage.containsKey(dead.getName())) {
				double d = CoinAlgorithm.calculateWorth(psoDead.getKd());
				//get the assist handler that belongs to the dead player
				AssistHandler n = plugin.damage.get(dead.getName());
				//check for the killer
				if (killer != null) {
					psoKiller.addCoins((int) d);
					killer.sendMessage(ChatColor.GREEN + "You received "
							+ ChatColor.AQUA + (int) d + " coins" + ChatColor.GREEN
							+ " for killing " + ChatColor.AQUA + dead.getName());
				}

				//here is where everyone gets the assists
				CoinAlgorithm.splitAssist(n.getMap(), d, dead.getName(),
						killer.getName(), plugin);
				//clear the assist handler
				n.clear();
                BullyScoreBoard killerBoard = BullyScoreboardManager.getBullyScoreboard(killer.getUniqueId().toString());
                killerBoard.setPSO(psoKiller);
                killerBoard.updateWithoutPrefixes();
                BullyScoreBoard deadBoard = BullyScoreboardManager.getBullyScoreboard(dead.getUniqueId().toString());
                deadBoard.setPSO(psoDead);
                deadBoard.updateWithoutPrefixes();
			}

		}
	}

	@EventHandler
	public void playerDeathDrop(PlayerDeathEvent event) {

		if (event.getEntity() instanceof Player) {
			event.setDroppedExp(0);
			event.getDrops().clear();

		}

	}

	@EventHandler(priority = EventPriority.HIGH)
	public void playerDeathNoKiller(PlayerDeathEvent event) {

		if (event.getEntity() instanceof Player
				&& event.getEntity().getKiller() == null) {
			Player dead = event.getEntity();
            PlayerStatsObject psoDead = PlayerStatsObjectManager.getPSO(dead, plugin);
            if (plugin.isBully1v1Enabled()){
                if (ArenaManager.isInArena(dead)){
                    return;
                }
               }
            int deaths = psoDead.getDeaths();
			psoDead.setDeaths(++deaths);
            psoDead.setCurrentstreak(0);

            BullyScoreBoard deadBoard = BullyScoreboardManager.getBullyScoreboard(dead.getUniqueId().toString());
            deadBoard.setPSO(psoDead);
            deadBoard.updateWithoutPrefixes();

		}

	}
}
