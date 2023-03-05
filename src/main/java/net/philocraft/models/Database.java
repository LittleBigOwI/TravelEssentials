package net.philocraft.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;

import net.philocraft.TravelEssentials;

public class Database {
 
    private static int maxRequests;

    private String host;
    private String database;
    private String user;
    private String password;

    private Connection connection;
    private TravelEssentials plugin;

    private Database(TravelEssentials plugin, String host, String database, String user, String password) throws ClassNotFoundException, SQLException {
        this.plugin = plugin;
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;

        Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, user, password);

        this.createStatement(
            "CREATE TABLE IF NOT EXISTS Teleports(" +
            "id int NOT NULL UNIQUE AUTO_INCREMENT, " +
            "uuid TEXT NOT NULL, " +
            "target TEXT NOT NULL, " +
            "x FLOAT NOT NULL, " +
            "y FLOAT NOT NULL, " +
            "z FLOAT NOT NULL, " +
            "yaw FLOAT NOT NULL, " +
            "pitch FLOAT NOT NULL);"
        );
    }

    private void resetConnection() throws SQLException {
        this.connection.close();
        this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.database, this.user, this.password);
    }

    public static Database init(TravelEssentials travelEssentials) {
        travelEssentials.saveDefaultConfig();
        ConfigurationSection credentials = travelEssentials.getConfig().getConfigurationSection("database");

        Database database = null;
        try {
            database = new Database(
                travelEssentials,
                credentials.getString("host"), 
                credentials.getString("database"), 
                credentials.getString("user"), 
                credentials.getString("password")
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        maxRequests = travelEssentials.getConfig().getInt("maxRequests");

        return database;
    } 

    public boolean createStatement(String sql) throws SQLException {
        this.resetConnection();
        return this.connection.createStatement().execute(sql);
    }

    public int updateStatement(String sql) throws SQLException {
        this.resetConnection();
        return this.connection.createStatement().executeUpdate(sql);
    }

    public ResultSet fetchStatement(String sql) throws SQLException {
        this.resetConnection();
        return this.connection.prepareStatement(sql).executeQuery();
    }

    public void loadTeleports() throws SQLException {
        ResultSet results = this.fetchStatement("SELECT * FROM Teleports");

        int count = 0;
        while(results.next()) {
            UUID uuid = UUID.fromString(results.getString("uuid"));
            UUID target = UUID.fromString(results.getString("target"));
            boolean reverse = results.getBoolean("reverse");

            TeleportationRequest.putRequest(uuid, new TeleportationRequest(uuid, target, reverse, false));
            count++;
        }

        this.plugin.getLogger().info("Loaded " + count + " teleportaion requests");
    }

    public int getMaxRequests() {
        return maxRequests;
    }
}