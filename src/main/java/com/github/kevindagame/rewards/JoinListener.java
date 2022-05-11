package com.github.kevindagame.rewards;

import com.github.kevindagame.DailyLeaderBoards;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        var uuid = event.getPlayer().getUniqueId().toString();
        RewardsHandler.handleRewards(uuid);
    }
}
