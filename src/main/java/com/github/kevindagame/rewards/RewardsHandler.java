package com.github.kevindagame.rewards;

import com.github.kevindagame.DailyLeaderBoards;
import org.bukkit.Bukkit;

public class RewardsHandler {
    //add reward
    public static void addReward(String uuid, String reward) {
        //if player is online then give rewards
        if (DailyLeaderBoards.plugin.getServer().getPlayer(uuid) != null) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), reward);
            return;
        }
        //add reward to database
        DailyLeaderBoards.plugin.getDataBase().addReward(uuid, reward);

    }

    public static void handleRewards(String uuid){
        var rewards = DailyLeaderBoards.plugin.getDataBase().getRewards(uuid);
        for (String reward : rewards) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), reward);
        }
    }
}
