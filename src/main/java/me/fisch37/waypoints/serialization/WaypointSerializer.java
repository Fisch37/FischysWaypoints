package me.fisch37.waypoints.serialization;

import com.google.gson.*;
import me.fisch37.waypoints.PlayerInfo;
import me.fisch37.waypoints.Waypoint;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.lang.reflect.Type;

public class WaypointSerializer implements JsonSerializer<Waypoint>, JsonDeserializer<Waypoint> {

    @Override
    public JsonElement serialize(
            Waypoint waypoint,
            Type type,
            JsonSerializationContext context
    ) {
        Location location = waypoint.location();
        double[] position = {location.getX(),location.getY(),location.getZ()};
        JsonObject serialized = new JsonObject();
        serialized.addProperty("world",location.getWorld().getName());
        serialized.addProperty("cost",waypoint.cost());
        serialized.add("position",context.serialize(position));
        serialized.add("author",context.serialize(waypoint.getAuthorInfo()));
        return serialized;
    }

    @Override
    public Waypoint deserialize(
            JsonElement jsonElement,
            Type type,
            JsonDeserializationContext context
    ) throws JsonParseException {
        JsonObject interpreted;
        try{
            interpreted = jsonElement.getAsJsonObject();
        } catch (IllegalStateException e){
            throw new JsonParseException("JSON waypoint is not a JsonObject",e);
        }
        String worldName = interpreted.get("world").getAsString();
        World world = Bukkit.getWorld(worldName);
        if (world == null){
            throw new JsonParseException("Could not find world "+worldName);
        }
        double[] position = context.deserialize(interpreted.get("position"),double[].class);
        int cost = context.deserialize(interpreted.get("cost"),int.class);
        PlayerInfo authorInfo = context.deserialize(interpreted.get("author"), PlayerInfo.class);

        return new Waypoint(
                new Location(world,position[0],position[1],position[2]),
                authorInfo,
                cost
        );
    }
}
