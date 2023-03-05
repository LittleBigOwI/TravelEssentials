package net.philocraft;

import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

import net.philocraft.commands.TpaCommand;
import net.philocraft.models.Database;

public final class TravelEssentials extends JavaPlugin {

    private static Database database;

    public static Database getDatabase() {
        return database;
    }

    @Override
    public void onEnable() {
        database = Database.init(this);

        //!REGSITER COMMANDS
        this.getCommand("tpa").setExecutor(new TpaCommand());

        try {
            database.loadTeleports();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.getLogger().info("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Plugin disabled.");
    }

}
