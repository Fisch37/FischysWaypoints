package me.fisch37.waypoints.commands;

import me.fisch37.waypoints.Waypoint;
import me.fisch37.waypoints.WaypointsMap;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class InfoWaypoint implements CommandWithHelp, PermissibleCommand{
    private final WaypointsMap waypoints;

    public InfoWaypoint(WaypointsMap waypoints){
        this.waypoints = waypoints;
    }

    private String locationStringifier(Location location){
        return String.format(
                "%s/%.3f,%.3f,%.3f",
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ()
        );
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (args.length == 0){
            sender.sendMessage("No waypoint supplied.");
            return false;
        }
        if (!this.waypoints.containsKey(args[0])){
            sender.sendMessage("No waypoint named \"" + args[0] + "\" exists!");
            return true;
        }
        Waypoint waypoint = this.waypoints.get(args[0]);
        sender.sendMessage("Information on Waypoint " + args[0]);
        sender.sendMessage("Level Cost: " + waypoint.cost());
        sender.sendMessage("Created by: " + waypoint.getAuthorName());
        Location location = waypoint.location();
        sender.sendMessage("Leads to: " + this.locationStringifier(location));
        return true;
    }

    @Override
    public List<String> onTabComplete(
            CommandSender sender,
            Command command,
            String label,
            String[] args) {
        return this.waypoints.keySet().stream().toList();
    }

    @Override
    public String getUsage() {
        return "/waypoints info <waypoint>";
    }

    @Override
    public String getPurpose() {
        return "Show information about an existing waypoint";
    }

    @Override
    public String getPurposeLong() {
        return this.getPurpose();
    }

    @Override
    public boolean hasPermission(CommandSender sender, Command command, String label, String[] args) {
        return sender.hasPermission("waypoints.info");
    }
}
