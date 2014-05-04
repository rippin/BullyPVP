package me.bullyscraft.com;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Spawn {

public static void setSpawn(Player player){
	
	World w = player.getLocation().getWorld();
	double x = player.getLocation().getX();
	double y = player.getLocation().getY();
	double z = player.getLocation().getZ();
	float yaw = player.getLocation().getYaw();
	float pitch = player.getLocation().getPitch();

	//set
	SpawnManager.getSpawnsConfig().set("spawn.world", w.getName().toString());
	SpawnManager.getSpawnsConfig().set("spawn.x", x);
	SpawnManager.getSpawnsConfig().set("spawn.y", y);
	SpawnManager.getSpawnsConfig().set("spawn.z", z);
	SpawnManager.getSpawnsConfig().set("spawn.yaw", yaw);
	SpawnManager.getSpawnsConfig().set("spawn.pitch", pitch);
	//Save
	SpawnManager.saveFile(SpawnManager.getSpawnsFile(), SpawnManager.getSpawnsConfig());
	player.sendMessage(ChatColor.GREEN + "Spawn has been set.");
}

public static Location getSpawnLoc(){
	
	World w = Bukkit.getWorld(SpawnManager.getSpawnsConfig().getString("spawn.world"));
	double x = SpawnManager.getSpawnsConfig().getDouble("spawn.x");
	double y = SpawnManager.getSpawnsConfig().getDouble("spawn.y");
	double z = SpawnManager.getSpawnsConfig().getDouble("spawn.z");
	float yaw =  (float) SpawnManager.getSpawnsConfig().getDouble("spawn.yaw");
	float pitch =  (float) SpawnManager.getSpawnsConfig().getDouble("spawn.pitch");
	
	Location loc = new Location(w, x, y, z, yaw, pitch);
	return loc;
}



}
