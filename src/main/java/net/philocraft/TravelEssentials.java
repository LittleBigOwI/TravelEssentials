package net.philocraft;

import org.bukkit.plugin.java.JavaPlugin;

import net.philocraft.models.Database;

public final class TravelEssentials extends JavaPlugin {

    private static Database database;

    public static Database getDatabase() {
        return database;
    }

    @Override
    public void onEnable() {
        database = Database.init(this);

        this.getLogger().info("Plugion enabled.");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Plugion disabled.");
    }

}
