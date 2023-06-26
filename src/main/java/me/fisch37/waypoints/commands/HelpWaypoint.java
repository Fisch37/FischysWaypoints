package me.fisch37.waypoints.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HelpWaypoint implements CommandWithHelp {
    private Map<String,TabExecutor> commands;

    public void setCommands(Map<String,TabExecutor> commands){
        this.commands = commands;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        sender.sendMessage("Help for Fischys Waypoints");
        sender.sendMessage("Arguments wrapped in []-brackets are optional");
        for (Map.Entry<String,TabExecutor> item : this.commands.entrySet()){
            if (item.getValue() instanceof CommandWithHelp subcommandHelp){
                sender.sendMessage(subcommandHelp.getUsage() + ": " + subcommandHelp.getPurpose());
            } else{
                sender.sendMessage("No help defined for subcommand "+item.getKey());
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }

    @Override
    public String getUsage() {
        return "/waypoints help";
    }

    @Override
    public String getPurpose() {
        return "Show this help";
    }

    @Override
    public String getPurposeLong() {
        return this.getPurpose();
    }
}
