package com.github.kevindagame.events;

import com.github.kevindagame.LeaderBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.sql.Date;


public class Event {
    private final Listener listener;
    private final LeaderBoard leaderBoard;
    private Date startTime;
    private Date endTime;
    public Event(Listener listener, LeaderBoard leaderBoard) {
        this.listener = listener;
        this.leaderBoard = leaderBoard;
    }

    public LeaderBoard getLeaderBoard() {
        return leaderBoard;
    }

    public Listener getListener() {
        return listener;
    }
}
