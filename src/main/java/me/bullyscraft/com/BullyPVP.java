package me.bullyscraft.com;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import me.bullyscraft.com.Classes.KitManager;
import me.bullyscraft.com.Listeners.ConsumeItemListener;
import me.bullyscraft.com.Listeners.CustomHealthListener;
import me.bullyscraft.com.Listeners.EntityDamageByEntityListener;
import me.bullyscraft.com.Listeners.FoodChangeListener;
import me.bullyscraft.com.Listeners.InventoryClickListener;
import me.bullyscraft.com.Listeners.LoginListener;
import me.bullyscraft.com.Listeners.PlayerDeathListener;
import me.bullyscraft.com.Listeners.PlayerDropListener;
import me.bullyscraft.com.Listeners.PlayerPickupListener;
import me.bullyscraft.com.Listeners.PlayerRespawnListener;
import me.bullyscraft.com.Listeners.RegainHealthListener;
import me.bullyscraft.com.MySQL.MySQL;
import me.bullyscraft.com.MySQL.SetupTables;
import me.bullyscraft.com.Stats.PlayerStatsObject;
import me.bullyscraft.com.Stats.TransferYmlToMySQL;
import me.bullyscraft.com.cmds.KitPVPCommandExecutor;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BullyPVP extends JavaPlugin {

 public final Logger logger = Logger.getLogger("Minecraft");
 public final String prefix = (ChatColor.DARK_RED + "[" + ChatColor.GOLD + "BullyPVP" + ChatColor.DARK_RED + "]" + ChatColor.RESET);
 public static HashMap<String, AssistHandler> damage = new HashMap<String, AssistHandler>();

 /*
 public  ConcurrentHashMap<String, Integer> coins = new ConcurrentHashMap<String, Integer>();
 public  ConcurrentHashMap<String, Integer> kills = new ConcurrentHashMap<String, Integer>();
 public  ConcurrentHashMap<String, Integer> deaths = new ConcurrentHashMap<String, Integer>();
 public  ConcurrentHashMap<String, Integer> highStreak = new ConcurrentHashMap<String, Integer>();
 public  ConcurrentHashMap<String, Integer> currentStreak = new ConcurrentHashMap<String, Integer>();
 public  ConcurrentHashMap<String, String> currentKit = new ConcurrentHashMap<String, String>();
 */
 public List<PlayerStatsObject> playerStats = new ArrayList<PlayerStatsObject>();
 private Connection c = null;
 public static BullyPVP instance;

public void onEnable() {
    instance = this;
	PluginDescriptionFile pdfFile = this.getDescription();

    new SpawnManager(this);
    new Config(this);
	SpawnManager.createFiles();
	Config.create();
    FileConfiguration config = Config.getConfig();
    MySQL.MySQLInitialize(this, config.getString("MySQL.Hostname"), config.getString("MySQL.Port"),
            config.getString("MySQL.Database"), config.getString("MySQL.Username"), config.getString("MySQL.Password"));
    c = MySQL.getConnection();

    if (c == null){
        getServer().getPluginManager().disablePlugin(this);
        this.logger.info(pdfFile.getName() + " was disabled. MYSQL connection was not available.");
    }
    SetupTables.createTableIfDoesntExist(this);
    if (!Config.getConfig().getBoolean("Transferred-Stats")) {
        TransferYmlToMySQL.transfer(this);
        Config.getConfig().set("Transferred-Stats", true);
        Config.saveFile(Config.getFile(), Config.getConfig());
    }

    KitManager.loadKits(this); //load kits
    SetupTables.cacheWholeDatabase(this); //cache whole database
    PluginManager pm = this.getServer().getPluginManager();
	
	pm.registerEvents(new LoginListener(this), this);
	pm.registerEvents(new FoodChangeListener(this), this);
	pm.registerEvents(new InventoryClickListener(this), this);
	//pm.registerEvents(new PlayerChatListener(this), this);
	pm.registerEvents(new PlayerDropListener(this), this);
	pm.registerEvents(new PlayerRespawnListener(this), this);
	pm.registerEvents(new PlayerDeathListener(this), this);
	pm.registerEvents(new ConsumeItemListener(this), this);
	pm.registerEvents(new PlayerPickupListener(this), this);
	pm.registerEvents(new EntityDamageByEntityListener(this), this);
	pm.registerEvents(new RegainHealthListener(this), this);
	pm.registerEvents(new CustomHealthListener(this), this);
	
	this.getCommand("help").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("?").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("setspawn").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("kits").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("kit").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("refill").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("soup").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("powerups").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("buffs").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("sharpness").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("sharp").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("protect").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("protection").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("power").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("pow").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("knockback").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("punch").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("repair").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("fix").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("add").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("speed").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("strength").setExecutor(new KitPVPCommandExecutor(this));
	this.getCommand("stats").setExecutor(new KitPVPCommandExecutor(this));
    this.getCommand("top").setExecutor(new KitPVPCommandExecutor(this));
	
	
	
	logger.info(pdfFile.getName() + " Has Been Enabled");
}

 public void onDisable() {
	PluginDescriptionFile pdfFile = this.getDescription();
	MySQL.shutdownConnPool();
	logger.info(pdfFile.getName() + " Has Been Disabled");
    instance = null;
}

}
