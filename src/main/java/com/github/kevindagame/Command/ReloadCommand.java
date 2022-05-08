package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadCommand extends CommandModule{

    public ReloadCommand(DailyLeaderBoards dailyLeaderBoards) {
        super(dailyLeaderBoards, Message.COMMAND_RELOAD_LABEL, Message.COMMAND_RELOAD_DESCRIPTION, Message.COMMAND_RELOAD_USAGE, 0, 0, Permission.RELOAD);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        Bukkit.getServer().getPluginManager().disablePlugin(plugin);
        Bukkit.getServer().getPluginManager().enablePlugin(plugin);
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        return null;
    }
}
