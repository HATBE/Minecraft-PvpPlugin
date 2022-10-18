package ch.hatbe2113.pvpminigame.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PvpadminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        sender.sendMessage("you executed pvpadmin");
        return true;
    }
}
