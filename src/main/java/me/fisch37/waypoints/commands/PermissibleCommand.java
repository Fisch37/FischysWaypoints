package me.fisch37.waypoints.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface PermissibleCommand {
    boolean hasPermission(CommandSender sender, Command command, String label, String[] args);
}
