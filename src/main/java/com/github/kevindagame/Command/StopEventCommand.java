package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.Permission;
import org.bukkit.command.CommandSender;

import java.util.List;

public class StopEventCommand extends CommandModule {

    public StopEventCommand(DailyLeaderBoards dailyLeaderBoards) {
        super(dailyLeaderBoards, "stop", 0, 0, Permission.STOP);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        plugin.getPluginConfig().disableAutoRun();
        if (plugin.getEventsHandler().isRunning()) {
            plugin.getEventsHandler().endCurrentEvent();
        }
        Message.STOP_EVENT_MESSAGE.send(sender);
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        return null;
    }
}
