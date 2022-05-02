package com.github.kevindagame.Command;

import com.github.kevindagame.Lang.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DailyLeaderBoardsCommand implements CommandExecutor, TabCompleter {

    private final Map<String, CommandModule> commandModules;

    public DailyLeaderBoardsCommand(Map<String, CommandModule> commandModule) {
        commandModules = commandModule;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
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
        if (!sender.hasPermission(commandModule.getPermission().getPermission())) {
            Message.NO_PERMISSION_ERROR.send(sender);
            return true;
        }
        if (args.length > commandModule.getMaxArgs())
            Message.TOO_MANY_ARGUMENTS_ERROR.send(sender, commandModule.getMaxArgs(), args.length);
        else if (args.length < commandModule.getMinArgs())
            Message.TOO_FEW_ARGUMENTS_ERROR.send(sender, commandModule.getMinArgs(), args.length);
        else return commandModule.run(sender, args);

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 0) return null;
        CommandModule commandModule = commandModules.get(args[0].toLowerCase());
        if (commandModule == null) {
            if (args.length == 1) {
                return commandModules.keySet().stream().toList();
            }
            return null;
        }
        return commandModule.tabComplete();
    }

    public Map<String, CommandModule> getCommandModules() {
        return commandModules;
    }
}
