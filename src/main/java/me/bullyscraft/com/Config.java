package me.bullyscraft.com;



import me.bullyscraft.com.Classes.KitManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config {

	private static BullyPVP plugin;
	private static FileConfiguration config;
	private static File configFile;
	

	@SuppressWarnings("static-access")
	public Config(BullyPVP plugin){
		this.plugin = plugin;
		this.configFile = new File(plugin.getDataFolder(), "config.yml");
		this.config = YamlConfiguration.loadConfiguration(configFile);
	}

	public static void create(){
		if (!(getFile().exists())){
			plugin.getServer().getLogger().info("Config.yml not found. Creating now...");
			try {
				getFile().createNewFile();
				setConfigInfo();
				saveFile(getFile(), getConfig());
				plugin.getServer().getLogger().info("Config.yml has been created!");	
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
	}
	public static void setConfigInfo(){

        List<String> items = new ArrayList<String>();
        items.add("items");
        List<String> armorEnchants = new ArrayList<String>();
        armorEnchants.add("1");
        armorEnchants.add("2");
        armorEnchants.add("2");
        List<String> weaponEnchants = new ArrayList<String>();
        weaponEnchants.add("1");
        weaponEnchants.add("2");
        weaponEnchants.add("3");
        List<String> lore = new ArrayList<String>();
        lore.add("1");
        lore.add("2");
        lore.add("3");
        List<String> lore2 = new ArrayList<String>();
        lore2.add("1");
        lore2.add("2");
        lore2.add("3");
        List<String> potions = new ArrayList<String>();
        getConfig().set("Version", 2.0);
        getConfig().set("Transferred-Stats", false);
        getConfig().set("MySQL.Hostname", "localhost");
        getConfig().set("MySQL.Port", "80");
        getConfig().set("MySQL.Database", "Database_Name");
        getConfig().set("MySQL.Username", "User");
        getConfig().set("MySQL.Password", "Password");
        getConfig().set("Kits.Example.Description", "Kit description here...");
        getConfig().set("Kits.Example.Potions", potions); //name:length:power
        getConfig().set("Kits.Example.Type", "PREMIUM/DEFAULT/REGULAR");
        getConfig().set("Kits.Example.NoPermMessage", "No Perm Message go here k");
        getConfig().set("Kits.Example.Armor.Type", "IRON");
        getConfig().set("Kits.Example.Armor.Enchants", armorEnchants);
        getConfig().set("Kits.Example.Armor.Lore", lore);
        getConfig().set("Kits.Example.Weapon.Item", "DIAMOND_SWORD");
        getConfig().set("Kits.Example.Weapon.Enchants", weaponEnchants);
        getConfig().set("Kits.Example.Weapon.Lore", lore2);
        getConfig().set("Kits.Example.Items", items);
        getConfig().set("Kits.Example.Buffs.Strength.Price", 800);
        getConfig().set("Kits.Example.Buffs.Strength.Type", "Potion");
        getConfig().set("Kits.Example.Buffs.Strength.Length", 12000);
        getConfig().set("Kits.Example.Buffs.Strength.Power", 0);
        getConfig().set("Kits.Example.Buffs.Strength.Description", "Buff Describe...");
        getConfig().set("Kits.Example.Buffs.Speed.Price", 200);
        getConfig().set("Kits.Example.Buffs.Speed.Type", "Potion");
        getConfig().set("Kits.Example.Buffs.Speed.Length", 2000);
        getConfig().set("Kits.Example.Buffs.Speed.Power", 1);
        getConfig().set("Kits.Example.Buffs.Speed.Description", "Buff Describe...");
        getConfig().set("Kits.Example.Buffs.Refill.Price", 50);
        getConfig().set("Kits.Example.Buffs.Refill.Type", "Item");
        getConfig().set("Kits.Example.Buffs.Refill.Amount", 10);
        getConfig().set("Kits.Example.Buffs.Refill.Description", "Buff Describe...");
        getConfig().set("Kits.Example.Buffs.Protection.Price", 100);
        getConfig().set("Kits.Example.Buffs.Protection.Type", "Armor-Enchantment");
        getConfig().set("Kits.Example.Buffs.Protection.Description", "Buff Describe...");
        getConfig().set("Kits.Example.Buffs.Protection.Max", 4);
        getConfig().set("Kits.Example.Buffs.Sharpness.Price", 100);
        getConfig().set("Kits.Example.Buffs.Sharpness.Type", "Weapon-Enchantment");
        getConfig().set("Kits.Example.Buffs.Sharpness.Description", "Buff Describe...");
        getConfig().set("Kits.Example.Buffs.Sharpness.Max", 4);
        getConfig().set("Kits.Example.Buffs.Knockback.Price", 75);
        getConfig().set("Kits.Example.Buffs.Knockback.Type", "Weapon-Enchantment");
        getConfig().set("Kits.Example.Buffs.Knockback.Description", "Buff Describe...");
        getConfig().set("Kits.Example.Buffs.Knockback.Max", 2);
        getConfig().set("MySQL.Enabled", false);
        getConfig().set("New-Player-Message", "Welcome");

    }
	
	public static File getFile(){
		return configFile;
	}

	public static FileConfiguration getConfig(){
		return config;
	}

	public static void saveFile(File file, FileConfiguration config) {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
    public static void reload(){
        config = YamlConfiguration.loadConfiguration(configFile);
        KitManager.reloadKits(plugin);
    }
}
