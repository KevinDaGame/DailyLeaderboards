package com.github.kevindagame.events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
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
        return getCurrentEvent(key);
    }

    public Event getEvent(String key) {
        return new Event(key);
    }

    public Event getCurrentEvent(String key) {
        Event event = getEvent(key);
        try {
            Listener listener = factory.getListener(event.getLeaderBoard(), key, file);
            if (listener == null) return null;
            event.setListener(listener);
            return event;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getAllEventNames() {
        List<String> list = new ArrayList<>(file.getKeys(false));
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).replace(' ', '-'));
        }
        return list;
    }
}
