package com.github.kevindagame;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PluginConfig {
    private final String tableName;
    private final String databaseName;

    public PluginConfig(File configFile) {
        FileConfiguration reader = new YamlConfiguration();
        try {
            reader.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        this.tableName = reader.getString("table-name");
        this.databaseName = reader.getString("database-name");

    }

    public String getTableName() {
        return tableName;
    }

    public String getDatabaseName() {
        return databaseName;
    }
}
