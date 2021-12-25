package com.github.kevindagame.database;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.*;
import java.util.logging.Level;

public abstract class Database {
    public String table = "daily_leaderboards";
    JavaPlugin plugin;
    Connection connection;

    public Database(JavaPlugin instance) {
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize() {
        connection = getSQLConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + table + " WHERE amount = 0");
            ResultSet rs = ps.executeQuery();
            close(ps, rs);

        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
        }
    }


    public void close(PreparedStatement ps, ResultSet rs) {
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
            Error.close(plugin, ex);
        }
    }

}

