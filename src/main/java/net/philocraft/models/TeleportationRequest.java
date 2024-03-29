package net.philocraft.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dev.littlebigowl.api.components.WarningComponent;
import dev.littlebigowl.api.constants.Colors;

public class TeleportationRequest {
    
    private static HashMap<UUID, ArrayList<TeleportationRequest>> requests = new HashMap<>();

    private UUID uuid;
    private UUID target;
    private boolean reverse;

    public TeleportationRequest(UUID uuid, UUID target, boolean reverse) {
        this.uuid = uuid;
        this.target = target;
        this.reverse = reverse;

        TeleportationRequest.putRequest(this.uuid, this);
    }

    public static void putRequest(UUID uuid, TeleportationRequest request) {
        if(requests.containsKey(uuid) && requests.get(uuid) != null && requests.get(uuid).size() != 0) {
            requests.get(uuid).add(request);
        } else {
            requests.put(uuid, new ArrayList<>(Arrays.asList(request)));
        }
    }

    public static TeleportationRequest getTeleportationRequest(Player player, Player target) {
        ArrayList<TeleportationRequest> playerRequests = requests.get(player.getUniqueId());

        for(TeleportationRequest r : playerRequests) {
            if(r.getTargetUUID().equals(target.getUniqueId())) {
                return r;
            }
        }
        return null;
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

    public static void deleteRequest(UUID uuid, UUID target) {
        ArrayList<TeleportationRequest> playerRequests = TeleportationRequest.requests.get(uuid);
        
        if(!(playerRequests == null || playerRequests.size() == 0)) {
            for(int i = playerRequests.size()-1; i > -1; i--) {
                if(playerRequests.get(i).getTargetUUID().equals(target)) {
                    playerRequests.remove(i);
                }
            }

        }
    }

    public static ArrayList<TeleportationRequest> getTeleportationRequests(UUID uuid) {
        ArrayList<TeleportationRequest> playerRequests = requests.get(uuid);
        if(playerRequests == null){
            return new ArrayList<>();
        }
        return playerRequests;
    }

    public static void clearTeleportationRequests(UUID uuid) {
        requests.put(uuid, new ArrayList<>());        
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public UUID getTargetUUID() {
        return this.target;
    }

    public void teleport() {
        Player player = Bukkit.getPlayer(this.uuid);
        Player target = Bukkit.getPlayer(this.target);
        
        if(!reverse) {
            player.teleport(target.getLocation());
        } else {
            target.teleport(player.getLocation());
        }

        TeleportationRequest.deleteRequest(this.uuid, this.target);
    }

    public void confirm() {
        Player player = Bukkit.getPlayer(this.uuid);
        Player target = Bukkit.getPlayer(this.target);

        player.sendMessage(
            Colors.SUCCESS.getChatColor() + "Teleportation request successfully sent to " + 
            Colors.SUCCESS_DARK.getChatColor() + target.getName() + 
            Colors.SUCCESS.getChatColor() + "."
        );
    }

    public void send() {
        Player player = Bukkit.getPlayer(this.uuid);
        Player target = Bukkit.getPlayer(this.target);

        if(!this.reverse) {
            new WarningComponent(
                target,
                new String[]{"", player.getName(), " wants to teleport to you. Accept? "},
                "/tpaccept " + player.getUniqueId(),
                "/tpadeny " + player.getUniqueId()
            ).send();

        } else {
            new WarningComponent(
                target,
                new String[]{"", player.getName(), " wants to teleport you. Accept? "},
                "/tpaccept " + player.getUniqueId(),
                "/tpdeny " + player.getUniqueId()
            ).send();

        }

        this.confirm();
    }

}
