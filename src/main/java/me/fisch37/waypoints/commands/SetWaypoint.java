package me.fisch37.waypoints.commands;

import me.fisch37.waypoints.PlayerInfo;
import me.fisch37.waypoints.Waypoint;
import me.fisch37.waypoints.WaypointsMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetWaypoint implements CommandWithHelp, PermissibleCommand{
    private final WaypointsMap waypoints;

    public SetWaypoint(WaypointsMap waypoints){
        this.waypoints = waypoints;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (args.length < 5) {
            sender.sendMessage("Too few arguments (expected 5-6, got " + args.length + ")");
            return false;
        }
        if (args.length > 6) {
            sender.sendMessage("Too many arguments (expected 5-6, got" + args.length + ")");
            return false;
        }
        String name = args[0];
        World world = Bukkit.getWorld(args[1]);
        if (world == null) {
            sender.sendMessage("Could not find any world named \"" + args[1] + "\"");
            return true;
        }
        Location location;
        try {
            location = new Location(
                    world,
                    Double.parseDouble(args[2]),
                    Double.parseDouble(args[3]),
                    Double.parseDouble(args[4])
            );
        } catch (NumberFormatException e) {
            sender.sendMessage(String.format(
                    "Could not parse location \"%s %s %s\". (Did you use , instead of .?)",
                    args[2], args[3], args[4]
            ));
            return true;
        }
        int cost;
        if (args.length == 6) {
            try {
                cost = Integer.parseInt(args[5]);
            } catch (NumberFormatException e) {
                sender.sendMessage("Could not parse cost \"" + args[5] + "\".");
                return true;
            }
        } else cost = 0;

        this.waypoints.put(
                name,
                new Waypoint(
                        location,
                        (PlayerInfo) null,
                        cost
                )
        );
        sender.sendMessage(String.format(
                "Created waypoint \"%s\" at %s/%s,%s,%s",
                (Object[]) args
        ));
        return true;
    }

    @Override
    public List<String> onTabComplete(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        switch (args.length) {
            case 2 -> {
                ArrayList<String> worldNames = new ArrayList<>();
                for (World world : Bukkit.getWorlds()) worldNames.add(world.getName());
                return worldNames;
            }
//            case 3 -> {
//                return getPositionCompletion(sender, 0);
//            }
//            case 4 -> {
//                return getPositionCompletion(sender, 1);
//            }
//            case 5 -> {
//                return getPositionCompletion(sender, 2);
//            }
            default -> {
                return new ArrayList<>();
            }
        }
    }

//    private List<String> getPositionCompletion(CommandSender sender, int posIndex){
//        // posIndex isn't a byte because passing literal bytes would probably cause more CPU lag than 4 bytes of memory make up for
//        List<String> output = new ArrayList<>();
//
//        Location location = null;
//        if (sender instanceof BlockCommandSender blockCommandSender)
//            location = blockCommandSender.getBlock().getLocation();
//        if (sender instanceof Player player) location = player.getLocation();
//        if (location != null) output.add(String.format(
//                "%d ".repeat(3-posIndex),
//                location.getBlockX(),
//                location.getBlockY(),
//                location.getBlockZ()
//        ));
//        return output;
//    }

    @Override
    public String getUsage() {
        return "/waypoints set <name> <world> <x> <y> <z> [<cost>]";
    }

    @Override
    public String getPurpose() {
        return "Create a waypoint at a set position.";
    }

    @Override
    public String getPurposeLong() {
        return "Create a waypoint at a set position. Different to /waypoints add it allows non-players to create waypoints";
    }

    @Override
    public boolean hasPermission(CommandSender sender, Command command, String label, String[] args) {
        return sender.hasPermission("waypoints.set");
    }
}
