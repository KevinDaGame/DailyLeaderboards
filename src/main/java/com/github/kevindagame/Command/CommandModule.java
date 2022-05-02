package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Permission;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class CommandModule {
    private final String label;
    private final int minArgs;
    private final int maxArgs;
    private final Permission permission;
    protected final DailyLeaderBoards plugin;
    private final String usage;
    private final String description;

    public CommandModule(DailyLeaderBoards dailyLeaderBoards, String label, String description, String usage, int minArgs, int maxArgs, Permission permission) {
        this.plugin = dailyLeaderBoards;
        this.label = label;
        this.description = description;
        this.usage = usage;
        this.minArgs = minArgs;
        this.maxArgs = maxArgs;
        this.permission = permission;

    }

    public Permission getPermission() {
        return permission;
    }

    public String getLabel() {
        return label;
    }

    public int getMinArgs() {
        return minArgs;
    }

    public int getMaxArgs() {
        return maxArgs;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return "/dlb " + label + " " + usage;
    }

    //This method will process the command.
    public abstract boolean run(CommandSender sender, String[] args);

    public abstract List<String> tabComplete(String[] args);
}