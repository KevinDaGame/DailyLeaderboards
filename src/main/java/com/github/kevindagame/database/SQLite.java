package com.github.kevindagame.database;

import com.github.kevindagame.DailyLeaderBoards;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class SQLite extends Database {
    public final String SQLiteCreateTable;
    final String dbname;

    public SQLite(DailyLeaderBoards instance) {
        super(instance);
        dbname = plugin.getPluginConfig().getDatabaseName();
        table = plugin.getPluginConfig().getTableName();
        SQLiteCreateTable = """
                CREATE TABLE IF NOT EXISTS event (
                  'type_slug' INT NOT NULL,
                  `start_time` DATETIME NOT NULL,
                  `end_time` DATETIME NOT NULL,
                  'is_running' INT NOT NULL
                  );


                CREATE TABLE IF NOT EXISTS score (
                  `event_id` INT NOT NULL,
                  `UUID` VARCHAR(32) NOT NULL,
                  `score` INT NOT NULL,
                  PRIMARY KEY (`event_id`, `UUID`),
                  CONSTRAINT `fk_score_event`
                    FOREIGN KEY (`event_id`)
                    REFERENCES `event` (`id`)
                    ON DELETE NO ACTION
                    ON UPDATE NO ACTION)""";
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Connection getSQLConnection() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();

        }
        File dataFolder = new File(plugin.getDataFolder(), dbname + ".db");
        if (!dataFolder.exists()) {
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "error creating database");
            }
        }
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
    }

    public void load() {
        connection = getSQLConnection();
        try {
            Statement s = connection.createStatement();
            s.executeUpdate(SQLiteCreateTable);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }
}