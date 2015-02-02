package me.bullyscraft.com.AbilityCountdowns;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class AbilityCountdown {
    private int delay;
    private BullyPVP plugin;
    private int taskid;
    private Player player;
    private AbilityCountdown aC;

    public AbilityCountdown(int delay, BullyPVP plugin, Player player){
        this.delay = delay;
        this.plugin = plugin;
        this.player = player;
        aC = this;
    }

    public void startAssassinCountdown(){
        if (AbilityCountdownManager.abilityCountdown.containsValue(player.getUniqueId().toString())){
            return;
        }
        taskid =  plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new BukkitRunnable() {
            @Override
            public void run() {
                if (delay <= 0) {
                    if (player == null || !player.isOnline() || (!PlayerStatsObjectManager.getPSO(player, plugin).getKitClass().equalsIgnoreCase("Assassin"))){
                        cancelTask(taskid);
                    }
                    else {
                        AbilityCountdownManager.abilityCountdown.put(aC, player.getUniqueId().toString());
                        PlayerInventory inv = player.getInventory();
                        for (int i = 0; i <= 2; i++){
                            if (inv.contains(Material.SNOW_BALL, 2)){
                                break;
                            }
                            else {
                                ItemStack item = new ItemStack(Material.SNOW_BALL);
                                ItemMeta meta = item.getItemMeta();
                                meta.setDisplayName(ChatColor.AQUA + "Web Balls");
                                List<String> lore = new ArrayList<String>();
                                lore.add(ChatColor.GREEN + "Trap a player in a web when you hit them.");
                                lore.add(ChatColor.GREEN + "you hit them.");
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                inv.addItem(item);
                            }
                        }
                        cancelTask(taskid);
                    }
                }
                --delay;
            }
        }, 0L, 20L);
    }

    public void startFreezerCountdown(){
        if (AbilityCountdownManager.abilityCountdown.containsValue(player.getUniqueId().toString())){

            return;
        }
        taskid =  plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new BukkitRunnable() {
            @Override
            public void run() {
                if (delay <= 0) {
                    if (player == null || !player.isOnline() || (!PlayerStatsObjectManager.getPSO(player, plugin).getKitClass().equalsIgnoreCase("Freezer"))){
                        cancelTask(taskid);
                    }
                    else {
                        AbilityCountdownManager.abilityCountdown.put(aC, player.getUniqueId().toString());
                        PlayerInventory inv = player.getInventory();
                        for (int i = 0; i <= 2; i++){
                            if (inv.contains(Material.SNOW_BALL, 2)){
                                break;
                            }
                            else {
                                ItemStack item = new ItemStack(Material.SNOW_BALL);
                                ItemMeta meta = item.getItemMeta();
                                meta.setDisplayName(ChatColor.GREEN + "Slow Balls");
                                List<String> lore = new ArrayList<String>();
                                lore.add(ChatColor.AQUA + "Deal slowness when you hit someone");
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                                inv.addItem(item);
                            }
                        }
                        cancelTask(taskid);
                    }
                }
                --delay;
            }
        }, 0L, 20L);
    }

    public void startPyroCountdown(){
        if (AbilityCountdownManager.abilityCountdown.containsValue(player.getUniqueId().toString())){
            return;
        }
        taskid =  plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new BukkitRunnable() {
            @Override
            public void run() {
                if (delay <= 0) {
                    if (player == null || !player.isOnline() || (!PlayerStatsObjectManager.getPSO(player, plugin).getKitClass().equalsIgnoreCase("Pyro"))){
                        cancelTask(taskid);
                    }
                    else {
                        AbilityCountdownManager.abilityCountdown.put(aC, player.getUniqueId().toString());
                        PlayerInventory inv = player.getInventory();
                        for (int i = 0; i <= 2; i++){
                            if (inv.contains(Material.EGG, 2)){
                                break;
                            }
                            else {
                                ItemStack item = new ItemStack(Material.EGG);
                                ItemMeta meta = item.getItemMeta();
                                meta.setDisplayName(ChatColor.DARK_RED + "Fire Egg");
                                item.setItemMeta(meta);
                                List<String> lore = new ArrayList<String>();
                                lore.add(ChatColor.RED + "Ignite players when hit with egg.");
                                inv.addItem(item);
                            }
                        }
                        cancelTask(taskid);
                    }
                }
                --delay;
            }
        }, 0L, 20L);
    }

    public void startGravCountdown(){
        if (AbilityCountdownManager.abilityCountdown.containsValue(player.getUniqueId().toString())){
            return;
        }
        taskid =  plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new BukkitRunnable() {
            @Override
            public void run() {
                if (delay <= 0) {
                    if (player == null || !player.isOnline() || (!PlayerStatsObjectManager.getPSO(player, plugin).getKitClass().equalsIgnoreCase("Gravity"))){
                        cancelTask(taskid);
                    }
                    else {
                        AbilityCountdownManager.abilityCountdown.put(aC, player.getUniqueId().toString());
                        PlayerInventory inv = player.getInventory();
                        for (int i = 0; i <= 2; i++){
                            if (inv.contains(Material.SNOW_BALL, 2)){
                                break;
                            }
                            else {
                                ItemStack item = new ItemStack(Material.SNOW_BALL);
                                ItemMeta meta = item.getItemMeta();
                                meta.setDisplayName(ChatColor.DARK_GRAY + "Gravity ball");
                                item.setItemMeta(meta);
                                List<String> lore = new ArrayList<String>();
                                lore.add(ChatColor.AQUA + "Players who get hit");
                                lore.add(ChatColor.AQUA + "cannot jump for a couple seconds.");
                                inv.addItem(item);
                            }
                        }
                        cancelTask(taskid);
                    }
                }
                --delay;
            }
        }, 0L, 20L);
    }
    public void cancelTask(int taskid){
        plugin.getServer().getScheduler().cancelTask(taskid);
        if (AbilityCountdownManager.abilityCountdown.containsKey(this))
            AbilityCountdownManager.abilityCountdown.remove(this);
    }
}
