package com.github.kevindagame.listener;

import com.github.kevindagame.events.LeaderBoard;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityKillListener implements Listener {
    private final LeaderBoard leaderBoard;
    private final EntityType entityType;

    public EntityKillListener(LeaderBoard leaderBoard, EntityType entityType) {
        this.leaderBoard = leaderBoard;
        this.entityType = entityType;
    }
    @EventHandler
    public void onEntityKill(EntityDeathEvent e){
        Entity entity = e.getEntity();
        if (!entity.getType().equals(entityType)) return;
        if(e.getEntity().getKiller() == null) return;
        leaderBoard.addScore(e.getEntity().getKiller(), 1);
    }
}
