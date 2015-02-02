package me.bullyscraft.com.Stats;

import me.bullyscraft.com.Scoreboards.BullyScoreBoard;
import me.bullyscraft.com.MySQL.MySQL;
import me.bullyscraft.com.Scoreboards.BullyScoreboardManager;
import org.bukkit.OfflinePlayer;
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
private OfflinePlayer player;
private int wins1v1 = 0;
private int losses1v1 = 0;
private int currentStreak1v1 = 0;
private int highStreak1v1 = 0;
private String ranking;


    public int getWins1v1() {
        return wins1v1;
    }

    public void setWins1v1(int wins1v1) {
        this.wins1v1 = wins1v1;
        if (UUID != null) {
            MySQL.updateSQL("UPDATE KitPVP SET Wins1v1 = " + wins1v1 + " WHERE UUID = '" + getUUID().toString() + "' LIMIT 1;");
        }
        else {
            MySQL.updateSQL("UPDATE KitPVP SET Wins1v1 = " + wins1v1 + " WHERE Username = '" + getUsername() + "' LIMIT 1;");
        }
    }

    public int getLosses1v1() {
        return losses1v1;
    }

    public void setLosses1v1(int losses1v1) {
        this.losses1v1 = losses1v1;

        if (UUID != null) {
            MySQL.updateSQL("UPDATE KitPVP SET Losses1v1 = " + losses1v1 + " WHERE UUID = '" + getUUID().toString() + "' LIMIT 1;");
        }
        else {
            MySQL.updateSQL("UPDATE KitPVP SET Losses1v1 = " + losses1v1 + " WHERE Username = '" + getUsername() + "' LIMIT 1;");
        }
    }

    public int getCurrentStreak1v1() {
        return currentStreak1v1;
    }

    public void setCurrentStreak1v1(int currentStreak1v1) {
        this.currentStreak1v1 = currentStreak1v1;

        if (UUID != null) {
            MySQL.updateSQL("UPDATE KitPVP SET CurrentStreak1v1 = " + currentStreak1v1 + " WHERE UUID = '" + getUUID().toString() + "' LIMIT 1;");
        }
        else {
            MySQL.updateSQL("UPDATE KitPVP SET CurrentStreak1v1 = " + currentStreak1v1 + " WHERE Username = '" + getUsername() + "' LIMIT 1;");
        }
    }

    public int getHighStreak1v1() {
        return highStreak1v1;
    }

    public void setHighStreak1v1(int highStreak1v1) {
        this.highStreak1v1 = highStreak1v1;

        if (UUID != null) {
            MySQL.updateSQL("UPDATE KitPVP SET HighStreak1v1 = " + highStreak1v1 + " WHERE UUID = '" + getUUID().toString() + "' LIMIT 1;");
        }
        else {
            MySQL.updateSQL("UPDATE KitPVP SET HighStreak1v1 = " + highStreak1v1 + " WHERE Username = '" + getUsername() + "' LIMIT 1;");
        }

    }

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
            MySQL.updateSQL("UPDATE KitPVP SET Kills = " + kills + " WHERE UUID = '" + getUUID().toString() + "' LIMIT 1;");
        }
        else {
            MySQL.updateSQL("UPDATE KitPVP SET Kills = " + kills + " WHERE Username = '" + getUsername() + "' LIMIT 1;");
        }

    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
        if (UUID != null) {
            MySQL.updateSQL("UPDATE KitPVP SET Deaths = " + deaths + " WHERE UUID = '" + getUUID().toString() + "' LIMIT 1;");
        }
        else {
            MySQL.updateSQL("UPDATE KitPVP SET Deaths = " + deaths + " WHERE Username = '" + getUsername() + "' LIMIT 1;");
        }

    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
     if (UUID != null) {
        MySQL.updateSQL("UPDATE KitPVP SET Coins = " + coins + " WHERE UUID = '" + getUUID().toString() + "' LIMIT 1;");
     }
    else {
         MySQL.updateSQL("UPDATE KitPVP SET Coins = " + coins + " WHERE Username = '" + getUsername() + "' LIMIT 1;");
     }
    }

    public int getHigheststreak() {
        return higheststreak;
    }

    public void setHigheststreak(int higheststreak) {
        this.higheststreak = higheststreak;
        if (UUID != null) {
            MySQL.updateSQL("UPDATE KitPVP SET HighStreak = " + higheststreak + " WHERE UUID = '" + getUUID().toString() + "' LIMIT 1;");
        }
        else {
            MySQL.updateSQL("UPDATE KitPVP SET HighStreak = " + higheststreak + " WHERE Username = '" + getUsername() + "' LIMIT 1;");
        }

    }

    public int getCurrentstreak() {
        return currentstreak;
    }

    public void setCurrentstreak(int currentstreak) {
        this.currentstreak = currentstreak;
        if (UUID != null) {
            MySQL.updateSQL("UPDATE KitPVP SET CurrentStreak = " + currentstreak + " WHERE UUID = '" + getUUID().toString() + "' LIMIT 1;");
        }
        else {
            MySQL.updateSQL("UPDATE KitPVP SET CurrentStreak = " + currentstreak + " WHERE Username = '" + getUsername() + "' LIMIT 1;");
        }

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        MySQL.updateSQL("UPDATE KitPVP SET Username = '" + username + "' WHERE UUID = '" + getUUID().toString() + "' LIMIT 1;");

    }

    public UUID getUUID() {
        return UUID;
    }

    public void setUUID(UUID UUID) {
        this.UUID = UUID;
        MySQL.updateSQL("UPDATE KitPVP SET UUID = '" + UUID.toString() + "' WHERE Username = '" + getUsername() + "' LIMIT 1;");

    }

    public String getKitClass() {
        return kitClass;
    }

    public void setKitClass(String kitClass) {
        this.kitClass = kitClass;
        if (UUID != null) {
            MySQL.updateSQL("UPDATE KitPVP SET CurrentKit = '" + kitClass + "' WHERE UUID = '" + getUUID().toString() + "' LIMIT 1;");
        }
        else {
            MySQL.updateSQL("UPDATE KitPVP SET CurrentKit = '" + kitClass + "' WHERE Username = '" + getUsername() + "' LIMIT 1;");
        }

    }

    public double getKd() {
     if (deaths != 0) {

         return ((double) (kills))/deaths;
     }
        else{
         return kills;
     }
    }

    public void setKd(double kd) {
        this.kd = kd;
    }

    public void setPlayer(OfflinePlayer player){
        this.player = player;
    }

    public OfflinePlayer getPlayer(){
        return player;
    }

    public void addCoins(int coins){
        this.coins = this.coins + coins;
        if (UUID != null) {
            MySQL.updateSQL("UPDATE KitPVP SET Coins = " + this.coins + " WHERE UUID = '" + getUUID().toString() + "' LIMIT 1;");
        }
        else {
            MySQL.updateSQL("UPDATE KitPVP SET Coins = " + this.coins + " WHERE Username = '" + getUsername() + "' LIMIT 1;");
        }
        BullyScoreBoard b = BullyScoreboardManager.getBullyScoreboard(UUID.toString());
        b.setPSO(this);
        b.updateWithoutPrefixes();

    }

    public void removeCoins(int coins){
        this.coins = this.coins - coins;
        if (UUID != null) {
            MySQL.updateSQL("UPDATE KitPVP SET Coins = " + this.coins + " WHERE UUID = '" + getUUID().toString() + "' LIMIT 1;");
        }
        else {
            MySQL.updateSQL("UPDATE KitPVP SET Coins = " + this.coins + " WHERE Username = '" + getUsername() + "' LIMIT 1;");
        }
        BullyScoreBoard b = BullyScoreboardManager.getBullyScoreboard(UUID.toString());
        b.setPSO(this);
        b.updateWithoutPrefixes();
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

    public void setUp(int kills, int deaths, int coins, int cs, int hs, String username, String kitClass, int wins1v1, int losses1v1, int currentStreak1v1, int highStreak1v1){
        this.kills = kills;
        this.deaths = deaths;
        this.coins = coins;
        this.currentstreak = cs;
        this.higheststreak = hs;
        this.username = username;
        this.kitClass = kitClass;
        this.wins1v1 = wins1v1;
        this.losses1v1 = losses1v1;
        this.currentStreak1v1 = currentStreak1v1;
        this.highStreak1v1 = highStreak1v1;

    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }
}
