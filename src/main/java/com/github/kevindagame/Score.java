package com.github.kevindagame;

public class Score {
    private final String uuid;
    private int score;

    public Score(String uuid) {
        this.uuid = uuid;
    }

    public int getScore() {
        return score;
    }

    public String getUuid() {
        return uuid;
    }

    public void addScore(int amount) {
        score += amount;
    }
}
