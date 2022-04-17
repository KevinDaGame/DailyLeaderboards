package com.github.kevindagame.listener;

import com.bgsoftware.superiorskyblock.api.events.IslandWorthUpdateEvent;
import com.github.kevindagame.events.LeaderBoard;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class IslandLevelListener implements Listener {

    private final LeaderBoard leaderBoard;
    private final Material material;

    public IslandLevelListener(LeaderBoard leaderBoard, Material material) {
        this.leaderBoard = leaderBoard;
        this.material = material;
    }
     @EventHandler
    public void islandWorthUpdate(IslandWorthUpdateEvent event) {
         var diff = event.getNewLevel().intValue() - event.getOldLevel().intValue();
         leaderBoard.addScore(event.get(), 1);
     }
}
