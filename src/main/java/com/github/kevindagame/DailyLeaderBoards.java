package com.github.kevindagame;

import com.github.kevindagame.Command.CommandModule;
import com.github.kevindagame.Command.CommandModuleFactory;
import com.github.kevindagame.Command.DailyLeaderBoardsCommand;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.database.Database;
import com.github.kevindagame.database.SQLite;
import com.github.kevindagame.events.EventsFileHandler;
import com.github.kevindagame.events.EventsHandler;
import com.github.kevindagame.placeholders.DailyLeaderBoardsExpansion;
import com.github.kevindagame.rewards.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;

public class DailyLeaderBoards extends JavaPlugin {
    public static DailyLeaderBoards plugin;
    private EventsFileHandler eventsFileHandler;
    private PluginConfig config;
    private Database db;
    private EventsHandler eventsHandler;
    private DailyLeaderBoardsCommand commandHandler;

    @Override
    public void onEnable() {
        plugin = this;
        Message.load();
        commandHandler = new DailyLeaderBoardsCommand(CommandModuleFactory.getCommandModules(this));
        plugin.getCommand("dailyleaderboards").setExecutor(commandHandler);
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new DailyLeaderBoardsExpansion(this).register();
            DailyLeaderBoards.log("Succesfully loaded placeholders");
        }
        loadConfig();
        loadDatabase();
        loadEvents();
        loadRewards();
    }

    private void loadRewards() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    @Override
    public void onDisable() {
        unloadEventsHandler();
        unloadDatabase();
        plugin = null;
    }

    public DailyLeaderBoardsCommand getCommandHandler() {
        return commandHandler;
    }

    private void unloadEventsHandler() {
        if (eventsHandler != null) {
            eventsHandler.save();
        }
    }

    private void unloadDatabase() {
        try {
            if (getDataBase() != null) {
                getDataBase().getSQLConnection().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadEvents() {
        File eventsFile = new File(getDataFolder(), "events.yml");
        if (!eventsFile.exists()) saveResource(eventsFile.getName(), false);
        FileConfiguration events = YamlConfiguration.loadConfiguration(eventsFile);
        eventsFileHandler = new EventsFileHandler(events);
        eventsHandler = new EventsHandler(this);
    }

    private void loadDatabase() {
        this.db = new SQLite(this);
        this.db.load();
    }

    private void loadConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) saveResource(configFile.getName(), false);
        config = new PluginConfig(configFile);
    }

    public static void log(String message) {
        plugin.getLogger().info(message);
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

    public EventsHandler getEventsHandler() {
        return eventsHandler;
    }

    public void reloadLang() {
        Message.load();
    }
}
