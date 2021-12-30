package com.github.kevindagame;

import com.github.kevindagame.listener.BlocksMinedListener;
import com.github.kevindagame.listener.BlocksPlacedListener;
import org.apache.commons.lang.NullArgumentException;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

public class EventListenerFactory {
    private final EventLoadError error;

    public EventListenerFactory() {
        error = new EventLoadError();
    }

    public Listener getListener(LeaderBoard leaderboard, String key, FileConfiguration file) {
        Material material;
        var type = file.getString(key + ".event-type");
        if(type == null) throw new NullArgumentException("The key " + key + " in events.yml has no event-type!");
        switch (type) {
            case ("blocks-mined") -> {
                material = getMaterial(key, file);
                if (material != null) return new BlocksMinedListener(leaderboard, material);
            }
            case ("blocks-placed") -> {
                material = getMaterial(key, file);
                if (material != null) return new BlocksPlacedListener(leaderboard, material);
            }
        }
        return null;
    }

    private Material getMaterial(String key, FileConfiguration file) {
        var materialString = file.getString(key + ".event-material");
        if(materialString == null) throw new NullArgumentException("The key " + key + " in events.yml requires a material!");
        var material = Material.getMaterial(materialString);
        if (material == null) {
            error.error(key, "An invalid material was specified");
            return null;
        }
        return material;
    }
}
