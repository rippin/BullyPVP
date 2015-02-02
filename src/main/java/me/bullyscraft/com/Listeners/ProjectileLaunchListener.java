package me.bullyscraft.com.Listeners;

import me.bullyscraft.com.AbilityCountdowns.AbilityCountdown;
import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Stats.PlayerStatsObject;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

/**
 * Created by EF on 7/8/14.
 */
public class ProjectileLaunchListener implements Listener {
    private BullyPVP plugin;

    public ProjectileLaunchListener(BullyPVP plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent event){

        if (event.getEntity() instanceof Snowball || event.getEntity() instanceof Egg){
            if (event.getEntity().getShooter() instanceof Player){
            Player player = (Player) event.getEntity().getShooter();
               if (player.getInventory().contains(Material.SNOW_BALL,1) || player.getInventory().contains(Material.EGG,1)){

               }
                PlayerStatsObject pso = PlayerStatsObjectManager.getPSO(player, plugin);

                if (pso.getKitClass().equalsIgnoreCase("Freezer")){
                    new AbilityCountdown(90, plugin, player).startFreezerCountdown();
                }
                else if (pso.getKitClass().equalsIgnoreCase("Assassin")){
                    new AbilityCountdown(90, plugin, player).startAssassinCountdown();
                }
                else if (pso.getKitClass().equalsIgnoreCase("Pyro")){
                    new AbilityCountdown(90, plugin, player).startPyroCountdown();
                }
                else if (pso.getKitClass().equalsIgnoreCase("Gravity")){
                    new AbilityCountdown(90, plugin, player).startGravCountdown();
                }

            }
        }
    }
}
