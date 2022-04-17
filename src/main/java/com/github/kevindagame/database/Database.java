package com.github.kevindagame.database;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.events.LeaderBoard;
import com.github.kevindagame.Score;
import com.github.kevindagame.events.Event;

import java.sql.*;
import java.util.logging.Level;

public abstract class Database {
    public String table;
    final DailyLeaderBoards plugin;
    Connection connection;

    public Database(DailyLeaderBoards instance) {
        plugin = instance;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize() {
        connection = getSQLConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM event WHERE name = \"\"");
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
                statement.addBatch("INSERT INTO score VALUES( " + currentEvent.getId() + ", \"" + score.getUuid() + "\", " + score.getScore() + ")" +
                        " ON CONFLICT(event_id, UUID) DO UPDATE SET score = " + score.getScore() + ";");
            }
            statement.executeBatch();
            statement.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Event createEvent(Event e) throws SQLException {
        Connection conn = getSQLConnection();
        Statement statement = conn.createStatement();
        statement.executeUpdate("INSERT INTO event (name, start_time, end_time, is_running) VALUES(\"" + e.getName() + "\", \"" + e.getStartTime() + "\", \"" + e.getEndTime() + "\", 1);");
        var rs = statement.getGeneratedKeys();
        if (rs.next()) {
            e.setId(rs.getInt(1));
            e.setDatabase(this);
        } else {

            throw new SQLException("No id was returned for this event");
        }
        return e;

    }

    public ResultSet getRunningEvent() {
        Connection conn;
        Statement statement;
        try {
            conn = getSQLConnection();
            statement = conn.createStatement();
            return statement.executeQuery("SELECT rowid, * FROM event WHERE (is_running = 1) LIMIT 1");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ResultSet getEvents(int amount) {
        Connection conn;
        Statement statement;
        try {
            conn = getSQLConnection();
            statement = conn.createStatement();
            System.out.println("SELECT rowid, * FROM event WHERE (is_running = 0) LIMIT " + amount + " ORDER BY 'end_time' DESCENDING");

            return statement.executeQuery("SELECT rowid, * FROM event WHERE (is_running = 0) ORDER BY 'end_time' DESC LIMIT " + amount);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ResultSet getLeaderBoard(int eventId) {
        Connection conn;
        Statement statement;
        try {
            conn = getSQLConnection();
            statement = conn.createStatement();
            return statement.executeQuery("SELECT * FROM score WHERE (event_id = " + eventId + ")");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void endEvent(Event event) {
        Connection conn;
        Statement statement;
        try {
            conn = getSQLConnection();
            statement = conn.createStatement();
            statement.executeUpdate("UPDATE event SET is_running = 0 WHERE rowid = " + event.getId() + ";");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

