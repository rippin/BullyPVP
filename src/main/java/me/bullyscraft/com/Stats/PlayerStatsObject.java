package me.bullyscraft.com.Stats;

import me.bullyscraft.com.BullyScoreBoard;
import me.bullyscraft.com.MySQL.MySQL;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerStatsObject {

private int kills;
private int deaths;
private int coins;
private int higheststreak;
private int currentstreak;
private String username;
private UUID UUID;
private String kitClass;
private double kd;
private Player player;

    public PlayerStatsObject(UUID UUID){
       this.UUID = UUID;
    }

    public PlayerStatsObject(String username){
    this.username = username;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
        if (UUID != null) {
            MySQL.updateSQL("UPDATE KitPVP SET Kills = " + kills + " WHERE UUID = '" + getUUID().toString() + "';");
        }
        else {
            MySQL.updateSQL("UPDATE KitPVP SET Kills = " + kills + " WHERE Username = '" + getUsername() + "';");
        }

    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
        if (UUID != null) {
            MySQL.updateSQL("UPDATE KitPVP SET Deaths = " + deaths + " WHERE UUID = '" + getUUID().toString() + "';");
        }
        else {
            MySQL.updateSQL("UPDATE KitPVP SET Deaths = " + deaths + " WHERE Username = '" + getUsername() + "';");
        }

    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
     if (UUID != null) {
        MySQL.updateSQL("UPDATE KitPVP SET Coins = " + coins + " WHERE UUID = '" + getUUID().toString() + "';");
     }
    else {
         MySQL.updateSQL("UPDATE KitPVP SET Coins = " + coins + " WHERE Username = '" + getUsername() + "';");
     }
    }

    public int getHigheststreak() {
        return higheststreak;
    }

    public void setHigheststreak(int higheststreak) {
        this.higheststreak = higheststreak;
        if (UUID != null) {
            MySQL.updateSQL("UPDATE KitPVP SET HighStreak = " + higheststreak + " WHERE UUID = '" + getUUID().toString() + "';");
        }
        else {
            MySQL.updateSQL("UPDATE KitPVP SET HighStreak = " + higheststreak + " WHERE Username = '" + getUsername() + "';");
        }

    }

    public int getCurrentstreak() {
        return currentstreak;
    }

    public void setCurrentstreak(int currentstreak) {
        this.currentstreak = currentstreak;
        if (UUID != null) {
            MySQL.updateSQL("UPDATE KitPVP SET CurrentStreak = " + currentstreak + " WHERE UUID = '" + getUUID().toString() + "';");
        }
        else {
            MySQL.updateSQL("UPDATE KitPVP SET CurrentStreak = " + currentstreak + " WHERE Username = '" + getUsername() + "';");
        }

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        MySQL.updateSQL("UPDATE KitPVP SET Username = '" + username + "' WHERE UUID = '" + getUUID().toString() + "';");

    }

    public UUID getUUID() {
        return UUID;
    }

    public void setUUID(UUID UUID) {
        this.UUID = UUID;
        MySQL.updateSQL("UPDATE KitPVP SET UUID = '" + UUID.toString() + "' WHERE Username = '" + getUsername() + "';");

    }

    public String getKitClass() {
        return kitClass;
    }

    public void setKitClass(String kitClass) {
        this.kitClass = kitClass;
        if (UUID != null) {
            MySQL.updateSQL("UPDATE KitPVP SET CurrentKit = '" + kitClass + "' WHERE UUID = '" + getUUID().toString() + "';");
        }
        else {
            MySQL.updateSQL("UPDATE KitPVP SET CurrentKit = '" + kitClass + "' WHERE Username = '" + getUsername() + "';");
        }

    }

    public double getKd() {
     if (deaths != 0) {
     return (double)(kills/deaths);
     }
        else{
         return kills;
     }
    }

    public void setKd(double kd) {
        this.kd = kd;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public Player getPlayer(){
        return player;
    }

    public void addCoins(int coins){
        this.coins = this.coins + coins;
        if (UUID != null) {
            MySQL.updateSQL("UPDATE KitPVP SET Coins = " + this.coins + " WHERE UUID = '" + getUUID().toString() + "';");
        }
        else {
            MySQL.updateSQL("UPDATE KitPVP SET Coins = " + this.coins + " WHERE Username = '" + getUsername() + "';");
        }
        BullyScoreBoard b = new BullyScoreBoard(player, this);
        b.setUp();

    }

    public void removeCoins(int coins){
        this.coins = this.coins - coins;
        if (UUID != null) {
            MySQL.updateSQL("UPDATE KitPVP SET Coins = " + this.coins + " WHERE UUID = '" + getUUID().toString() + "';");
        }
        else {
            MySQL.updateSQL("UPDATE KitPVP SET Coins = " + this.coins + " WHERE Username = '" + getUsername() + "';");
        }
        BullyScoreBoard b = new BullyScoreBoard(player, this);
        b.setUp();

    }

    public void setUp(int kills, int deaths, int coins, int cs, int hs, String username, String kitClass){
        this.kills = kills;
        this.deaths = deaths;
        this.coins = coins;
        this.currentstreak = cs;
        this.higheststreak = hs;
        this.username = username;
        this.kitClass = kitClass;
    }


}
