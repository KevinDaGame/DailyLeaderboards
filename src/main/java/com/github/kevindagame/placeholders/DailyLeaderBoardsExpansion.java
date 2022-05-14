package com.github.kevindagame.placeholders;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.Score;
import com.github.kevindagame.events.Event;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import java.util.Comparator;

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
        //param 0 = header or rank
        //param 1 = leaderboard number (0 is the current event), 1 is the latest ended, 2 is the 2nd latest etc
        //param 2 = the rank (0 based)
        if (newParams[0].equals("header") && newParams.length == 2) {
            return headerRequest(newParams);
        }
        if (newParams[0].equals("description") && newParams.length == 2) {
            return descriptionRequest(newParams);
        }
        if (newParams[0].equals("score")) {
            return score(p);
        }
        if (newParams[0].equals("rank") && newParams.length == 3) {
            return rankRequest(newParams);
        }
        return Message.LEADERBOARD_INVALID_VALUE.getMessage();
    }


    private String score(OfflinePlayer p) {
        var event = plugin.getEventsHandler().getCurrentEvent();
        if(event == null) return Message.SCORE_NO_EVENT.getMessage();
        var score = event.getLeaderBoard().getScore(p.getUniqueId().toString());
        if(score == null) return Message.SCORE_NULL.getMessage();
        else return score.getScore() + "";
    }

    private String rankRequest(String[] newParams) {
        if (isinvalidInt(newParams[1])) return Message.LEADERBOARD_INVALID_VALUE.getMessage();
        if (isinvalidInt(newParams[2])) return Message.LEADERBOARD_INVALID_VALUE.getMessage();
        var event = plugin.getEventsHandler().getEvent(Integer.parseInt(newParams[1]));
        if(event == null) return Message.LEADERBOARD_RANK_NO_EVENT.getMessage(Integer.parseInt(newParams[2]) + 1);
        var leaderboard = event.getLeaderBoard().getScores().stream().sorted(Comparator.comparingInt(Score::getScore).reversed()).toList();
        if(leaderboard.size() < Integer.parseInt(newParams[2]) + 1){
            return Message.LEADERBOARD_RANK_NO_PLAYER.getMessage(Integer.parseInt(newParams[2]) + 1);
        }
        var score = leaderboard.get(Integer.parseInt(newParams[2]));
        return Message.LEADERBOARD_RANK.getMessage(Integer.parseInt(newParams[2]) + 1, score.getName(), score.getScore());
    }

    private String descriptionRequest(String[] newParams) {
        if (isinvalidInt(newParams[1])) return Message.LEADERBOARD_INVALID_VALUE.getMessage();
        var event = plugin.getEventsHandler().getEvent(Integer.parseInt(newParams[1]));
        if(event == null) return Message.LEADERBOARD_DESCRIPTION_NO_EVENT.getMessage();
        return Message.LEADERBOARD_DESCRIPTION.getMessage(event.getDescription());
    }

    private String headerRequest(String[] newParams) {
        if (isinvalidInt(newParams[1])) return Message.LEADERBOARD_INVALID_VALUE.getMessage();
        var event = plugin.getEventsHandler().getEvent(Integer.parseInt(newParams[1]));
        if(event == null) return Message.LEADERBOARD_HEADER_NO_EVENT.getMessage();
        return Message.LEADERBOARD_HEADER.getMessage(event.getName());
    }

    private boolean isinvalidInt(String string) {
        var charArr = string.toCharArray();
        for (char c : charArr) {
            if (!Character.isDigit(c)) return true;
        }
        return false;
    }

    @Override
    public boolean persist() {
        return true;
    }
}
