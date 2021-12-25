package com.github.kevindagame.database;
import com.github.kevindagame.DailyLeaderBoards;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class SQLite extends Database {
    public String SQLiteCreateTokensTable = "CREATE TABLE IF NOT EXISTS " + table + " (" +
            "`UUID` varchar(32) NOT NULL," +
            "`score` int(5) NOT NULL," +
            "`transaction_date` DATE NOT NULL," +
            "`sell_price` int(5) NOT NULL," +
            "PRIMARY KEY(item_name,transaction_date)" +
            ");";
    String dbname;

    public SQLite(DailyLeaderBoards instance) {
        super(instance);
        dbname = plugin.getPluginConfig().getDatabaseName();
        table = plugin.getPluginConfig().getTableName();
    }

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
            s.executeUpdate(SQLiteCreateTokensTable);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }
}