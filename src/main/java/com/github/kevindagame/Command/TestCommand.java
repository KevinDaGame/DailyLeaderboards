package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.Permission;
import org.bukkit.command.CommandSender;

import java.util.List;

public class TestCommand extends CommandModule{

    public TestCommand(DailyLeaderBoards dailyLeaderBoards) {
        super(dailyLeaderBoards, "test", 0, 0, Permission.DEBUG);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        Message.TEST_MESSAGE.send(sender, sender.getName());
        Message.TEST2_MESSAGE.send(sender, sender.getName());
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        return null;
    }
}
