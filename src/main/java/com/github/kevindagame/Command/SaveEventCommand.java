package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.Permission;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SaveEventCommand extends CommandModule{

    public SaveEventCommand(DailyLeaderBoards dailyLeaderBoards) {
        super(dailyLeaderBoards, Message.COMMAND_SAVE_LABEL, Message.COMMAND_SAVE_DESCRIPTION, Message.COMMAND_SAVE_USAGE, 0, 0, Permission.MANAGE);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        if(plugin.getEventsHandler() != null){
            var eventHandler = plugin.getEventsHandler();
            if (eventHandler.getCurrentEvent() != null) {
                eventHandler.save();
                Message.SAVE_EVENT_MESSAGE.send(sender, eventHandler.getCurrentEvent().getName());
            } else {
                Message.NO_CURRENT_EVENT_ERROR.send(sender);
            }
        }
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        return null;
    }
}
