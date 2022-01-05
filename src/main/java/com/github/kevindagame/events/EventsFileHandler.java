package com.github.kevindagame.events;

import com.github.kevindagame.EventListenerFactory;
import com.github.kevindagame.LeaderBoard;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import java.util.Random;

public class EventsFileHandler {

    private final FileConfiguration file;
    private final EventListenerFactory factory;

    public EventsFileHandler(FileConfiguration file) {
        this.file = file;
        this.factory = new EventListenerFactory();
    }

    public Event getRandomEvent() {
        Random random = new Random();
        var keys = file.getKeys(false).toArray();
        if (keys.length == 0) return null;
        String key = (String) keys[random.nextInt(keys.length)];
        return getEvent(key);
    }

    public Event getEvent(String key) {
        Event event = new Event(key);
        LeaderBoard leaderBoard = new LeaderBoard(event);
        event.setLeaderBoard(leaderBoard);
        Listener listener = factory.getListener(leaderBoard, key, file);
        if (listener == null) return null;
        event.setListener(listener);
        return event;
    }
}
