package me.bullyscraft.com.Listeners;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Scoreboards.BullyScoreBoard;
import me.bullyscraft.com.Classes.Kit;
import me.bullyscraft.com.Classes.KitManager;
import me.bullyscraft.com.Classes.RefillSoup;
import me.bullyscraft.com.MethodLibs;
import me.bullyscraft.com.MySQL.SetupTables;
import me.bullyscraft.com.Classes.Wipe;
import me.bullyscraft.com.Scoreboards.BullyScoreboardManager;
import me.bullyscraft.com.Spawn;
import me.bullyscraft.com.Stats.PlayerStatsObject;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoginListener implements Listener {

	public BullyPVP plugin;

	public LoginListener(BullyPVP plugin){
	this.plugin = plugin;
	
	}
	
	

	@EventHandler
	public void onJoin(PlayerJoinEvent event){
	
	final Player player = event.getPlayer();
        PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(player, plugin);

        if (pso == null){
            Kit k = KitManager.getDefaultKit();

            SetupTables.insertIntoTable(plugin, player, k.getName());
            pso = SetupTables.cacheNewPlayer(player,k.getName(),plugin);
				plugin.logger.info("Creating MySQL entry for " + player.getName() + ".");

            Bukkit.broadcastMessage(MethodLibs.prefix + MethodLibs.newPlayerMessage(player));

            Wipe.wipe(player);
            k.giveKit(player);
            RefillSoup.soup(player);
          }
        else {
         if (pso.getUUID() == null){
            pso.setUUID(player.getUniqueId());
            plugin.logger.info("UUID set for " + player.getName());
         }
     }

        pso.setPlayer(player);
        pso.setUsername(player.getName()); //Set username every join in case of name change.

		BullyScoreBoard b = new BullyScoreBoard(player, pso);
		b.updateWithoutPrefixes();
        BullyScoreboardManager.addPlayerToAllScoreboards(player); //Update all scoreboards
		player.teleport(Spawn.getSpawnLoc());


	}

    //for lazyness sake
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
       String uuid = event.getPlayer().getUniqueId().toString();
        BullyScoreboardManager.removeBullyScoreboard(uuid); // remove scoreboards

        if (BullyScoreBoard.prefixUUIDList.contains(uuid)){
            BullyScoreBoard.prefixUUIDList.remove(uuid);
        }
    }

}
