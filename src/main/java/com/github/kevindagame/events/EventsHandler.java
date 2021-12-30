package com.github.kevindagame.events;

import com.github.kevindagame.DailyLeaderBoards;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class EventsHandler {

    private final DailyLeaderBoards plugin;
    private Event currentEvent;

    public EventsHandler(DailyLeaderBoards plugin) {
        this.plugin = plugin;
        loadEvent();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            System.out.println("Saving leaderboard...");
            save();
        }, 1200, 1200);
    }

    private void loadEvent() {
        if(currentEvent != null){
            HandlerList.unregisterAll(plugin);
        }
        currentEvent = plugin.getEventsFileHandler().getRandomEvent();
        System.out.println("Current event is " + currentEvent.getLeaderBoard().getName());
        plugin.getServer().getPluginManager().registerEvents(currentEvent.getListener(), plugin);
    }

    public Event getCurrentEvent(){
        return currentEvent;
    }

    public void save(){
        plugin.getDataBase().saveEvent(currentEvent);
    }
}
