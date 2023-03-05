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

import net.philocraft.constants.Colors;
import net.philocraft.constants.Worlds;
import net.philocraft.errors.InvalidArgumentsException;
import net.philocraft.errors.InvalidSenderException;
import net.philocraft.errors.InvalidWorldException;
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
        TeleportationRequest request = TeleportationRequest.getTeleportationRequest(player, target);
        
        if(request == null) {
            return new TeleportationRequestNotFoundException().sendCause(sender);
        }

        TeleportationRequest.deleteRequest(player.getUniqueId(), target.getUniqueId());
        
        target.sendMessage(
            Colors.SUCCESS.getChatColor() + "Denied teleportation request from " +
            Colors.COMMON.getChatColor() + player.getName() +
            Colors.SUCCESS.getChatColor() + "."
        );
        player.sendMessage(
            Colors.COMMON.getChatColor() + target.getName() + 
            Colors.FAILURE.getChatColor() + " denied your teleportation request."
        );
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
    
}
