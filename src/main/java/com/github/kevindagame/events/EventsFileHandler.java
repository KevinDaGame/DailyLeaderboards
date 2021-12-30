package com.github.kevindagame.events;

import com.github.kevindagame.EventListenerFactory;
import com.github.kevindagame.LeaderBoard;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class EventsFileHandler {

    private final FileConfiguration file;
    private final EventListenerFactory factory;
    public EventsFileHandler(FileConfiguration file) {
        this.file = file;
        this.factory = new EventListenerFactory();
    }

    private ArrayList<Event> deserialize() {
        ArrayList<Event> list = new ArrayList<>();
        EventListenerFactory factory = new EventListenerFactory();
        for (String key : file.getKeys(false)) {
            LeaderBoard leaderBoard = new LeaderBoard(key);
            Listener listener = factory.getListener(leaderBoard, key, file);
            if (listener == null) break;
            list.add(new Event(listener, leaderBoard));
        }
        return list;
    }

    public Event getRandomEvent() {
        Random random = new Random();
        var keys = file.getKeys(false).toArray();
        if (keys.length == 0) return null;
        var key = (String) keys[random.nextInt(keys.length)];
        LeaderBoard leaderBoard = new LeaderBoard(key);
        Listener listener = factory.getListener(leaderBoard, key, file);
        if (listener == null) return null;
        return new Event(listener, leaderBoard);
    }
}
