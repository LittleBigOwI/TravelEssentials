package net.philocraft;

import org.bukkit.plugin.java.JavaPlugin;

import net.philocraft.commands.TpaCommand;
import net.philocraft.commands.TpacceptCommand;
import net.philocraft.commands.TpahereCommand;

public final class TravelEssentials extends JavaPlugin {

    @Override
    public void onEnable() {
    
        //!REGSITER COMMANDS
        this.getCommand("tpa").setExecutor(new TpaCommand());
        this.getCommand("tpaccept").setExecutor(new TpacceptCommand());
        this.getCommand("tpahere").setExecutor(new TpahereCommand());

        this.getLogger().info("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Plugin disabled.");
    }

}
