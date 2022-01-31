package com.github.kevindagame;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PluginConfig {
    private final FileConfiguration reader;
    private final File configFile;
    public PluginConfig(File configFile) {
        reader = new YamlConfiguration();
        this.configFile = configFile;

        try {
            reader.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String getTableName() {
        return reader.getString("table-name");
    }

    public String getDatabaseName() {
        return reader.getString("database-name");
    }

    public int getEventDuration() {
        return reader.getInt("event-duration");
    }

    public void disableAutoRun() {
        reader.set("auto-run", false);
        save();
    }
    private void save(){
        try {
            reader.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void enableAutoRun() {
        reader.set("auto-run", true);
        save();
    }

    public boolean getAutoRun(){
        return reader.getBoolean("auto-run");
    }
}
