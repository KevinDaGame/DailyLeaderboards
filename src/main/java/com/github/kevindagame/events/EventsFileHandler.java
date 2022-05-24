package com.github.kevindagame.events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import javax.naming.ConfigurationException;
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

    public Event getEvent(String slug) throws ConfigurationException {
        var key = file.getConfigurationSection(slug);
        if ( key == null) throw new ConfigurationException("Event with slug " + slug + " does not exist");
        var name = key.getString("event-name");
        if(name == null) throw new ConfigurationException("Event with slug " + slug + " does not have a name");
        var description = key.getString("event-description");
        if(description == null) throw new ConfigurationException("Event with slug " + slug + " does not have a description");
        return new Event(slug, name, description);
    }

    public Event getCurrentEvent(String key) {
        Event event;
        try {
            event = getEvent(key);
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
        list.replaceAll(s -> s.replace(' ', '-'));
        return list;
    }
}
