package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadCommand extends CommandModule{

    public ReloadCommand(DailyLeaderBoards dailyLeaderBoards) {
        super(dailyLeaderBoards, "reload", 0, 0, Permission.RELOAD);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        Bukkit.getServer().getPluginManager().disablePlugin(plugin);
        Bukkit.getServer().getPluginManager().enablePlugin(plugin);
        return true;
    }

    @Override
    public List<String> tabComplete() {
        return null;
    }
}
