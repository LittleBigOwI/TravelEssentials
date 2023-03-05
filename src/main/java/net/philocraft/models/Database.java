package net.philocraft.models;

import net.philocraft.TravelEssentials;

public class Database {
    
    private static int maxTeleports;

    private Database(int maxTeleports) {
        Database.maxTeleports = maxTeleports;
    }

    public static Database init(TravelEssentials plugin) {
        plugin.saveDefaultConfig();
        
        int maxTeleports = plugin.getConfig().getInt("maxTeleports");
        return new Database(maxTeleports);
    } 

    public static int getMaxTeleports() {
        return Database.maxTeleports;
    }

}
