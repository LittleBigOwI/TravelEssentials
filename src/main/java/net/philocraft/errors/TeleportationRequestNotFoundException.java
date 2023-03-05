package net.philocraft.errors;

import org.bukkit.command.CommandSender;

import net.philocraft.constants.Colors;

public class TeleportationRequestNotFoundException {
    
    private String cause;

    public TeleportationRequestNotFoundException() {
        this.cause = Colors.FAILURE.getChatColor() + "Could not find a matching teleportaion request.";
    }

    public TeleportationRequestNotFoundException(String cause) {
        this.cause = Colors.FAILURE.getChatColor() + cause;
    }

    public boolean sendCause(CommandSender sender) {
        sender.sendMessage(this.cause);
        return true;
    }

}
