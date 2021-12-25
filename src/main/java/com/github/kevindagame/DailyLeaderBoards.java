package com.github.kevindagame;

import com.github.kevindagame.events.EventsFileHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class DailyLeaderBoards extends JavaPlugin {
    private EventsFileHandler eventsFileHandler;

    @Override
    public void onEnable() {
        File eventsFile = new File(getDataFolder(), "events.yml");
        if (!eventsFile.exists()) saveResource(eventsFile.getName(), false);
        FileConfiguration events = YamlConfiguration.loadConfiguration(eventsFile);
        eventsFileHandler = new EventsFileHandler(events);
    }
}
