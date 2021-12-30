package com.github.kevindagame;

import com.github.kevindagame.database.Database;
import com.github.kevindagame.database.SQLite;
import com.github.kevindagame.events.Event;
import com.github.kevindagame.events.EventsFileHandler;
import com.github.kevindagame.events.EventsHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class DailyLeaderBoards extends JavaPlugin {
    private EventsFileHandler eventsFileHandler;
    private PluginConfig config;
    private Database db;
    private EventsHandler eventsHandler;
    @Override
    public void onEnable() {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) saveResource(configFile.getName(), false);
        config = new PluginConfig(configFile);

        File eventsFile = new File(getDataFolder(), "events.yml");
        if (!eventsFile.exists()) saveResource(eventsFile.getName(), false);
        FileConfiguration events = YamlConfiguration.loadConfiguration(eventsFile);
        eventsFileHandler = new EventsFileHandler(events);
        this.db = new SQLite(this);
        this.db.load();
        eventsHandler = new EventsHandler(this);
    }

    @Override
    public void onDisable(){
        eventsHandler.save();
    }

    public PluginConfig getPluginConfig() {
        return config;
    }

    public Database getDataBase() {
        return db;
    }

    public EventsFileHandler getEventsFileHandler() {
        return eventsFileHandler;
    }
}
