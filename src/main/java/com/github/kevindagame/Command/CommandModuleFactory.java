package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;
import com.github.kevindagame.Lang.Message;

import java.util.HashMap;
import java.util.Map;

public class CommandModuleFactory {
    public static Map<String, CommandModule> getCommandModules(DailyLeaderBoards dailyLeaderBoards) {
        Map<String, CommandModule> commandModules = new HashMap<>();
        commandModules.put(Message.COMMAND_HELP_LABEL.getMessage(), new HelpCommand(dailyLeaderBoards));
        commandModules.put(Message.COMMAND_NEXT_LABEL.getMessage(), new NextEventCommand(dailyLeaderBoards));
        commandModules.put(Message.COMMAND_STOP_LABEL.getMessage(), new StopEventCommand(dailyLeaderBoards));
        commandModules.put(Message.COMMAND_START_LABEL.getMessage(), new StartEventCommand(dailyLeaderBoards));
        commandModules.put(Message.COMMAND_STATUS_LABEL.getMessage(), new StatusEventCommand(dailyLeaderBoards));
        commandModules.put(Message.COMMAND_SAVE_LABEL.getMessage(), new SaveEventCommand(dailyLeaderBoards));
        commandModules.put(Message.COMMAND_LANG_LABEL.getMessage(), new LangCommand(dailyLeaderBoards));
        commandModules.put(Message.COMMAND_RELOAD_LABEL.getMessage(), new ReloadCommand(dailyLeaderBoards));
        return commandModules;
    }
}
