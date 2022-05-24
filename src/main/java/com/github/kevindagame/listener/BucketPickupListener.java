package com.github.kevindagame.listener;

import com.github.kevindagame.events.LeaderBoard;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class BucketPickupListener implements Listener {
    private final LeaderBoard leaderBoard;
    private final Material material;

    public BucketPickupListener(LeaderBoard leaderBoard, Material material) {
        this.leaderBoard = leaderBoard;
        this.material = material;
    }
    @EventHandler
    public void onBucketPickup(PlayerBucketFillEvent e) {
        if (!e.getBlock().getType().equals(material)) return;
        leaderBoard.addScore(e.getPlayer(), 1);
    }
}
