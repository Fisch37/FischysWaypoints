package me.fisch37.waypoints.commands;

import me.fisch37.waypoints.WaypointsMap;

import java.util.Map;
import java.util.TreeMap;

public class WaypointsCommand extends SubcommandGroup {
    private final WaypointsMap waypoints;

    public WaypointsCommand(WaypointsMap waypoints){
        this.waypoints = waypoints;
        // Using a TreeMap ensures ordering for /wps help
        subcommands = new TreeMap<>(Map.of(
                "list", new ListWaypoints(waypoints),
                "add", new AddWaypoint(waypoints),
                "delete", new DeleteWaypoint(waypoints),
                "go", new GoWaypoint(waypoints),
                "info", new InfoWaypoint(waypoints),
                "help", new HelpWaypoint()
        ));
        ((HelpWaypoint) subcommands.get("help")).setCommands(subcommands);
    }
}
