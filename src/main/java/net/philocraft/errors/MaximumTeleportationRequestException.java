package net.philocraft.errors;

import org.bukkit.command.CommandSender;

import net.philocraft.constants.Colors;

public class MaximumTeleportationRequestException {
    
    private String cause;

    public MaximumTeleportationRequestException() {
        this.cause = Colors.FAILURE.getChatColor() + "You have reached the maximum amount of teleportation requests.";
    }

    public MaximumTeleportationRequestException(String cause) {
        this.cause = Colors.FAILURE.getChatColor() + cause;
    }

    public boolean sendCause(CommandSender sender) {
        sender.sendMessage(this.cause);
        return true;
    }

}
