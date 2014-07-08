package me.bullyscraft.com.AbilityCountdowns;

import me.bullyscraft.com.BullyPVP;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EF on 7/3/14.
 */
public class BleedCountdown {
    private int delay;
    private BullyPVP plugin;
    private int taskid;
    private Player damager;
    private Player damagee;
    private static List<String> players = new ArrayList<String>();

    public BleedCountdown(int delay, BullyPVP plugin, Player damager, Player damagee){
        this.delay = delay;
        this.plugin = plugin;
        this.damager = damager;
        this.damagee = damagee;

    }

    public void startBleedingCountdown(){
        final int realDelay = delay;
        if (players.contains(damager.getUniqueId().toString())){
            return;
        }
        taskid =  plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {

                if (damager == null || !damager.isOnline() || damagee == null || !damagee.isOnline()){
                    cancelTask(taskid);
                }
                else {
                    players.add(damager.getUniqueId().toString());
                    EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(damager, damagee, EntityDamageEvent.DamageCause.CUSTOM, 2.0);
                    damagee.getWorld().playEffect(damagee.getLocation(), Effect.STEP_SOUND, Material.ICE);
                    damagee.playEffect(EntityEffect.HURT);
                    damagee.sendMessage(ChatColor.GREEN + "You are being hurt by "
                            + ChatColor.AQUA + damager.getName() + ChatColor.GREEN + "'s Freezer ability.");
                    damager.sendMessage(ChatColor.GREEN + "You are hurting "
                            + ChatColor.AQUA + damagee.getName() + ChatColor.GREEN + " with your Freezer ability.");

                }
                if (delay <= 0){
                    cancelTask(taskid);
                }
                --delay;
            }
        }, 0L, 60L);
    }

    public void cancelTask(int taskid){
        plugin.getServer().getScheduler().cancelTask(taskid);
        players.remove(damager.getUniqueId().toString());
    }


}
