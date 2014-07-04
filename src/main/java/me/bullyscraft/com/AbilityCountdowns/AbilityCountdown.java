package me.bullyscraft.com.AbilityCountdowns;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Stats.PlayerStatsObjectManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

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
        final int realDelay = delay;
        if (plugin.abilityCountdown.containsValue(player.getUniqueId().toString())){
            return;
        }
        taskid =  plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (delay <= 0) {
                    if (player == null || !player.isOnline() || (!PlayerStatsObjectManager.getPSO(player, plugin).getKitClass().equalsIgnoreCase("Assassin"))){
                        cancelTask(taskid);
                    }
                    else {
                        plugin.abilityCountdown.put(aC, player.getUniqueId().toString());
                        PlayerInventory inv = player.getInventory();
                        for (int i = 0; i <= 2; i++){
                            if (inv.contains(Material.SNOW_BALL, 2)){
                                break;
                            }
                            else {
                                ItemStack item = new ItemStack(Material.SNOW_BALL);
                                ItemMeta meta = item.getItemMeta();
                                meta.setDisplayName("Web Balls");
                                item.setItemMeta(meta);

                            }
                        }

                    }
                    delay = realDelay;
                }
                --delay;
            }
        }, 20L, 20L);
    }

    public void startFreezerCountdown(){
        final int realDelay = delay;
        if (plugin.abilityCountdown.containsValue(player.getUniqueId().toString())){

            return;
        }
        taskid =  plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (delay <= 0) {
                    if (player == null || !player.isOnline() || (!PlayerStatsObjectManager.getPSO(player, plugin).getKitClass().equalsIgnoreCase("Freezer"))){
                        cancelTask(taskid);
                    }
                    else {
                        plugin.abilityCountdown.put(aC, player.getUniqueId().toString());
                        PlayerInventory inv = player.getInventory();
                        for (int i = 0; i <= 2; i++){
                            if (inv.contains(Material.SNOW_BALL, 2)){
                                break;
                            }
                            else {
                                ItemStack item = new ItemStack(Material.SNOW_BALL);
                                ItemMeta meta = item.getItemMeta();
                                meta.setDisplayName("Slow Balls");
                                item.setItemMeta(meta);

                            }
                        }

                    }
                    delay = realDelay;
                }
                --delay;
            }
        }, 20L, 20L);
    }

    public void startPyroCountdown(){
        final int realDelay = delay;
        if (plugin.abilityCountdown.containsValue(player.getUniqueId().toString())){
            return;
        }
        taskid =  plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (delay <= 0) {
                    if (player == null || !player.isOnline() || (!PlayerStatsObjectManager.getPSO(player, plugin).getKitClass().equalsIgnoreCase("Pyro"))){
                        cancelTask(taskid);
                    }
                    else {
                        plugin.abilityCountdown.put(aC, player.getUniqueId().toString());
                        PlayerInventory inv = player.getInventory();
                        for (int i = 0; i <= 2; i++){
                            if (inv.contains(Material.EGG, 2)){
                                break;
                            }
                            else {
                                ItemStack item = new ItemStack(Material.EGG);
                                ItemMeta meta = item.getItemMeta();
                                meta.setDisplayName("Fire Egg");
                                item.setItemMeta(meta);

                            }
                        }

                    }
                    delay = realDelay;
                }
                --delay;
            }
        }, 20L, 20L);
    }

    public void cancelTask(int taskid){
        plugin.getServer().getScheduler().cancelTask(taskid);
        if (plugin.abilityCountdown.containsKey(this))
        plugin.abilityCountdown.remove(this);
    }
}
