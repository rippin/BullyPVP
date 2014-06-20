package me.bullyscraft.com.MySQL;

import me.bullyscraft.com.BullyPVP;
import me.bullyscraft.com.Stats.PlayerStatsObject;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.*;

public class SetupTables {

    public static void createTableIfDoesntExist() {
        if (MySQL.getConnectionPool().getConfig().getJdbcUrl().contains("sqlite")){
            MySQL.updateSQL("CREATE TABLE IF NOT EXISTS KitPVP ( Username VARCHAR(20) NOT NULL, " +
                    "Coins int(20) NOT NULL DEFAULT '0', UUID VARCHAR(60) NOT NULL, Kills int(20) NOT NULL DEFAULT '0', " +
                    "Deaths int(20) NOT NULL DEFAULT '0', HighStreak int(20) NOT NULL DEFAULT '0', " +
                    "CurrentStreak int(20) NOT NULL DEFAULT '0', CurrentKit VARCHAR(20), PRIMARY KEY (UUID));");
        }
        else {
        MySQL.updateSQL("CREATE TABLE IF NOT EXISTS KitPVP (id int NOT NULL AUTO_INCREMENT, Username VARCHAR(20) NOT NULL, " +
                "Coins int(20) NOT NULL DEFAULT '0', UUID VARCHAR(60) NOT NULL, Kills int(20) NOT NULL DEFAULT '0', " +
                "Deaths int(20) NOT NULL DEFAULT '0', HighStreak int(20) NOT NULL DEFAULT '0', " +
                "CurrentStreak int(20) NOT NULL DEFAULT '0', CurrentKit VARCHAR(20), PRIMARY KEY (id));");
            }
        }


