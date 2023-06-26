package me.fisch37.waypoints;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import me.fisch37.waypoints.serialization.PlayerInfoSerializer;
import me.fisch37.waypoints.serialization.WaypointMapSerializer;
import me.fisch37.waypoints.serialization.WaypointSerializer;
import org.bukkit.Bukkit;

import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;

public class WaypointsMap extends java.util.TreeMap<String,Waypoint> {
    private File location;
    private final static Gson gson = (new GsonBuilder())
            .registerTypeHierarchyAdapter(PlayerInfo.class, new PlayerInfoSerializer())
            .registerTypeAdapter(Waypoint.class, new WaypointSerializer())
            .registerTypeHierarchyAdapter(WaypointsMap.class,new WaypointMapSerializer())
            .setPrettyPrinting()
            .create();
    public static WaypointsMap load(File location) throws JsonSyntaxException {
        WaypointsMap result;
        try (FileReader reader = new FileReader(location)) {
            JsonReader jsonReader = new JsonReader(reader);
            result = gson.fromJson(jsonReader,WaypointsMap.class);
        } catch (java.io.FileNotFoundException e){
            result = null;
        } catch (java.io.IOException e){
            Bukkit.getLogger().warning("Waypoints file could not be closed");
            result = null;
        }
        if (result == null) result = new WaypointsMap();
        result.setLocation(location);
        return result;
    }

    public void save() throws java.io.IOException{
        try (FileWriter writer = new FileWriter(this.location)){
            // Buffering serialized json is space inefficient, but fails safely
            String fileContents = gson.toJson(this);
            writer.write(fileContents);
        }
    }

    public void setLocation(File location) {
        this.location = location;
    }
    public File getLocation(){
        return this.location;
    }
}
