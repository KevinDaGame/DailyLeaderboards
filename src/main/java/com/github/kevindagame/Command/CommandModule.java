package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import org.bukkit.command.CommandSender;

public abstract class CommandModule
{
    private String label;
    private int minArgs;
    private int maxArgs;

    /**
     * @param label - The label of the command.
     * @param minArgs - The minimum amount of arguments.
     * @param maxArgs - The maximum amount of arguments.
     */
    public CommandModule(String label, int minArgs, int maxArgs)
    {
        this.label = label;
        this.minArgs = minArgs;
        this.maxArgs = maxArgs;

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

    //This method will process the command.
    public abstract boolean run(CommandSender sender, String[] args);
}