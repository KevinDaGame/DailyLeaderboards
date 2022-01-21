package com.github.kevindagame.Command;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends CommandModule {

    public HelpCommand() {
        super("help", 0, 0);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        sender.sendMessage("This is the help command!");
        return true;
    }

    @Override
    public List<String> tabComplete() {
        return null;
    }
}
