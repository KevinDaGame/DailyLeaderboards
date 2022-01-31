package com.github.kevindagame;

public class Timespan {
    private long milliSeconds;


    public Timespan(Interval... interval) {
        for(Interval i: interval){
            milliSeconds += convertToMs(i);
        }
    }

    private long convertToMs(Interval i) {
        return switch (i.getUnit()) {
            case DAY -> i.getValue() * 86400000L;
            case HOUR -> i.getValue() * 3600000L;
            case MINUTE -> i.getValue() * 60000L;
            case SECOND -> i.getValue() * 1000L;
            case MILLISECOND -> i.getValue();
            case TICK -> i.getValue() * 50L;
        };
    }

    public void add(Interval interval){
        milliSeconds += convertToMs(interval);
    }

    public void subtract(Interval interval){
        milliSeconds -= convertToMs(interval);
    }

    public String getFormatted(){
        var seconds = milliSeconds * 1000;
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%d:%02d:%02d", h,m,s);
    }

    public long getMilliSeconds() {
        return milliSeconds;
    }
}

