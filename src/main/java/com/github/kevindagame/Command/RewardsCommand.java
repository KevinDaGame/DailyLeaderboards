package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;

import java.util.ArrayList;
import java.util.List;

public class RewardsCommand extends CommandModule {

    public RewardsCommand(DailyLeaderBoards dailyLeaderBoards) {
        super(dailyLeaderBoards, Message.COMMAND_REWARDS_LABEL, Message.COMMAND_REWARDS_DESCRIPTION, Message.COMMAND_REWARDS_USAGE, 1, 2, Permission.MANAGE);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        if (args.length == 3) {
            if (args[1].equalsIgnoreCase("add")) {
                //check if arg 2 is a player
                if (plugin.getServer().getPlayer(args[2]) == null) {
                    Message.INVALID_PLAYER_ERROR.send(sender, args[2]);
                }
            }
        }
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        var complete = new ArrayList<String>();
        if (args.length == 2) {
            complete.add("add");
            return complete;
        }

        //if first arg is add, return list of players
        if (args.length == 3 && args[1].equalsIgnoreCase("add")) {
            return plugin.getServer().getOnlinePlayers().stream().toList().stream().map(HumanEntity::getName).filter(name -> name.toLowerCase().startsWith(args[2].toLowerCase())).toList();
        }
        if (args.length == 4 && args[1].equalsIgnoreCase("add")) {
            var keys = DailyLeaderBoards.plugin.getPluginConfig().getRewards().keySet();
            for (var key : keys) complete.add(key.toString());
            return complete;
        }
        return complete;
    }
}
