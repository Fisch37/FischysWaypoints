package me.fisch37.waypoints;

import com.google.gson.JsonSyntaxException;
import me.fisch37.waypoints.commands.SubcommandGroup;
import me.fisch37.waypoints.commands.WaypointsCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class FischysWaypoints extends JavaPlugin {
    YamlConfiguration config;
    WaypointsMap waypoints;
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.config = loadConfig();
        try{
            this.waypoints = WaypointsMap.load(new File(getDataFolder(),"waypoints.json"));
        } catch (JsonSyntaxException e){
            Bukkit.getLogger().severe("Could not parse waypoints.json. Waypoints will be disabled!");
            this.getServer().getPluginManager().disablePlugin(this);
        }


        PluginCommand command = this.getCommand("waypoints");
        SubcommandGroup executor = new WaypointsCommand(this.waypoints);
        command.setExecutor(executor);
        command.setTabCompleter(executor);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try{
            this.waypoints.save();
        } catch (java.io.IOException e){
            this.getLogger().warning("Could not save Waypoints configuration!");
        }
    }

    private YamlConfiguration loadConfig(){
        File fileLocation = new File(getDataFolder(), "config.yml");
        if (!fileLocation.exists()) {
            fileLocation.getParentFile().mkdirs();
            saveResource("config.yml",false);
        }
        return YamlConfiguration.loadConfiguration(fileLocation);
    }
}