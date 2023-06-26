package me.fisch37.waypoints.serialization;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.util.UUID;

public class PlayerSerializer implements JsonSerializer<Player>, JsonDeserializer<Player> {

    @Override
    public JsonElement serialize(
            Player player,
            Type type,
            JsonSerializationContext context
    ) {
        JsonObject serialized = new JsonObject();
        serialized.addProperty("name",player.getName());
        serialized.addProperty("uuid",player.getUniqueId().toString());
        return serialized;
    }

    @Override
    public Player deserialize(
            JsonElement jsonElement,
            Type type,
            JsonDeserializationContext context
    ) throws JsonParseException {
        JsonObject interpreted;
        try {
            interpreted = jsonElement.getAsJsonObject();
        } catch (IllegalStateException e){
            throw new JsonParseException("JSON Player is not a JsonObject",e);
        }
        String stringUuid = context.deserialize(interpreted.get("uuid"),String.class);
        if (stringUuid != null){
            return Bukkit.getPlayer(UUID.fromString(stringUuid));
        } else {
            String playerName = context.deserialize(interpreted.get("name"),String.class);
            if (playerName == null){
                throw new JsonParseException("JSON Player has defined neither uuid or name");
            }
            return Bukkit.getPlayerExact(playerName);
        }
    }
}
