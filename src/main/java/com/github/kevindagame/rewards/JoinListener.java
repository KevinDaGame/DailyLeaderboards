package com.github.kevindagame.rewards;

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
