package com.github.kevindagame.Command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.Console;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DailyLeaderBoardsCommand implements CommandExecutor, TabCompleter {

    private Map<String, CommandModule> commandModules;

    public DailyLeaderBoardsCommand(Map<String, CommandModule> commandModule) {
        commandModules = commandModule;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run commands dummy");
            return true;
        }
        if (args.length == 0) {
            Bukkit.dispatchCommand(sender, label + " help");
            return true;
        }
        CommandModule commandModule = commandModules.get(args[0].toLowerCase());
        if (commandModule == null) {
            Bukkit.dispatchCommand(sender, label + "help");
            return true;
        }
        args = Arrays.copyOfRange(args, 1, args.length);
        if (sender.hasPermission("DailyLeaderBoards." + label)) {
            if (args.length >= commandModule.getMinArgs() && args.length <= commandModule.getMaxArgs()) {
                return commandModule.run(sender, args);
            }
            sender.sendMessage("Command is incorrect length. idk whether it should be longer or shorter, but you should know dummu");
        } else {
            sender.sendMessage("You don't have perms lol");
        }
        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) return null;
        CommandModule commandModule = commandModules.get(args[0].toLowerCase());
        if (commandModule == null) {
            if(args.length == 1){
                return commandModules.keySet().stream().toList();
            }
        }
        return commandModule.tabComplete();
    }
}
