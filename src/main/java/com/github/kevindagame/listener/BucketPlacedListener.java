package com.github.kevindagame.listener;

import com.github.kevindagame.events.LeaderBoard;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class BucketPlacedListener implements Listener {
    private final LeaderBoard leaderBoard;
    private final Material material;

    public BucketPlacedListener(LeaderBoard leaderBoard, Material material) {
        this.leaderBoard = leaderBoard;
        this.material = material;
    }
    @EventHandler
    public void onBucketPlaced(PlayerBucketEmptyEvent e) {
        if (!e.getBucket().equals(material)) return;
        leaderBoard.addScore(e.getPlayer(), 1);
    }
}
