package me.fisch37.waypoints.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.*;

public class SubcommandGroup implements TabExecutor {
    public static Map<String,TabExecutor> subcommands;

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (args.length == 0) return false;
        for (String key : subcommands.keySet()){
            if (key.equals(args[0])){
                CommandExecutor commandExecutor = subcommands.get(key);
                boolean commandResult = commandExecutor.onCommand(
                        sender,
                        command,
                        label + " " + key,
                        Arrays.copyOfRange(args,1,args.length)
                );
                if (commandExecutor instanceof  CommandWithHelp){
                    if (!commandResult) sender.sendMessage(((CommandWithHelp) commandExecutor).getUsage());
                    return true;
                }
                return commandResult;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (args.length == 1){
            List<String> possibilities = new ArrayList<>();
            for (String key : subcommands.keySet()){
                if (key.startsWith(args[0])){
                    possibilities.add(key);
                }
            }
            return possibilities;
        }
        for (String key : subcommands.keySet()){
            if (key.equals(args[0])){
                return subcommands.get(key).onTabComplete(
                        sender,
                        command,
                        label + " " + key,
                        Arrays.copyOfRange(args,1,args.length)
                );
            }
        }
        return new ArrayList<>();
    }
}
