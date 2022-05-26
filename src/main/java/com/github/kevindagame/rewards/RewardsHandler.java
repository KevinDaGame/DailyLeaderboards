package com.github.kevindagame.rewards;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.events.Event;
import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.UUID;

public class RewardsHandler {
    //add reward
    public static void addReward(String uuid, String reward) {
        //if player is online then give rewards
        if (DailyLeaderBoards.plugin.getServer().getPlayer(UUID.fromString(uuid)) != null) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replaceVariables(uuid, reward));
            Message.REWARD_GIVE_MESSAGE.send(Objects.requireNonNull(Bukkit.getPlayer(UUID.fromString(uuid))));
            return;
        }
        //add reward to database
        DailyLeaderBoards.plugin.getDataBase().addReward(uuid, reward);

    }

    private static String replaceVariables(String uuid, String reward) {
        return reward.replaceAll("%player%", Objects.requireNonNull(Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName()));
    }
    public static void addRewards(Event event) {
        var rewards = DailyLeaderBoards.plugin.getPluginConfig().getRewards();
        var leaderboard = event.getLeaderBoard();
        leaderboard.sort();
        for(var reward: rewards.keySet()){
            var score  = leaderboard.getScore(reward);
            if(score == null) continue;
            addReward(score.getUuid(), rewards.get(reward));

        }

    }

    public static void handleRewards(String uuid){
        var rewards = DailyLeaderBoards.plugin.getDataBase().getRewards(uuid);
        for (String reward : rewards) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replaceVariables(uuid, reward));
            Message.REWARD_GIVE_MESSAGE.send(Objects.requireNonNull(Bukkit.getPlayer(UUID.fromString(uuid))));

        }
    }
}
