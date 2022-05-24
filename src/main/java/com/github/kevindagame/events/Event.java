package com.github.kevindagame.events;

import com.github.kevindagame.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Date;
import java.sql.Timestamp;


public class Event {
    private final String description;
    private Listener listener;
    private LeaderBoard leaderBoard;
    private Timestamp startTime;
    private Timestamp endTime;
    private int id;
    private final String eventTypeSlug;
    private final String name;
    private Database database;
    private int saveTask;
    private int endTask;

    public Event(String slug, String name, String description) {
        this.name = name;
        this.eventTypeSlug = slug;
        this.description = description;
        this.setLeaderBoard(new LeaderBoard(this));
    }

    public void startAutoSave(JavaPlugin plugin) {
        saveTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::save, 1200, 1200);
    }

    public String getDescription() {
        return description;
    }

    public String getEventTypeSlug() {
        return eventTypeSlug;
    }

    public void stopSaving() {
        if (saveTask != 0) Bukkit.getScheduler().cancelTask(saveTask);
    }

    public LeaderBoard getLeaderBoard() {
        return leaderBoard;
    }

    public void setLeaderBoard(LeaderBoard leaderBoard) {
        this.leaderBoard = leaderBoard;
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public boolean shouldBeRunning() {
        return endTime.after(new Date(System.currentTimeMillis()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void save() {
        if (database == null){
            return;
        }
        database.saveEvent(this);
    }

    public void setEndTask(int scheduleSyncDelayedTask) {
        endTask = scheduleSyncDelayedTask;
    }

    public int getEndTask() {
        return endTask;
    }

    public long getTimeRemaining() {
        return endTime.getTime() - System.currentTimeMillis();
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(saveTask);
        save();
        HandlerList.unregisterAll(listener);
    }
}
