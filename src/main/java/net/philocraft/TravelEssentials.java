package net.philocraft;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import dev.littlebigowl.api.EssentialsAPI;
import net.philocraft.commands.TpaCommand;
import net.philocraft.commands.TpacceptCommand;
import net.philocraft.commands.TpadenyCommand;
import net.philocraft.commands.TpahereCommand;

public final class TravelEssentials extends JavaPlugin {

    public static final EssentialsAPI api = (EssentialsAPI) Bukkit.getServer().getPluginManager().getPlugin("EssentialsAPI");

    @Override
    public void onEnable() {
    
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
