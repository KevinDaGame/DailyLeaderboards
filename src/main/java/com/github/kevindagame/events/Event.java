package com.github.kevindagame.events;

import org.bukkit.event.Listener;

public class Event {
    private final Listener listener;

    public Event(Listener listener) {
        this.listener = listener;
    }
}
