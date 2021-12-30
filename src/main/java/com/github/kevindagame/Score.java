package com.github.kevindagame;

public class Score {
    private int score;
    private final String uuid;

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
