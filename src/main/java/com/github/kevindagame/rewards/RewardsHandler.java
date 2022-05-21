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
        reward = reward.replaceAll("%player%", uuid);
        //if player is online then give rewards
        if (DailyLeaderBoards.plugin.getServer().getPlayer(UUID.fromString(uuid)) != null) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), reward);
            Message.REWARD_GIVE_MESSAGE.send(Objects.requireNonNull(Bukkit.getPlayer(UUID.fromString(uuid))), reward);
            return;
        }
        //add reward to database
        DailyLeaderBoards.plugin.getDataBase().addReward(uuid, reward);

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
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), reward);
            Message.REWARD_GIVE_MESSAGE.send(Objects.requireNonNull(Bukkit.getPlayer(UUID.fromString(uuid))), reward);

        }
    }
}
