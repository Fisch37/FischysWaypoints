package me.fisch37.waypoints.serialization;

import com.google.gson.*;
import me.fisch37.waypoints.PlayerInfo;

import java.lang.reflect.Type;
import java.util.UUID;

public class PlayerInfoSerializer implements JsonSerializer<PlayerInfo>, JsonDeserializer<PlayerInfo> {
    @Override
    public PlayerInfo deserialize(
            JsonElement jsonElement,
            Type type,
            JsonDeserializationContext context
    ) throws JsonParseException {
        if (!jsonElement.isJsonObject()){
            throw new JsonParseException("Author field is not of type JsonObject");
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement jsonName = jsonObject.get("name");
        if (jsonName == null) throw new JsonParseException("Author object has no property name");
        String name;
        try{
            name = jsonName.getAsString();
        } catch (UnsupportedOperationException | IllegalStateException e){
            throw new JsonParseException("Author property name cannot be interpreted as string",e);
        }

        JsonElement jsonUuid = jsonObject.get("uuid");
        if (jsonUuid == null) throw new JsonParseException("Author object has no property uuid");
        UUID uuid;
        try {
            String uuidString = jsonUuid.getAsString();
            uuid = UUID.fromString(uuidString);
        } catch (IllegalArgumentException e){
            throw new JsonParseException("Author property uuid is not a valid Gen-4 UUID");
        } catch (UnsupportedOperationException | IllegalStateException e) {
            throw new JsonParseException("Author property uuid cannot be interpreted as string", e);
        }
        return new PlayerInfo(uuid,name);
    }

    @Override
    public JsonElement serialize(
            PlayerInfo playerInfo,
            Type type,
            JsonSerializationContext context
    ) {
        JsonObject serialized = new JsonObject();
        serialized.addProperty("name",playerInfo.name());
        serialized.addProperty("uuid",playerInfo.uuid().toString());
        return serialized;
    }
}
