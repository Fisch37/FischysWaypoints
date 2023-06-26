package me.fisch37.waypoints;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public record Waypoint (
        Location location,
        PlayerInfo authorInfo,
        int cost
) {

    public Waypoint(
            Location location,
            int cost,
            String authorName,
            UUID authorUUID
    ) {
        this(
                location,
                new PlayerInfo(authorUUID,authorName),
                cost
        );
    }

    public Waypoint(Location location, Player author, int cost) {
        this(
                location,
                new PlayerInfo(author.getUniqueId(),author.getName()),
                cost
        );
    }

    /*
     * Will be null if the author is not online
     */
    public Player getAuthor(){
        return Bukkit.getPlayer(this.getAuthorUuid());
    }

    public boolean hasAuthorInfo(){
        return this.authorInfo() != null;
    }

    public PlayerInfo getAuthorInfo(){
        return this.authorInfo();
    }

    public UUID getAuthorUuid(){
        if (!this.hasAuthorInfo()) return null;
        return this.getAuthorInfo().uuid();
    }

    /*
     * Gets the author's name
     * This data may be invalid. If you want up-to-date info, call updateAuthorName instead
     */
    public String getAuthorName(){
        if (!this.hasAuthorInfo()) return null;
        return this.getAuthorInfo().name();
    }

    public String setAuthorName(String name){
        if (!this.hasAuthorInfo()) throw new IllegalStateException("This waypoint does not have any author info");
        this.getAuthorInfo().setName(name);
        return name;
    }

    public String updateAuthorName(){
        Player author = Bukkit.getPlayer(this.getAuthorUuid());
        if (author != null) this.setAuthorName(author.getName());
        return this.getAuthorName();
    }
}
