package me.fisch37.waypoints.commands;

import me.fisch37.waypoints.Waypoint;
import me.fisch37.waypoints.WaypointsMap;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddWaypoint implements CommandWithHelp {
    private final WaypointsMap waypoints;

    public AddWaypoint(WaypointsMap waypoints){
        this.waypoints = waypoints;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (!(sender instanceof Player player)){
            sender.sendMessage("/waypoints add can only be executed by players");
            return true; // Invalid execution, but should not print usage
        }
        if (args.length == 0){
            sender.sendMessage("No waypoint name specified");
            return false;
        }
        String name = args[0];
        int cost;
        try {
            cost = (args.length == 2) ? Integer.parseInt(args[1]) : 0;
        } catch (NumberFormatException e){
            sender.sendMessage("Waypoint cost is not a whole number! Did you try to add spaces to the waypoint name?");
            return true;
        }
        this.waypoints.put(
                name,
                new Waypoint(player.getLocation(),player,cost)
        );
        try{
            this.waypoints.save();
        } catch (IOException e){
            sender.sendMessage("The waypoint could not be saved. Contact your admin if this persists.");
            Bukkit.getLogger().warning("Could not save waypoint \""+name+"\"");
            return true;
        }
        sender.sendMessage("Added waypoint \""+name+"\"!");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }

    @Override
    public String getUsage() {
        return "/waypoints add <waypoint> [<cost>]";
    }

    @Override
    public String getPurpose() {
        return "Creates a waypoint at your current position.";
    }

    @Override
    public String getPurposeLong() {
        return this.getPurpose();
    }
}
