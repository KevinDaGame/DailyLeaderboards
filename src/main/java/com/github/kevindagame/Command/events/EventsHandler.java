package com.github.kevindagame.Command.events;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.TimeFormatter;
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
        if(plugin.getPluginConfig().getAutoRun()){
            load();
        }

    }
    //only called on first plugin load. This method checks if there is an existing event, and if there isnt it creates and starts a new one
    private void load() {

        if (loadExistingEvent()) return;
        createNewRandomEvent();

    }

    private void createNewRandomEvent() {
        Event event = eventsFileHandler.getRandomEvent();
        event = createNewEvent(event);
        startEvent(event);
    }

    //Create a new event. it takes an event from the filehandler
    public Event createNewEvent(Event event) throws UnsupportedOperationException {
        if(currentEvent != null){
            throw new UnsupportedOperationException("There is already an active event!");
        }
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        event.setStartTime(currentDate);
        //Add the hours in config to end date converter
        // d to ms
        Timestamp endDate = new Timestamp(System.currentTimeMillis() + TimeFormatter.deformatTimeRemaining(plugin.getPluginConfig().getEventDuration()));
        System.out.println(endDate);
        event.setEndTime(endDate);
        try {
            event = database.createEvent(event);
            return event;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            plugin.getPluginLoader().disablePlugin(plugin);
            return null;
        }
    }

    public void startEvent(Event event) {
        this.currentEvent = event;
        plugin.getServer().getPluginManager().registerEvents(currentEvent.getListener(), plugin);
        event.startAutoSave(plugin);
        Message.START_EVENT_BROADCAST.broadcast(event.getName());
        scheduleEventEnd(event);
    }

    private void scheduleEventEnd(Event event) {
        var timeToGo = event.getEndTime().getTime() - System.currentTimeMillis();
        if (timeToGo <= 0) {
            endEvent(event);
            return;
        }
        System.out.println(timeToGo + "ms remaining");
        event.setEndTask(Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            endEvent(event);
            createNewRandomEvent();
        }, timeToGo / 50));
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

    public void endCurrentEvent(){
        Bukkit.getScheduler().cancelTask(currentEvent.getEndTask());
        endEvent(currentEvent);
    }

    private void endEvent(Event event) {
        Message.STOP_EVENT_BROADCAST.broadcast(event.getName());
        event.stop();
        database.endEvent(event);
        currentEvent = null;
        //TODO handle rewards here
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void save() {
        currentEvent.save();
    }

    public boolean isRunning() {
        return currentEvent != null;
    }
}
