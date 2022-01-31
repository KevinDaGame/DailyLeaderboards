package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.Permission;
import org.bukkit.command.CommandSender;

import java.util.List;

public class StatusEventCommand extends CommandModule{
    public StatusEventCommand(DailyLeaderBoards dailyLeaderBoards) {
        super(dailyLeaderBoards, "status", 0, 0, Permission.STATUS);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        var event = dailyLeaderBoards.getEventsHandler().getCurrentEvent();

        if(event == null){
            Message.NO_CURRENT_EVENT_ERROR.send(sender);
            return true;
        }
        Message.STATUS_EVENT_MESSAGE.send(sender, event.getName(), "placeholder");
        return true;

    }

    @Override
    public List<String> tabComplete() {
        return null;
    }
}
