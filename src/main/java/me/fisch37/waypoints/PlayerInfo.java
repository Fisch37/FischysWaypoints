package me.fisch37.waypoints;

import java.util.Objects;
import java.util.UUID;

public final class PlayerInfo {
    private final UUID uuid;
    private String name;

    public PlayerInfo(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID uuid() {
        return uuid;
    }

    public String name() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PlayerInfo) obj;
        return Objects.equals(this.uuid(), that.uuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClass(),uuid);
    }

    @Override
    public String toString() {
        return "PlayerInfo[" +
                "uuid=" + uuid + ", " +
                "name=" + name + ']';
    }

}
