package me.bullyscraft.com;

import me.bullyscraft.com.Scoreboards.BullyScoreboardManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class PrefixConfig {

    private static BullyPVP plugin;
    private static FileConfiguration config;
    private static File configFile;


    @SuppressWarnings("static-access")
    public PrefixConfig(BullyPVP plugin){
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "config.yml");
        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    public static void create(){
        if (!(getFile().exists())){
            plugin.getServer().getLogger().info("prefixes.yml not found. Creating now...");
            try {
                plugin.getDataFolder().mkdirs();
                getFile().createNewFile();
                copy(plugin.getResource("prefixes.yml"), configFile);
                reload();
                saveFile(getFile(), getConfig());
                plugin.getServer().getLogger().info("prefixes.yml has been created!");
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

    }

    private static void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        BullyScoreboardManager.loadPrefixList();
    }
}
