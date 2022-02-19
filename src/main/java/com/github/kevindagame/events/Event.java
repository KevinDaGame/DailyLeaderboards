package com.github.kevindagame.events;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Date;
import java.sql.Timestamp;


public class Event {
    private Listener listener;
    private LeaderBoard leaderBoard;
    private Timestamp startTime;
    private Timestamp endTime;
    private int id;
    private final String name;
    private Database database;
    private int saveTask;
    private int endTask;

    public Event(String name) {
        this.name = name;
    }

    public void startAutoSave(JavaPlugin plugin) {
        saveTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            save();
        }, 1200, 1200);
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
//            throw new NullPointerException("The database has failed to load, and this event can therefore not be saved!");
            return;
        }
        database.saveEvent(this);
        DailyLeaderBoards.log("Saving leaderboard...");
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
