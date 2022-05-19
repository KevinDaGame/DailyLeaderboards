package com.github.kevindagame.events;

import com.github.kevindagame.Score;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoard {
    private final Event event;
    private final List<Score> scores;

    public LeaderBoard(Event event) {
        this.event = event;
        this.scores = new ArrayList<>();
    }

    public void addScore(OfflinePlayer player, int amount) {
        var score = getScore(player.getUniqueId().toString());
        if (score == null) {
            score = new Score(player.getUniqueId().toString(), player.getName());
            scores.add(score);
        }
        score.addScore(amount);
    }

    public Score getScore(String uuid) {
        var score = scores.stream().filter(s -> s.getUuid().equals(uuid)).findFirst();
        if (score.isEmpty()) return null;
        return score.get();
    }

    public Score getScore(int rank) {
        return scores.get(rank - 1);
    }


    public List<Score> getScores() {
        return scores;
    }

    public Event getEvent() {
        return event;
    }
}
