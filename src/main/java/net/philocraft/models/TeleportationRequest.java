package net.philocraft.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.philocraft.TravelEssentials;
import net.philocraft.components.WarningComponent;
import net.philocraft.constants.Colors;

public class TeleportationRequest {
    
    private static HashMap<UUID, ArrayList<TeleportationRequest>> requests = new HashMap<>();

    private UUID uuid;
    private UUID target;
    private boolean reverse;

    public TeleportationRequest(UUID uuid, UUID target, boolean reverse, boolean insert) {
        this.uuid = uuid;
        this.target = target;
        this.reverse = reverse;

        if(insert) {
            try {
                TravelEssentials.getDatabase().updateStatement(
                    "INSERT INTO Teleports(uuid, target, reverse) VALUES('" + 
                    uuid + "', '" + 
                    target + "', " +
                    reverse + ");"
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }

            TeleportationRequest.putRequest(uuid, this);
        }
    }

    public static void putRequest(UUID uuid, TeleportationRequest request) {
        if(requests.containsKey(uuid) && requests.get(uuid) != null && requests.get(uuid).size() != 0) {
            requests.get(uuid).add(request);
        } else {
            requests.put(uuid, new ArrayList<>(Arrays.asList(request)));
        }
    }

    public static boolean isOnline(Player player) {
        boolean online = false;

        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p.getUniqueId().equals(player.getUniqueId())) {
                online = true;
            }
        }

        return online;
    }

    public void teleport() {
        Player player = Bukkit.getPlayer(this.uuid);
        Player target = Bukkit.getPlayer(this.target);
        
        if(!reverse) {
            player.teleport(target.getLocation());
        } else {
            target.teleport(player.getLocation());
        }
    }

    public void confirm() {
        Player player = Bukkit.getPlayer(this.uuid);
        Player target = Bukkit.getPlayer(this.target);

        player.sendMessage(
            Colors.SUCCESS.getChatColor() + "Teleportation request successfully sent to " + 
            Colors.COMMON.getChatColor() + target.getName() + 
            Colors.SUCCESS.getChatColor() + "."
        );
    }

    public void send() {
        Player player = Bukkit.getPlayer(this.uuid);
        Player target = Bukkit.getPlayer(this.target);

        new WarningComponent(
            target,
            new String[]{"", player.getName(), " wants to teleport to you. Accept? "},
            "/tpaccept " + player.getUniqueId(),
            "/tpdeny " + player.getUniqueId()
        ).send();

        this.confirm();
    }

}
