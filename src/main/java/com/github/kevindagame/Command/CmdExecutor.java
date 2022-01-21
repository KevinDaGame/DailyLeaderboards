package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdExecutor implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        //Checks if the label is one of yours.
        if(DailyLeaderBoards.commands.containsKey(label))
        {
            //If so, it will check if they have the permission.
            if(sender.hasPermission("DailyLeaderBoards." + label))
            {
                //Get the module so you can access its variables.
                CommandModule mod = DailyLeaderBoards.commands.get(label);

                //Checks if the amount of arguments are within the minimum amount and maximum amount.
                if(args.length >= mod.getMinArgs() && args.length <= mod.getMaxArgs())
                {
                    //If so, it will run the command.
                    mod.run(sender, args);
                }else
                {
                    //If not, it will send the player some sass.
                    sender.sendMessage("You should learn how to use this command...");
                }
            }else
            {
                //If not, it will send the player some more sass.
                sender.sendMessage("Looks like you are lacking some perms.");
            }
        }

        return false;
    }
}