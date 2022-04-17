package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.Permission;
import com.github.kevindagame.events.Event;
import org.bukkit.command.CommandSender;

import java.util.List;

public class StartEventCommand extends CommandModule{

    public StartEventCommand(DailyLeaderBoards dailyLeaderBoards) {
        super(dailyLeaderBoards, "start", 0, 1, Permission.START);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        var eventsHandler = plugin.getEventsHandler();
        var eventsFileHandler = plugin.getEventsFileHandler();
        plugin.getPluginConfig().enableAutoRun();
        Event event = null;
        if (args.length == 1) {
            args[0] = args[0].replace('-', ' ');
            event = eventsFileHandler.getCurrentEvent(args[0]);
            if (event == null) {
                Message.INVALID_EVENT_ERROR.send(sender, args[0]);
                return true;
            }
        }
        if (eventsHandler.isRunning()) {
            Message.ALREADY_EVENT_RUNNING_ERROR.send(sender);
            return true;
        }
        if (event == null) {
            event = eventsFileHandler.getRandomEvent();
        }
        var newEvent = eventsHandler.createNewEvent(event);
        eventsHandler.startEvent(newEvent);
        return true;
    }

    @Override
    public List<String> tabComplete() {
        return plugin.getEventsFileHandler().getAllEventNames();
    }
}
