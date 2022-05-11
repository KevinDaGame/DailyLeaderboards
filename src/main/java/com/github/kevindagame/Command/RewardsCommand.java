package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.Permission;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class RewardsCommand extends CommandModule {

    public RewardsCommand(DailyLeaderBoards dailyLeaderBoards) {
        super(dailyLeaderBoards, Message.COMMAND_REWARDS_LABEL, Message.COMMAND_REWARDS_DESCRIPTION, Message.COMMAND_REWARDS_USAGE, 1, 2, Permission.MANAGE);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        return false;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        var complete = new ArrayList<String>();
        complete.add("add");
        return complete;
    }
}
