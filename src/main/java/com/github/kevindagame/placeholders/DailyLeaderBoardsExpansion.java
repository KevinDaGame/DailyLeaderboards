package com.github.kevindagame.placeholders;

import com.github.kevindagame.Command.events.Event;
import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.Score;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Comparator;
import java.util.stream.Collectors;

public class DailyLeaderBoardsExpansion extends PlaceholderExpansion {
    private final DailyLeaderBoards plugin;

    public DailyLeaderBoardsExpansion(DailyLeaderBoards dailyLeaderBoards) {
        this.plugin = dailyLeaderBoards;
    }

    @Override
    public String getIdentifier() {
        return "dailyleaderboards";
    }

    @Override
    public String getAuthor() {
        return "KevDaDev";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer p, String params) {
        String[] newParams = params.split("_");

        if (newParams[0].equals("header") && newParams.length == 2) {
            if (!isValidInt(newParams[1])) return Message.LEADERBOARD_INVALID_VALUE.getMessage();
            if (Integer.valueOf(newParams[1]) == 0) {
                return Message.LEADERBOARD_HEADER.getMessage(plugin.getEventsHandler().getCurrentEvent().getName());
            }
        }

        if (newParams[0].equals("rank") && newParams.length == 3) {
            if (!isValidInt(newParams[1])) return Message.LEADERBOARD_INVALID_VALUE.getMessage();
            Event event = null;
            if (Integer.valueOf(newParams[1]) == 0) {
                event = plugin.getEventsHandler().getCurrentEvent();
            }
            if (event == null) {
                return Message.LEADERBOARD_RANK_NO_CURRENT_EVENT.getMessage();
            }
            var leaderboard = event.getLeaderBoard().getScores().stream().sorted(Comparator.comparingInt(Score::getScore).reversed()).collect(Collectors.toList());
            if(leaderboard.size() < Integer.valueOf(newParams[2]) + 1){
                return Message.LEADERBOARD_RANK_NO_PLAYER.getMessage();
            }
            var score = leaderboard.get(Integer.valueOf(newParams[2]));
            return Message.LEADERBOARD_RANK.getMessage(Integer.valueOf(newParams[2]) + 1, score.getName(), score.getScore());

        }
        return Message.LEADERBOARD_INVALID_VALUE.getMessage();
    }

    private boolean isValidInt(String string) {
        var charArr = string.toCharArray();
        for (char c : charArr) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    private String replace(String toReplace, String word) {
        String replaced = toReplace.replace(word, "");
        if (replaced.startsWith("_")) return replaced.substring(1);
        return replaced;

    }

    @Override
    public boolean persist() {
        return true;
    }
}
