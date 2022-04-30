package com.github.kevindagame.listener;

import com.github.kevindagame.events.LeaderBoard;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MythicMobkillsListener implements Listener {

    private final LeaderBoard leaderBoard;
    private final MythicMob mythicMob;

    public MythicMobkillsListener(LeaderBoard leaderBoard, MythicMob mythicMob) {
        this.leaderBoard = leaderBoard;
        this.mythicMob = mythicMob;
    }

    @EventHandler
    public void onMobKill(MythicMobDeathEvent e) {
        if(!e.getMobType().equals(mythicMob)) return;
        if(e.getKiller() == null) return;
        var killerid = e.getKiller().getUniqueId();
        var killer = Bukkit.getPlayer(killerid);
        if(killer == null) return;
        leaderBoard.addScore(killer, 1);
    }


}
