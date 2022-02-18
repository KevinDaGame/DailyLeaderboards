package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.Permission;
import com.github.kevindagame.Command.events.Event;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

public class NextEventCommand extends CommandModule {

    public NextEventCommand(DailyLeaderBoards dailyLeaderBoards) {
        super(dailyLeaderBoards, "next", 0, 1, Permission.NEXT);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        if(!dailyLeaderBoards.getPluginConfig().getAutoRun()){
            Message.AUTORUN_DISABLED_ERROR.send(sender);
        }
        var eventsHandler = dailyLeaderBoards.getEventsHandler();
        var eventsFileHandler = dailyLeaderBoards.getEventsFileHandler();
        Event event = null;
        if (args.length == 1) {
            event = eventsFileHandler.getEvent(args[0]);
            if (event == null) {
                Message.INVALID_EVENT_ERROR.send(sender, args[0]);
                return true;
            }
        }
        if (!eventsHandler.isRunning()) {
            Message.NO_CURRENT_EVENT_ERROR.send(sender);
            return true;
        }
        eventsHandler.endCurrentEvent();
        if (event == null) {
            event = eventsFileHandler.getRandomEvent();
        }
        var newEvent = eventsHandler.createNewEvent(event);
        Bukkit.getScheduler().scheduleSyncDelayedTask(dailyLeaderBoards, () -> {
            eventsHandler.startEvent(newEvent);
            //TODO what if server crashes during this time?
        }, 200);
        return true;
    }

    @Override
    public List<String> tabComplete() {
        return dailyLeaderBoards.getEventsFileHandler().getAllEventNames();
    }
}
