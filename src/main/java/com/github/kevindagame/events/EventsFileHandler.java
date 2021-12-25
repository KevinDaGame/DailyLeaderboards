package com.github.kevindagame.events;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;

public class EventsFileHandler {

    private final FileConfiguration file;
    private ArrayList<Event> events;

    public EventsFileHandler(FileConfiguration file) {
        this.file = file;
        events = deserialize();
    }

    private ArrayList<Event> deserialize() {
        ArrayList<Event> list = new ArrayList<>();
        for (String key: file.getKeys(false)) {
            Listener listener = getListener(file.getString(key + ".event-type"));
            list.add(new Event(listener));
        }
        return list;

    }

    private Listener getListener(String s) {
        System.out.println(s);
        return null;
    }
}
