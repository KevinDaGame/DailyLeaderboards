package com.github.kevindagame.events;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.listener.*;

import io.lumine.mythic.api.MythicProvider;
import io.lumine.mythic.api.mobs.MythicMob;
import org.apache.commons.lang.NullArgumentException;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.plugin.UnknownDependencyException;

public class EventListenerFactory {
    private final EventLoadError error;

    public EventListenerFactory() {
        error = new EventLoadError();
    }

    public Listener getListener(LeaderBoard leaderboard, String key, FileConfiguration file) throws UnsupportedOperationException{
        Material material;
        EntityType entityType;
        if(!file.contains(key)) throw new UnsupportedOperationException("The key " + key + " in events.yml does not exist! You may have removed it while an event of this type was running!");
        var type = file.getString(key + ".event-type");
        if (type == null) throw new NullArgumentException("The key " + key + " in events.yml has no event-type!");
        switch (type) {
            case ("blocks-broken") -> {
                material = getMaterial(key, file);
                if (material != null) return new BlocksMinedListener(leaderboard, material);
            }
            case ("blocks-placed") -> {
                material = getMaterial(key, file);
                if (material != null) return new BlocksPlacedListener(leaderboard, material);
            }
            case("bucket-placed") -> {
                material = getMaterial(key, file);
                if (material != null) return new BucketPlacedListener(leaderboard, material);
            }
            case("bucket-pickup") -> {
                material = getMaterial(key, file);
                if (material != null) return new BucketPickupListener(leaderboard, material);
            }
            case("entity-kill") -> {
                entityType = getEntityType(key, file);
                if (entityType != null) return new EntityKillListener(leaderboard, entityType);
            }
            case("mythic-mob-kill") -> {
                if(DailyLeaderBoards.plugin.getServer().getPluginManager().getPlugin("MythicMobs") == null) throw new UnknownDependencyException("The plugin MythicMobs is not found, yet is required for one of your events!");
                MythicMob mob = getMythicMob(key, file);
                if(mob != null) return new MythicMobKillsListener(leaderboard, mob);
            }
        }
        return null;
    }

    private EntityType getEntityType(String key, FileConfiguration file) {
        var entityString = file.getString(key + ".event-entity");
        if (entityString == null)
            throw new NullArgumentException("The key " + key + " in events.yml requires an entity!");
        try {
            var entityType = EntityType.valueOf(entityString);
            return entityType;
        }
        catch (IllegalArgumentException e) {
            error.error(key, "An invalid entity type was specified");
            return null;
        }
    }
    private MythicMob getMythicMob(String key, FileConfiguration file) {
        var mobString = file.getString(key + ".event-mob");
        if (mobString == null)
            throw new NullArgumentException("The key " + key + " in events.yml requires an entity!");
        var mob = MythicProvider.get().getMobManager().getMythicMob(mobString);
        if (mob.isEmpty()) {
            error.error(key, "An invalid MythicMob type was specified");
            return null;
        }
        return mob.get();
    }
    private Material getMaterial(String key, FileConfiguration file) {
        var materialString = file.getString(key + ".event-material");
        if (materialString == null)
            throw new NullArgumentException("The key " + key + " in events.yml requires a material!");
        var material = Material.getMaterial(materialString);
        if (material == null) {
            error.error(key, "An invalid material was specified");
            return null;
        }
        return material;
    }
}
