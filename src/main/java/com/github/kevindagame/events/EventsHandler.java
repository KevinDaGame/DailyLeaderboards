package com.github.kevindagame.events;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.database.Database;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.sql.Timestamp;

public class EventsHandler {

    private final DailyLeaderBoards plugin;
    private final Database database;
    private final EventsFileHandler eventsFileHandler;
    private Event currentEvent;

    public EventsHandler(DailyLeaderBoards plugin) {
        this.plugin = plugin;
        this.database = plugin.getDataBase();
        this.eventsFileHandler = plugin.getEventsFileHandler();
        loadEvent();

    }

    private void loadEvent() {

        if (loadExistingEvent()) return;

        Event event = eventsFileHandler.getRandomEvent();
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        event.setStartTime(currentDate);
        //Add the hours in config to end date converte
        // d to ms
        Timestamp endDate = new Timestamp(System.currentTimeMillis() + plugin.getPluginConfig().getEventDuration() * 3600000L);
        System.out.println(endDate);
        event.setEndTime(endDate);
        try {
            event = database.createEvent(event);
            startEvent(event);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            plugin.getPluginLoader().disablePlugin(plugin);
        }


    }

    private void startEvent(Event event) {
        this.currentEvent = event;
        plugin.getServer().getPluginManager().registerEvents(currentEvent.getListener(), plugin);
        event.startAutoSave(plugin);
        scheduleEventEnd(event);
    }

    private void scheduleEventEnd(Event event) {
        var timeToGo = event.getEndTime().getTime() - System.currentTimeMillis();
        if (timeToGo <= 0) {
            endEvent(event);
            return;
        }
        System.out.println(timeToGo + "ms remaining");
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> endEvent(event), timeToGo / 50);
    }

    private boolean loadExistingEvent() {
        var runningEvent = database.getRunningEvent();
        try {
            if (runningEvent.next()) {
                Event event = eventsFileHandler.getEvent(runningEvent.getString("name"));
                event.setStartTime(runningEvent.getTimestamp("start_time"));
                event.setEndTime(runningEvent.getTimestamp("end_time"));
                event.setId(runningEvent.getInt("rowid"));
                if (event.shouldBeRunning()) {
                    startEvent(event);
                    return true;
                } else {
                    endEvent(event);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private void endEvent(Event event) {
        Bukkit.broadcastMessage("The event with the name " + event.getName() + " and id " + event.getId() + "has ended");
        event.stopSaving();
        database.endEvent(event);
        //TODO handle rewards here

        loadEvent();
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void save() {
        currentEvent.save();
    }
}
