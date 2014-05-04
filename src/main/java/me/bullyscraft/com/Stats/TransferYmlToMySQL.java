package me.bullyscraft.com.Stats;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.MySQL.MySQL;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;


public class TransferYmlToMySQL {

public static void transfer(BullyPVP plugin){

    File dir = new File(plugin.getDataFolder(), "players");
    int i = 0;
    if (dir.exists()){
        for (File child: dir.listFiles()){
            FileConfiguration config = YamlConfiguration.loadConfiguration(child);
            String name = config.getString("Name");
            int kills = config.getInt("Kills");
            int deaths = config.getInt("Deaths");
            int currentStreak = config.getInt("Streak.Current");
            int highestStreak = config.getInt("Streak.Highest");
            // double killdeath = config.getDouble("K/D");
            int coins = config.getInt("Coins");
            String kit = config.getString("Class");

            MySQL.updateSQL("INSERT INTO KitPVP SET Username = '" + name + "', Kills = "
                    + kills + ", Deaths = " + deaths + ", CurrentStreak = "
                    + currentStreak + ", HighStreak = " + highestStreak + ", Coins = "
                    + coins + ", CurrentKit = '"+ kit + "' ;");
            plugin.getLogger().info(name + "'s config has been transferred to MySQL. " + ++i);

        }
    }

    }
}
