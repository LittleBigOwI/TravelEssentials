package net.philocraft.errors;

import org.bukkit.command.CommandSender;

import net.philocraft.constants.Colors;

public class PlayerNotFoundException {
    
    private String cause;

    public PlayerNotFoundException() {
        this.cause = Colors.FAILURE.getChatColor() + "Could not find player.";
    }

    public PlayerNotFoundException(String cause) {
        this.cause = Colors.FAILURE.getChatColor() + cause;
    }

    public boolean sendCause(CommandSender sender) {
        sender.sendMessage(this.cause);
        return true;
    }

}
