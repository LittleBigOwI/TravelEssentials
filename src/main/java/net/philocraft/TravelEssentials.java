package net.philocraft;

import org.bukkit.plugin.java.JavaPlugin;

public final class TravelEssentials extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getLogger().info("Plugion enabled.");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Plugion disabled.");
    }

}
