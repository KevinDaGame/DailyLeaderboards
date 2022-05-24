package com.github.kevindagame;

import org.jetbrains.annotations.NotNull;

public class TimeFormatter {
    private static final long DAY = 86400000L;
    private static final long HOUR = 3600000L;
    private static final long MINUTE = 60000L;
    private static final long SECOND = 1000L;

    public static long deformatTimeRemaining(String HHMMSS) throws IllegalArgumentException{
        String[] split = HHMMSS.split(":");
        if(split.length != 3) throw new IllegalArgumentException("The given string is invalid! String: " + HHMMSS);
        long milliseconds = 0;
        milliseconds += Long.parseLong(split[0]) * HOUR;
        milliseconds += Long.parseLong(split[1]) * MINUTE;
        milliseconds += Long.parseLong(split[2]) * SECOND;
        return milliseconds;
    }

    public static String formatTimeRemaining(long miliseconds) {
        int days = (int)(miliseconds / DAY);
        miliseconds -= days * DAY;
        int hours = (int) (miliseconds / HOUR);
        miliseconds -= hours * HOUR;
        int minutes = (int) (miliseconds / MINUTE);
        miliseconds -= minutes * MINUTE;
        int seconds = (int) (miliseconds / SECOND);
        miliseconds -= seconds * SECOND;

//        String result = hours + ":" + minutes + ":" + seconds;
        String result = "";
        if (days == 1) {
            result += days + " day ";
        }
        else if(days > 1){
            result += days + " days ";
        }
        result += formatUnit(hours, true);
        result += formatUnit(minutes, true);
        result += formatUnit(seconds, false);
        return result;
    }

    @NotNull
    private static String formatUnit(int time, boolean addColon) {
        String result = "";
        if (time <= 0) {
            result += "00";
        } else if (time < 10) {
            result += "0" + time;
        } else {
            result += time;
        }
        if (addColon) {
            result += ":";
        }
        return result;
    }

}
