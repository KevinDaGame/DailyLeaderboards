package com.github.kevindagame.listener;

import com.github.kevindagame.LeaderBoard;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlocksMinedListener implements Listener {

    private final LeaderBoard leaderBoard;
    private final Material material;

    public BlocksMinedListener(LeaderBoard leaderBoard, Material material) {
        this.leaderBoard = leaderBoard;
        this.material = material;
    }

    @EventHandler
    public void onBlockMined(BlockBreakEvent e) {
        System.out.println("block mined");
        if (!e.getBlock().getType().equals(material)) return;
        leaderBoard.addScore(e.getPlayer(), 1);
    }
}
