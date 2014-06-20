package me.bullyscraft.com.Listeners;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Classes.Kit;
import me.bullyscraft.com.Classes.KitManager;
import me.bullyscraft.com.Classes.RefillSoup;
import me.bullyscraft.com.Spawn;

import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import rippin.bullyscraft.com.ArenaManager;

public class PlayerRespawnListener implements Listener {

	public BullyPVP plugin;

	public PlayerRespawnListener(BullyPVP plugin) {
		this.plugin = plugin;

	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
        if (plugin.isBully1v1Enabled()){
        if (ArenaManager.wasInArena(player)){
            ArenaManager.getWasInArena().remove(player.getUniqueId().toString());
            return;
          }
        }
		event.setRespawnLocation(Spawn.getSpawnLoc());

		player.teleport(Spawn.getSpawnLoc());
		
		String s = PlayerStatsObjectManager.getPSO(player, plugin).getKitClass();
        Kit k = KitManager.getKit(s);
           if (k != null){
               k.giveKit(player);
               return;
           }
        else{
             Kit def = KitManager.getDefaultKit();
                def.giveKit(player);

           }

    }

}
