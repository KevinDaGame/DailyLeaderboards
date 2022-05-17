package com.github.kevindagame;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.naming.ConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    public String getEventDuration() {
        return reader.getString("event-duration");
    }
    public int getSavedLeaderboards() {
        return reader.getInt("saved-leaderboards");

    }
    public Map<Integer, String> getRewards() {
        Map<Integer, String> rewards = new HashMap<>();
        var value = reader.getConfigurationSection("rewards");
        if(value == null) return null;
        for(var key : value.getKeys(false)) {
            rewards.put(Integer.parseInt(key), value.getString(key + ".command"));
        }
        return rewards;
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
