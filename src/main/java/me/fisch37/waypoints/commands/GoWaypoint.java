package me.fisch37.waypoints.commands;

import me.fisch37.waypoints.Waypoint;
import me.fisch37.waypoints.WaypointsMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class GoWaypoint implements CommandWithHelp, PermissibleCommand {
    private final WaypointsMap waypoints;

    public GoWaypoint(WaypointsMap waypoints){
        this.waypoints = waypoints;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args) {
        if (!(sender instanceof Player player)){
            sender.sendMessage("/waypoints go only works for players!");
            return true;
        }
        if (args.length == 0){
            sender.sendMessage("No waypoint specified");
            return false;
        }
        String waypointName = args[0];
        if (args.length > 1){
            sender.sendMessage("Command call specified more than one argument. Are you sure you want to teleport to " + waypointName + "?");
            return true;
        }
        if (!this.waypoints.containsKey(waypointName)){
            sender.sendMessage("No waypoint named \"" + waypointName + "\" exists!");
            return true;
        }
        Waypoint waypoint = this.waypoints.get(waypointName);
        if (player.getLevel() < waypoint.cost()){
            sender.sendMessage("Insufficient levels for this waypoint! (Requires " + waypoint.cost() + ")");
            return true;
        }
        player.teleport(waypoint.location());
        player.setLevel(player.getLevel()- waypoint.cost());
        sender.sendMessage("Welcome to " + waypointName + "!");
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
        return "/waypoints go <waypoint>";
    }

    @Override
    public String getPurpose() {
        return "Teleport to an existing waypoint.";
    }

    @Override
    public String getPurposeLong(){
        return "Teleport to an existing waypoint. This may reduce your levels.";
    }

    @Override
    public boolean hasPermission(CommandSender sender, Command command, String label, String[] args) {
        return sender.hasPermission("waypoints.go");
    }
}
