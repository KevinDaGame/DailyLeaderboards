package com.github.kevindagame;

public class EventLoadError {
    public void error(String key, String errorText) {
        System.out.println("An error occured while loading the event " + key + ". " + errorText);
    }
}
