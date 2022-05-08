package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.Permission;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LangCommand extends CommandModule {
    public LangCommand(DailyLeaderBoards dailyLeaderBoards) {
        super(dailyLeaderBoards, Message.COMMAND_LANG_LABEL, Message.COMMAND_LANG_DESCRIPTION, Message.COMMAND_LANG_USAGE, 1, 2, Permission.MANAGE);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        if (args[0].equals("reload")) {
            plugin.reloadLang();
            Message.LANG_RELOADED.send(sender);
            return true;
        }
        if (args[0].equals("reset")) {
            if (args.length == 2 && args[1].equals("confirm")) {
                File langFile = new File(plugin.getDataFolder(), "lang.yml");
                langFile.delete();
                plugin.reloadLang();
                Message.LANG_RESET.send(sender);
                return true;
            }
            Message.LANG_RESET_ERROR.send(sender);
            return true;
        }
        if (args[0].equals("test")) {
            if (args.length == 2) {
                try {
                    Message.valueOf(args[1]).send(sender);
                }
                catch (Exception e){
                    Message.INVALID_ARGUMENT_ERROR.send(sender);
                }
                return true;
            }
            for (Message m : Message.values()) {
                sender.sendMessage(ChatColor.RED + m.toString() + ChatColor.WHITE + ": " + m.getMessage());
            }
        }
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        if(args.length == 3){
            switch(args[1]){
                case "test":
                    return Arrays.stream(Message.values()).map(Message::toString).toList();
                case "reset":
                    return List.of("confirm");
            }
        }
        if(args.length == 2){
            var list = new ArrayList<String>();
            list.add("reload");
            list.add("reset");
            list.add("test");
            return list;

        }
        return null;
    }
}
