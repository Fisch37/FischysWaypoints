package me.fisch37.waypoints.commands;

import me.fisch37.waypoints.Waypoint;
import me.fisch37.waypoints.WaypointsMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ListWaypoints implements CommandWithHelp, PermissibleCommand{
    private final WaypointsMap waypoints;

    public ListWaypoints(WaypointsMap waypoints){
        this.waypoints = waypoints;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        String entryCountOutput = "(" + this.waypoints.size() + " entries)";
        sender.sendMessage("List of Waypoints "+entryCountOutput);
        for (java.util.Map.Entry<String, Waypoint> item : this.waypoints.entrySet()){
            String playerName = item.getValue().getAuthorName();
            if (playerName == null) playerName = "UNKNOWN";
            sender.sendMessage(item.getKey() + " (by "+ playerName + ")");
        }
        sender.sendMessage(entryCountOutput); // Duplicating for really large lists
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }

    @Override
    public String getUsage() {
        return "/waypoints list";
    }

    @Override
    public String getPurpose() {
        return "List all existing waypoints.";
    }

    @Override
    public String getPurposeLong() {
        return this.getPurpose();
    }

    @Override
    public boolean hasPermission(CommandSender sender, Command command, String label, String[] args) {
        return sender.hasPermission("waypoints.list");
    }
}
