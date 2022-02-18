package com.github.kevindagame;

import com.github.kevindagame.Command.CommandModule;
import com.github.kevindagame.Command.CommandModuleFactory;
import com.github.kevindagame.Command.DailyLeaderBoardsCommand;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.database.Database;
import com.github.kevindagame.database.SQLite;
import com.github.kevindagame.Command.events.EventsFileHandler;
import com.github.kevindagame.Command.events.EventsHandler;
import com.github.kevindagame.placeholders.DailyLeaderBoardsExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class DailyLeaderBoards extends JavaPlugin {
    private EventsFileHandler eventsFileHandler;
    private PluginConfig config;
    private Database db;
    private EventsHandler eventsHandler;
    public static DailyLeaderBoards plugin;

    public static HashMap<String, CommandModule> commands;

    @Override
    public void onEnable() {
        plugin = this;
        Message.load();
        plugin.getCommand("dailyleaderboards").setExecutor(new DailyLeaderBoardsCommand(CommandModuleFactory.getCommandModules(this)));
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&cFound PAPI UWU"));
            new DailyLeaderBoardsExpansion(this).register();
        }
        else{
            System.out.println(ChatColor.translateAlternateColorCodes('&', "&cDidn't find PAPI UWU"));
        }
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

    public static void log(String message) {
        plugin.getLogger().info(message);
    }

    @Override
    public void onDisable() {
        if (eventsHandler != null) {
            eventsHandler.save();
        }
        plugin = null;
        if (commands != null) {
            commands.clear();
        }
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
}
