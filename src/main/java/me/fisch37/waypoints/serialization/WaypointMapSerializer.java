package me.fisch37.waypoints.serialization;

import com.google.gson.*;
import me.fisch37.waypoints.Waypoint;
import me.fisch37.waypoints.WaypointsMap;

import java.lang.reflect.Type;

public class WaypointMapSerializer implements JsonSerializer<WaypointsMap> {

    @Override
    public JsonElement serialize(
            WaypointsMap map,
            Type type,
            JsonSerializationContext context
    ) {
        JsonObject serialized = new JsonObject();
        for (java.util.Map.Entry<String, Waypoint> item : map.entrySet()){
            serialized.add(item.getKey(),context.serialize(item.getValue()));
        }
        return serialized;
    }
}
