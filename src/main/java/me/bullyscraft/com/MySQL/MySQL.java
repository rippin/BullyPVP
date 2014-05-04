package me.bullyscraft.com.MySQL;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import me.bullyscraft.com.BullyPVP;


import java.sql.*;

public class MySQL {

    private static BoneCP connectionPool = null;
    private static String user;
    private static String database;
    private static String password;
    private static String port;
    private static String hostname;
    private static BullyPVP plugin;


    public static void MySQLInitialize(BullyPVP plugin1, String hostname1, String port1, String database1, String username1, String password1) {
        plugin = plugin1;
        hostname = hostname1;
        port = port1;
        database = database1;
        user = username1;
        password = password1;
        MySQL.configureConnPool();
    }


    private static void configureConnPool() {
        try {
            Class.forName("com.mysql.jdbc.Driver"); //also you need the MySQL driver
            BoneCPConfig config = new BoneCPConfig();
            config.setJdbcUrl("jdbc:mysql://" + hostname + ":" + port + "/" + database);
            config.setUsername(user);
            config.setPassword(password);
            config.setMinConnectionsPerPartition(8); //if you say 5 here, there will be 10 connection available
            config.setMaxConnectionsPerPartition(20);

            config.setPartitionCount(2); //2*5 = 10 connection will be available
            config.setLazyInit(false);
            //setting Lazy true means BoneCP won't open any connections before you request a one from it.
            connectionPool = new BoneCP(config); // setup the connection pool
            System.out.println("contextInitialized.....Connection Pooling is configured");
            System.out.println("Total connections ==> " + connectionPool.getTotalCreatedConnections());
            MySQL.setConnectionPool(connectionPool);

        } catch (Exception e) {
            e.printStackTrace(); //you should use exception wrapping on real-production code
        }

    }

    public static void shutdownConnPool() {

        try {
            BoneCP connectionPool = MySQL.getConnectionPool();
            System.out.println("contextDestroyed....");
            if (connectionPool != null) {
                connectionPool.shutdown(); //this method must be called only once when the application stops.
            //you don't need to call it every time when you get a connection from the Connection Pool
                System.out.println("contextDestroyed.....Connection Pooling shut downed!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized Connection getConnection() {

        Connection conn = null;
        try {
            conn = getConnectionPool().getConnection();
    //will get a thread-safe connection from the BoneCP connection pool.
    //synchronization of the method will be done inside BoneCP source

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;

    }

    public static void closeStatement(Statement stmt) {
        try {
            if (stmt != null)
                stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void closePreparedStatement(PreparedStatement pstmt)
    {
        try
        {
            if (pstmt != null)
                pstmt.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet rSet) {
        try {
            if (rSet != null)
                rSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null)
                conn.close(); //release the connection - the name is tricky but connection is not closed it is released
            //and it will stay in pool
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static BoneCP getConnectionPool() {
        return connectionPool;
    }

    public static void setConnectionPool(BoneCP connectionPool) {
        MySQL.connectionPool = connectionPool;
    }

    public static void updateSQL(final String update) {

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {

            @Override
            public void run() {

                //Run async to not lag server
                Connection c = null;

                c = MySQL.getConnection();

                PreparedStatement s = null;

                try {
                    s = c.prepareStatement(update);
                    s.executeUpdate();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                MySQL.closePreparedStatement(s);
                MySQL.closeConnection(c);
            }
            });

            }



    public static ResultSet querySQL(final String query) {
        Connection c = null;

        c = MySQL.getConnection();

        PreparedStatement s = null;

        try {
            s = c.prepareStatement(query);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        ResultSet ret = null;

        try {
            ret = s.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        return ret;
    }


}
