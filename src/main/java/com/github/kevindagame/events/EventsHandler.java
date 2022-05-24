package com.github.kevindagame.events;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.TimeFormatter;
import com.github.kevindagame.database.Database;
import com.github.kevindagame.rewards.RewardsHandler;
import org.bukkit.Bukkit;

import javax.naming.ConfigurationException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventsHandler {

    private final DailyLeaderBoards plugin;
    private final Database database;
    private final EventsFileHandler eventsFileHandler;
    private Event currentEvent;
    private final List<Event> pastEvents;

    public EventsHandler(DailyLeaderBoards plugin) {
        this.plugin = plugin;
        this.database = plugin.getDataBase();
        this.eventsFileHandler = plugin.getEventsFileHandler();
        this.pastEvents = new ArrayList<>();
        load();

    }
    //only called on first plugin load. This method checks if there is an existing event, and if there isn't, it creates and starts a new one
    private void load() {
        loadEvents();
        if (loadRunningEvent()) return;
        if(plugin.getPluginConfig().getAutoRun()){
            createNewRandomEvent();

        }

    }

    private void loadEvents() {
        DailyLeaderBoards.log("Loading previous events!");
        var events = database.getEvents(plugin.getPluginConfig().getSavedLeaderboards());
        try {
            while (events.next()) {
                Event event = eventsFileHandler.getEvent(events.getString("type_slug"));
                PopulateEvent(events, event);
                pastEvents.add(event);
            }
        } catch (SQLException | ConfigurationException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createNewRandomEvent() {
        Event event = eventsFileHandler.getRandomEvent();
        event = createNewEvent(event);
        startEvent(event);
    }

    //Create a new event. it takes an event from the file handler
    public Event createNewEvent(Event event) throws UnsupportedOperationException {
        if(currentEvent != null){
            throw new UnsupportedOperationException("There is already an active event!");
        }
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        event.setStartTime(currentDate);
        //Add the hours in config to end date converter
        // d to ms
        Timestamp endDate = new Timestamp(System.currentTimeMillis() + TimeFormatter.deformatTimeRemaining(plugin.getPluginConfig().getEventDuration()));
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
        event.setEndTask(Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            endEvent(event);
            createNewRandomEvent();
        }, timeToGo / 50));
    }

    private boolean loadRunningEvent() {
        var runningEvent = database.getRunningEvent();
        try {
            if (runningEvent.next()) {
                Event event = eventsFileHandler.getCurrentEvent(runningEvent.getString("type_slug"));
                if(event == null){
                    database.forceEndEvent(runningEvent.getInt("rowid"));
                    DailyLeaderBoards.log("Stopping event " + runningEvent.getString("type_slug") + " because it is not in the events file!");
                    return false;
                }
                event.setDatabase(database);
                PopulateEvent(runningEvent, event);
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

    private void PopulateEvent(ResultSet eventData, Event event) throws SQLException {
        event.setStartTime(eventData.getTimestamp("start_time"));
        event.setEndTime(eventData.getTimestamp("end_time"));
        event.setId(eventData.getInt("rowid"));
        var leaderboard = database.getLeaderBoard(event.getId());
        while(leaderboard.next()){
            event.getLeaderBoard().addScore(Bukkit.getOfflinePlayer(UUID.fromString(leaderboard.getString("UUID"))), leaderboard.getInt("score"));
        }
    }

    public void endCurrentEvent(){
        Bukkit.getScheduler().cancelTask(currentEvent.getEndTask());
        endEvent(currentEvent);
    }

    private void endEvent(Event event) {
        Message.STOP_EVENT_BROADCAST.broadcast(event.getName());
        event.stop();
        addEventToPastEvents(event);
        database.endEvent(event);
        currentEvent = null;
        RewardsHandler.addRewards(event);
    }
    private void addEventToPastEvents(Event event){
        pastEvents.add(0, event);
        if(pastEvents.size() > plugin.getPluginConfig().getSavedLeaderboards()){
            pastEvents.remove(pastEvents.size() - 1);
        }
    }
    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void save() {
        if(currentEvent != null){
            currentEvent.save();
        }
    }

    public boolean isRunning() {
        return currentEvent != null;
    }

    public Event getEvent(int id) {
        if(id == 0){
            return currentEvent;
        }
        else if(id < pastEvents.size() + 1){
            return pastEvents.get(id - 1);
        }
        return null;
    }
}
