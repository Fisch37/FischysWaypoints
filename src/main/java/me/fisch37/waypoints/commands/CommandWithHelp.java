package me.fisch37.waypoints.commands;

import org.bukkit.command.TabExecutor;

public interface CommandWithHelp extends TabExecutor {
    public String getUsage();

    public String getPurpose();

    public String getPurposeLong();
}
