package com.github.kevindagame.database;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.LeaderBoard;
import com.github.kevindagame.Score;
import com.github.kevindagame.events.Event;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.logging.Level;

public abstract class Database {
    public String table;
    DailyLeaderBoards plugin;
    Connection connection;

    public Database(DailyLeaderBoards instance) {
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize() {
        connection = getSQLConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM event WHERE score = 0");
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

    public void saveEvent(Event currentEvent) {
        Connection conn;
        Statement statement;
        try {
            conn = getSQLConnection();
            statement = connection.createStatement();
            LeaderBoard lb = currentEvent.getLeaderBoard();
            for (Score score : lb.getScores()) {
                statement.addBatch("INSERT INTO score VALUES(\"" + score.getUuid() + "\", " + score.getScore() + ")" +
                        " ON CONFLICT(UUID) DO UPDATE SET score = " + score.getScore() + ";");
            }
            statement.executeBatch();
            statement.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

