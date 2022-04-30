package com.github.kevindagame.events;

import org.bukkit.Bukkit;

public class EventLoadError {
    public void error(String key, String errorText) {
        Bukkit.getLogger().warning("An error occured while loading the event " + key + ". " + errorText);
    }
}
