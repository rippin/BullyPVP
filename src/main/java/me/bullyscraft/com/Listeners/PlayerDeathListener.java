package me.bullyscraft.com.Listeners;

import me.bullyscraft.com.*;
import me.bullyscraft.com.Stats.PlayerStatsObject;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

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

			// set k/d plus solve division by zero
			/*
            if (deaths == 0) {
				PlayerStats.setKD(dead, (double) deathsKills);
			} else {
				PlayerStats.setKD(dead, (deathsKills * 1.0) / deaths);
			}
			if (killsDeath == 0) {
				PlayerStats.setKD(killer, (double) kills);
			} else {
				PlayerStats.setKD(killer, (kills * 1.0) / killsDeath);
			}
            */
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
                BullyScoreBoard b = new BullyScoreBoard(killer, psoKiller);
                b.setUp();
                BullyScoreBoard b1 = new BullyScoreBoard(dead, psoDead);
                b1.setUp();
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

	@EventHandler
	public void playerDeathNoKiller(PlayerDeathEvent event) {

		if (event.getEntity() instanceof Player
				&& event.getEntity().getKiller() == null) {
			Player dead = event.getEntity();
            PlayerStatsObject psoDead = PlayerStatsObjectManager.getPSO(dead, plugin);
			int deaths = psoDead.getDeaths();
			psoDead.setDeaths(++deaths);
            psoDead.setCurrentstreak(0);

            BullyScoreBoard b1 = new BullyScoreBoard(dead, psoDead);
            b1.setUp();
		}

	}

}
