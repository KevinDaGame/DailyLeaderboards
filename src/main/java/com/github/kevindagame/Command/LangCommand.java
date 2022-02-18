package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;
import com.github.kevindagame.Permission;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LangCommand extends CommandModule{
    public LangCommand(DailyLeaderBoards dailyLeaderBoards) {
        super(dailyLeaderBoards, "lang", 1, 2, Permission.MANAGE);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) {
        if(args[0].equals("reload")){
            plugin.reloadLang();
            Message.LANG_RELOADED.send(sender);
            return true;
        }
        if(args[0].equals("reset")){
            if(args.length == 2 && args[1].equals("confirm")){
                File langFile = new File(plugin.getDataFolder(), "lang.yml");
                langFile.delete();
                plugin.reloadLang();
                Message.LANG_RESET.send(sender);
                return true;
            }
            Message.LANG_RESET_ERROR.send(sender);
        }
        return true;
    }

    @Override
    public List<String> tabComplete() {
        var list = new ArrayList<String>();
        list.add("reload");
        list.add("reset");
        return list;
    }
}
