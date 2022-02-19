package com.github.kevindagame;

public class Score {
    private final String uuid;
    private int score;
    private final String name;
    public Score(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;

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

    public Object getName() {
        return name;
    }
}
