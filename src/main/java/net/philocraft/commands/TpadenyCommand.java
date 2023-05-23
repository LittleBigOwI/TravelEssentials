package net.philocraft.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import dev.littlebigowl.api.constants.Colors;
import dev.littlebigowl.api.constants.Worlds;
import dev.littlebigowl.api.errors.InvalidArgumentsException;
import dev.littlebigowl.api.errors.InvalidSenderException;
import dev.littlebigowl.api.errors.InvalidWorldException;
import net.philocraft.errors.PlayerNotFoundException;
import net.philocraft.errors.TeleportationRequestNotFoundException;
import net.philocraft.models.TeleportationRequest;

public class TpadenyCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if(!(sender instanceof Player && label.equalsIgnoreCase("tpadeny"))) {
            return new InvalidSenderException("You need to be a player to use this command.").sendCause(sender);
        }

        Player target = (Player) sender;

        if(!target.getWorld().equals(Worlds.OVERWORLD.getWorld())) {
            return new InvalidWorldException("Teleportations can only be used in the overword.").sendCause(sender);
        }

        if(args.length != 1) {
            return new InvalidArgumentsException().sendCause(sender);
        }

        Player player = Bukkit.getPlayer(UUID.fromString(args[0]));
        
        if(player == null) {
            return new PlayerNotFoundException().sendCause(sender);
        }
        
        TeleportationRequest request = TeleportationRequest.getTeleportationRequest(player, target);
        
        if(request == null) {
            return new TeleportationRequestNotFoundException().sendCause(sender);
        }

        TeleportationRequest.deleteRequest(player.getUniqueId(), target.getUniqueId());
        
        target.sendMessage(
            Colors.SUCCESS.getChatColor() + "Denied teleportation request from " +
            Colors.SUCCESS_DARK.getChatColor() + player.getName() +
            Colors.SUCCESS.getChatColor() + "."
        );
        player.sendMessage(
            Colors.FAILURE_DARK.getChatColor() + target.getName() + 
            Colors.FAILURE.getChatColor() + " denied your teleportation request."
        );
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
    
}
