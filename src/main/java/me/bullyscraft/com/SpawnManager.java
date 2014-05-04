package me.bullyscraft.com;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SpawnManager {
private static BullyPVP plugin;
private static FileConfiguration spawnsConfig;
private static File spawnsFile;

@SuppressWarnings("static-access")
public SpawnManager(BullyPVP plugin){
	
	this.plugin = plugin;
	this.spawnsFile = new File(plugin.getDataFolder(), "spawns.yml");
	this.spawnsConfig = YamlConfiguration.loadConfiguration(this.spawnsFile);
	
}

public static void createFiles() {
	
	if (!(spawnsFile.exists())){
		plugin.logger.info("Spawns.yml not found, creating now.");
		try {
		spawnsFile.createNewFile();
		}
		catch (IOException e){
			e.printStackTrace();
			
		}
		
		plugin.logger.info("Spawn.yml has been created!");
		}
	
}

public static File getSpawnsFile() {
	return spawnsFile;
}

public static FileConfiguration getSpawnsConfig() {
	return spawnsConfig;
}

public static void saveFile(File file, FileConfiguration config) {
	try {
		config.save(file);
	} catch (IOException e) {
		e.printStackTrace();
	}
}


}
