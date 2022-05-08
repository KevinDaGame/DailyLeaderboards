package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.Permission;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpCommand extends CommandModule {

    public HelpCommand(DailyLeaderBoards dailyLeaderBoards) {
        super(dailyLeaderBoards, Message.COMMAND_HELP_LABEL, Message.COMMAND_HELP_DESCRIPTION, Message.COMMAND_HELP_USAGE, 0, 0, Permission.HELP);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        Message.HELP_HEADER.send(sender);
        for (CommandModule command : plugin.getCommandHandler().getCommandModules().values()) {
            Message.HELP_ROW.sendNoPrefix(sender, command.getLabel(), command.getDescription(), command.getUsage());
        }
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        return null;
    }
}
