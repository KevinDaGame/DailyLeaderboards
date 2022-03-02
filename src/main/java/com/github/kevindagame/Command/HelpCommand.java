package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Permission;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpCommand extends CommandModule {

    public HelpCommand(DailyLeaderBoards dailyLeaderBoards) {
        super(dailyLeaderBoards, "help", 0, 0, Permission.HELP);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        sender.sendMessage("This is the help command!");
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        return null;
    }
}
