package me.fisch37.waypoints.commands;

import me.fisch37.waypoints.Waypoint;
import me.fisch37.waypoints.WaypointsMap;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeleteWaypoint implements CommandWithHelp {
    private final WaypointsMap waypoints;

    public DeleteWaypoint(WaypointsMap waypoints){
        this.waypoints = waypoints;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (args.length == 0){
            sender.sendMessage("No waypoint passed");
            return false;
        }
        String waypointName = args[0];
        if (args.length > 1){
            sender.sendMessage("More than one argument supplied. Are you sure you meant waypoint " + waypointName + "?");
            return false;
        }
        if (!this.waypoints.containsKey(args[0])){
            sender.sendMessage("No waypoint found with name " + waypointName);
            return true;
        }
        Waypoint waypoint = this.waypoints.get(waypointName);
        if (!mayDelete(sender,waypoint)){
            sender.sendMessage("You do not have permission to delete this waypoint.");
            return true;
        }
        this.waypoints.remove(waypointName);
        sender.sendMessage("Deleted waypoint " + waypointName + "!");
        try {
            this.waypoints.save();
        } catch (IOException e) {
            sender.sendMessage("Could not save waypoint data and reverting the change. Contact your admin if this persists");
            Bukkit.getLogger().warning("Could not save waypoint deletion of "+waypointName+"!");
            this.waypoints.put(waypointName,waypoint);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (args.length > 1){
            return new ArrayList<>();
        }
        ArrayList<String> availableWaypointNames = new ArrayList<>();
        for (Map.Entry<String,Waypoint> item : this.waypoints.entrySet()){
            if (this.mayDelete(sender,item.getValue())){
                availableWaypointNames.add(item.getKey());
            }
        }
        return availableWaypointNames;
    }

    public boolean mayDelete(CommandSender sender, Waypoint waypoint){
        // TODO: Add permissions system
        return true;
    }

    @Override
    public String getUsage() {
        return "/waypoints delete <waypoint>";
    }

    @Override
    public String getPurpose() {
        return "Delete an existing waypoint.";
    }

    @Override
    public String getPurposeLong() {
        return this.getPurpose();
    }
}
