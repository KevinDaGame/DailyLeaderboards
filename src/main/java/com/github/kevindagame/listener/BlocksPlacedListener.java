package com.github.kevindagame.listener;

import com.github.kevindagame.Command.events.LeaderBoard;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlocksPlacedListener implements Listener {

    private final LeaderBoard leaderBoard;
    private final Material material;

    public BlocksPlacedListener(LeaderBoard leaderBoard, Material material) {
        this.leaderBoard = leaderBoard;
        this.material = material;
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent e) {
        if (!e.getBlock().getType().equals(material)) return;
        leaderBoard.addScore(e.getPlayer(), 1);
    }
}
