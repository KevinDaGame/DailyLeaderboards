package com.github.kevindagame;

import com.github.kevindagame.events.LeaderBoard;
import com.github.kevindagame.listener.*;
import org.apache.commons.lang.NullArgumentException;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;

public class EventListenerFactory {
    private final EventLoadError error;

    public EventListenerFactory() {
        error = new EventLoadError();
    }

    public Listener getListener(LeaderBoard leaderboard, String key, FileConfiguration file) {
        Material material;
        EntityType entityType;
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
                if (entityType != null) return new EntityKillEvent(leaderboard, entityType);
            }
        }
        return null;
    }

    private EntityType getEntityType(String key, FileConfiguration file) {
        var entityString = file.getString(key + ".event-entity");
        if (entityString == null)
            throw new NullArgumentException("The key " + key + " in events.yml requires an entity!");
        var entityType = EntityType.valueOf(entityString);
        if (entityType == null) {
            error.error(key, "An invalid entity type was specified");
            return null;
        }
        return entityType;
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