    public static void cacheWholeDatabase(final BullyPVP plugin) {

        final String query = "SELECT * FROM KitPVP ;";


                try {
                    Connection c = null;

                    c = MySQL.getConnection();

                    PreparedStatement s = c.prepareStatement(query);
                    final ResultSet r = s.executeQuery();
                    //Check if UUID COLUMN exists. Create if not.
                    ResultSetMetaData md = r.getMetaData();
                    int col = md.getColumnCount();
                    List<String> columnList = new ArrayList<String>();
                    for (int i = 1; i <= col; i++){
                        String colName = md.getColumnName(i);
                         columnList.add(colName);
                    }
                    if (!columnList.contains("UUID")){
                        addUUIDColumn(plugin);
                        plugin.logger.info("UUID Column has been created.");
                    }
                        if (!columnList.contains("Wins1v1")){
                            add1v1Columns(plugin);
                            plugin.logger.info("1v1 Columns have been added.");
                        }

                    while (r.next()) {
                      String username = r.getString("Username");
                      int coins = r.getInt("Coins");
                      int kills = r.getInt("Kills");
                      int deaths = r.getInt("Deaths");
                      int highstreak = r.getInt("HighStreak");
                      int currentstreak = r.getInt("CurrentStreak");
                      String currentkit = r.getString("CurrentKit");
                      String uuid = r.getString("UUID");
                      if (plugin.isBully1v1Enabled()){
                          int wins1v1 = r.getInt("Wins1v1");
                          int losses1v1 = r.getInt("Losses1v1");
                          int currentStreak1v1 = r.getInt("CurrentStreak1v1");
                          int highStreak1v1 = r.getInt("HighStreak1v1");
                      }
                        PlayerStatsObject pso;
                        if (uuid != null) {
                        pso = new PlayerStatsObject(UUID.fromString(uuid));
                        }
                       else {
                          pso = new PlayerStatsObject(username);
                        }

                        pso.setUp(kills, deaths, coins, currentstreak, highstreak, username, currentkit);

                        plugin.playerStats.add(pso);

                    }
                    MySQL.closeResultSet(r);
                    MySQL.closePreparedStatement(s);
                    MySQL.closeConnection(c);

                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }


    public static void insertIntoTable(BullyPVP plugin, Player p, String kitname) {
        if (MySQL.getConnectionPool().getConfig().getJdbcUrl().contains("sqlite")){
            if (plugin.isBully1v1Enabled()){
                MySQL.updateSQL("INSERT OR IGNORE INTO KitPVP (Username, Kills, Deaths, Coins, HighStreak, CurrentStreak, CurrentKit, " +
                        "UUID, Wins1v1, Losses1v1, HighStreak1v1, CurrentStreak1v1)" +
                        " VALUES ('" +p.getName() + "', '0', '0', '100', '0', '0', '" + kitname + "', '" + p.getUniqueId().toString()
                        +"', '0', '0', '0', '0');");
            }
            else {
                MySQL.updateSQL("INSERT OR IGNORE INTO KitPVP (Username, Kills, Deaths, Coins, HighStreak, CurrentStreak, CurrentKit, UUID)" +
                        " VALUES ('" +p.getName() + "', '0', '0', '100', '0', '0', '" + kitname + "', '" + p.getUniqueId().toString() +"');");
                }

        }
        else {
        if (plugin.isBully1v1Enabled()){
            MySQL.updateSQL("INSERT IGNORE INTO KitPVP SET Username = '" + p.getName() + "', Kills = 0, Deaths = 0, Coins = 100, HighStreak = 0," +
                    " CurrentStreak = 0, CurrentKit = '" + kitname +"', UUID = '"
                    + p.getUniqueId().toString() + "', Wins1v1 = 0, Losses1v1 = 0, HighStreak1v1 = 0, CurrentStreak1v1 = 0;");
            plugin.getLogger().info("Added " + p.getName() + " into table.");
        }
       else {
       MySQL.updateSQL("INSERT IGNORE INTO KitPVP SET Username = '" + p.getName() + "', Kills = 0, Deaths = 0, Coins = 100, HighStreak = 0," +
                " CurrentStreak = 0, CurrentKit = '" + kitname +"', UUID = '" + p.getUniqueId().toString() + "';");
        plugin.getLogger().info("Added " + p.getName() + " into table.");
            }
        }
      }
    public static PlayerStatsObject cacheNewPlayer(Player player, String defaultKit, BullyPVP plugin){
        String username = player.getName();
        UUID UUID = player.getUniqueId();
        PlayerStatsObject pso = new PlayerStatsObject(UUID);
        pso.setUsername(username);
        pso.setUUID(UUID);
        pso.setCoins(0);
        pso.setKills(0);
        pso.setDeaths(0);
        pso.setHigheststreak(0);
        pso.setCurrentstreak(0);
        pso.setKitClass(defaultKit);
        if (plugin.isBully1v1Enabled()){
        pso.setWins1v1(0);
        pso.setLosses1v1(0);
        pso.setHighStreak1v1(0);
        pso.setCurrentStreak1v1(0);
        }
       plugin.playerStats.add(pso);
        return pso;
    }

    public static void top10Kills(final BullyPVP plugin, final CommandSender player) {
        final String query = "SELECT * FROM KitPVP ORDER BY Kills DESC LIMIT 10";

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {

            @Override
            public void run() {

                try {
                    Connection c = null;

                    c = MySQL.getConnection();


                    PreparedStatement s = c.prepareStatement(query);
                    final ResultSet r = s.executeQuery();

                    try {
                        player.sendMessage(ChatColor.GOLD + "========" + ChatColor.RED + " TOP 10 KILLS" + ChatColor.GOLD + "========");
                        while (r.next()) {
                            int wins = 0;
                            String name = null;
                            wins = r.getInt("Kills");
                            name = r.getString("Username");


                            player.sendMessage(ChatColor.AQUA + name + ChatColor.GREEN + " Kills : " + wins);


                        }

                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    MySQL.closeResultSet(r);
                    MySQL.closePreparedStatement(s);
                    MySQL.closeConnection(c);

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    public static void top10Deaths(final BullyPVP plugin, final CommandSender player) {
        final String query = "SELECT * FROM KitPVP ORDER BY Deaths DESC LIMIT 10";

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {

            @Override
            public void run() {

                try {
                    Connection c = null;

                    c = MySQL.getConnection();


                    PreparedStatement s = c.prepareStatement(query);
                    final ResultSet r = s.executeQuery();

                    try {
                        player.sendMessage(ChatColor.GOLD + "========" + ChatColor.RED + " TOP 10 DEATHS " + ChatColor.GOLD + "========");
                        while (r.next()) {
                            int wins = 0;
                            String name = null;
                            wins = r.getInt("Deaths");
                            name = r.getString("Username");


                            player.sendMessage(ChatColor.AQUA + name + ChatColor.GREEN + " Deaths : " + wins);


                        }
                        MySQL.closeResultSet(r);
                        MySQL.closePreparedStatement(s);
                        MySQL.closeConnection(c);
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    MySQL.closeResultSet(r);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    public static void top10HighestStreak(final BullyPVP plugin, final CommandSender player) {
        final String query = "SELECT * FROM KitPVP ORDER BY HighStreak DESC LIMIT 10";

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {

            @Override
            public void run() {

                try {
                    Connection c = null;

                    c = MySQL.getConnection();


                    PreparedStatement s = c.prepareStatement(query);
                    final ResultSet r = s.executeQuery();

                    try {
                        player.sendMessage(ChatColor.GOLD + "========" + ChatColor.RED + " TOP 10 HIGHEST STREAK " + ChatColor.GOLD + "========");
                        while (r.next()) {
                            int wins = 0;
                            String name = null;
                            wins = r.getInt("HighStreak");
                            name = r.getString("Username");


                            player.sendMessage(ChatColor.AQUA + name + ChatColor.GREEN + " Highest Streak : " + wins);


                        }
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    MySQL.closeResultSet(r);
                    MySQL.closePreparedStatement(s);
                    MySQL.closeConnection(c);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    public static void top10CurrentStreak(final BullyPVP plugin, final CommandSender player) {
        final String query = "SELECT * FROM KitPVP ORDER BY CurrentStreak DESC LIMIT 10";

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {

            @Override
            public void run() {

                try {
                    Connection c = null;

                    c = MySQL.getConnection();


                    PreparedStatement s = c.prepareStatement(query);
                    final ResultSet r = s.executeQuery();

                    try {
                        player.sendMessage(ChatColor.GOLD + "========" + ChatColor.RED + " TOP 10 CURRENT STREAKS " + ChatColor.GOLD + "========");
                        while (r.next()) {
                            int wins = 0;
                            String name = null;
                            wins = r.getInt("CurrentStreak");
                            name = r.getString("Username");


                            player.sendMessage(ChatColor.AQUA + name + ChatColor.GREEN + " Current Streak : " + wins);


                        }
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    MySQL.closeResultSet(r);
                    MySQL.closePreparedStatement(s);
                    MySQL.closeConnection(c);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    public static void top10Coins(final BullyPVP plugin, final CommandSender player) {
        final String query = "SELECT * FROM KitPVP ORDER BY Coins DESC LIMIT 10";

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {

            @Override
            public void run() {

                try {
                    Connection c = null;

                    c = MySQL.getConnection();


                    PreparedStatement s = c.prepareStatement(query);
                    final ResultSet r = s.executeQuery();

                    try {
                        player.sendMessage(ChatColor.GOLD + "========" + ChatColor.RED + " TOP 10 COINS " + ChatColor.GOLD + "========");
                        while (r.next()) {
                            int wins = 0;
                            String name = null;
                            wins = r.getInt("Coins");
                            name = r.getString("Username");


                            player.sendMessage(ChatColor.AQUA + name + ChatColor.GREEN +  " Coins : " + wins);


                        }
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    MySQL.closeResultSet(r);
                    MySQL.closePreparedStatement(s);
                    MySQL.closeConnection(c);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    public static void addUUIDColumn(final BullyPVP plugin){

               MySQL.updateSQL("ALTER TABLE KitPVP ADD UUID VARCHAR(60) AFTER Username;");
    }

    public static void add1v1Columns(final BullyPVP plugin){
        MySQL.updateSQL("ALTER TABLE KitPVP ADD Wins1v1 int(20) NOT NULL DEFAULT '0';");
        MySQL.updateSQL("ALTER TABLE KitPVP ADD Losses1v1 int(20) NOT NULL DEFAULT '0';");
        MySQL.updateSQL("ALTER TABLE KitPVP ADD HighStreak1v1 int(20) NOT NULL DEFAULT '0';");
        MySQL.updateSQL("ALTER TABLE KitPVP ADD CurrentStreak1v1 int(20) NOT NULL DEFAULT '0';");

    }
    public static void transferUUID(final BullyPVP plugin){
       System.out.println("Attempting to begin the transfer.");
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
           @Override
           public void run() {
               List<String> usernames = new ArrayList<String>();

               for (PlayerStatsObject pso : plugin.playerStats){
                   if (pso.getUUID() == null) {
                       usernames.add(pso.getUsername());
                   }
               }
               System.out.println("Retrieved " + usernames.size() + " usernames, now attempting to retrieve UUID's");
               UUIDFetcher fetcher = new UUIDFetcher(usernames);
               Map<String, UUID> data = null;
               try {
                   data =  fetcher.call();
               } catch (Exception e){
                   e.printStackTrace();
               }
               System.out.println("Done retrieving " + data.size() +  " UUID's from mojang api. ");
               for (Map.Entry<String, UUID> entry : data.entrySet() ) {

                   MySQL.updateSQL("UPDATE KitPVP SET UUID = '" + entry.getValue().toString() + "' WHERE Username = '" + entry.getKey() + "';");

               }
               System.out.println("Done updating database. Restart server for no lag.");
           }
       });
      }
}