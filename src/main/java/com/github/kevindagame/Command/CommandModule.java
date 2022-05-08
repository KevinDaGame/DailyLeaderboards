package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.Permission;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class CommandModule {
    private final Message label;
    private final int minArgs;
    private final int maxArgs;
    private final Permission permission;
    protected final DailyLeaderBoards plugin;
    private final Message usage;
    private final Message description;

    public CommandModule(DailyLeaderBoards dailyLeaderBoards, Message label, Message description, Message usage, int minArgs, int maxArgs, Permission permission) {
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
        return label.getMessage();
    }

    public int getMinArgs() {
        return minArgs;
    }

    public int getMaxArgs() {
        return maxArgs;
    }

    public String getDescription() {
        return description.getMessage();
    }

    public String getUsage() {
        return "/dlb " + getLabel() + " " + usage;
    }

    //This method will process the command.
    public abstract boolean run(CommandSender sender, String[] args);

    public abstract List<String> tabComplete(String[] args);
}