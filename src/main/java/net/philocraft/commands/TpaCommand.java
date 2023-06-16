package net.philocraft.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import dev.littlebigowl.api.constants.Worlds;
import dev.littlebigowl.api.errors.InvalidArgumentsException;
import dev.littlebigowl.api.errors.InvalidSenderException;
import dev.littlebigowl.api.errors.InvalidWorldException;
import net.philocraft.TravelEssentials;
import net.philocraft.errors.MaximumTeleportationRequestException;
import net.philocraft.errors.PlayerNotFoundException;
import net.philocraft.models.TeleportationRequest;

public class TpaCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if(!(sender instanceof Player && label.equalsIgnoreCase("tpa"))) {
            return new InvalidSenderException("You need to be a player to use this command.").sendCause(sender);
        }
        
        Player player = (Player) sender;

        if(!player.getWorld().equals(Worlds.OVERWORLD.getWorld())) {
            return new InvalidWorldException("Teleportations can only be used in the overworld.").sendCause(sender);
        }

        if(args.length != 1) {
            return new InvalidArgumentsException().sendCause(sender);
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null) {
            return new PlayerNotFoundException().sendCause(sender);
        }

        if(target.getName().equals(player.getName())) {
            return new PlayerNotFoundException("You can't teleport to yourself.").sendCause(sender);
        }

        if(!target.getWorld().equals(Worlds.OVERWORLD.getWorld())) {
            return new InvalidWorldException("The specified player is not in the overworld.").sendCause(sender);
        }

        ArrayList<TeleportationRequest> playerRequests = TeleportationRequest.getTeleportationRequests(player.getUniqueId());

        if(playerRequests.size() >= TravelEssentials.api.teleports.getMaxTeleports()) {
            TeleportationRequest.clearTeleportationRequests(player.getUniqueId());
            return new MaximumTeleportationRequestException("The maximum number of requests was reached. Your requests have been cleared.").sendCause(sender);
        }

        TeleportationRequest request = new TeleportationRequest(player.getUniqueId(), target.getUniqueId(), false);
        request.send();

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            return null;
        }
        return new ArrayList<>();
    }
    
}
