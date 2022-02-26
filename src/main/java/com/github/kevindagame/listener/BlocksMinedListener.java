package com.github.kevindagame.listener;

import com.github.kevindagame.events.LeaderBoard;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
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
        var block = e.getBlock();
        if (!block.getType().equals(material)) return;
        if (block.getBlockData() instanceof Ageable){
            if(((Ageable)block.getBlockData()).getAge() != ((Ageable)block.getBlockData()).getMaximumAge()) return;
        }
        leaderBoard.addScore(e.getPlayer(), 1);
    }
}
