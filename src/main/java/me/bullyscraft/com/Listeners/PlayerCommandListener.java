package me.bullyscraft.com.Listeners;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.bullyscraft.com.BullyPVP;

import me.bullyscraft.com.MethodLibs;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandListener implements Listener {

private BullyPVP plugin;



	public PlayerCommandListener(BullyPVP plugin){
	
		this.plugin = plugin;
	}
	
	@EventHandler
	public void playerCommandEvent(PlayerCommandPreprocessEvent event){
	
		Player player = event.getPlayer();
        if (event.getMessage().contains("/kit")) {
        if (event.getMessage().contains(" ")) {
            String split[] = event.getMessage().split("\\s+");
            if (split[0].equalsIgnoreCase("/kit")){
                if (!checkiFPlayerInWGRegion(player.getLocation())){
                    player.sendMessage(ChatColor.RED + "You may only use /kit in the spawn.");
                    event.setCancelled(true);
                    return;
                }
            }
        }
        else {
            if (event.getMessage().equalsIgnoreCase("/kit") || event.getMessage().equalsIgnoreCase("/kitgui")){
                if (!checkiFPlayerInWGRegion(player.getLocation())){
                    player.sendMessage(ChatColor.RED + "You may only use /kit in the spawn.");
                    event.setCancelled(true);
                    return;
                }
            }
        }
      }
}



    public boolean checkiFPlayerInWGRegion(Location loc){
        if (plugin.getWorldGuard() == null){
            return false;
        }
        ApplicableRegionSet ars = plugin.getWorldGuard().
                getRegionManager(loc.getWorld()).getApplicableRegions(loc);
        for (ProtectedRegion pr : ars){
            if (pr.getId().contains("spawn")){
                return true;
            }
        }

        return false;
    }


}
