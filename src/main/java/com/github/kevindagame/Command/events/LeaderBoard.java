package com.github.kevindagame.Command.events;

import com.github.kevindagame.Score;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoard {
    private final Event event;
    private final List<Score> scores;

    public LeaderBoard(Event event) {
        this.event = event;
        this.scores = new ArrayList<>();
    }

    public void addScore(Player player, int amount) {
        var score = getScore(player.getUniqueId().toString());
        if (score == null) {
            score = new Score(player.getUniqueId().toString());
            scores.add(score);
        }
        score.addScore(amount);
        System.out.println("adding score! New score: " + score.getScore());
    }

    private Score getScore(String uuid) {
        var score = scores.stream().filter(s -> s.getUuid().equals(uuid)).findFirst();
        if (score.isEmpty()) return null;
        return score.get();
    }

    public List<Score> getScores() {
        return scores;
    }

    public Event getEvent() {
        return event;
    }
}
