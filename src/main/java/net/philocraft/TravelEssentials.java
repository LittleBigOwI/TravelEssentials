package net.philocraft;

import org.bukkit.plugin.java.JavaPlugin;

import net.philocraft.commands.TpaCommand;
import net.philocraft.commands.TpacceptCommand;
import net.philocraft.commands.TpadenyCommand;
import net.philocraft.commands.TpahereCommand;
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
        this.getCommand("tpaccept").setExecutor(new TpacceptCommand());
        this.getCommand("tpahere").setExecutor(new TpahereCommand());
        this.getCommand("tpadeny").setExecutor(new TpadenyCommand());

        this.getLogger().info("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Plugin disabled.");
    }

}
