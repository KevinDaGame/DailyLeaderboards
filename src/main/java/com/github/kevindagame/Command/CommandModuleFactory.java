package com.github.kevindagame.Command;

import com.github.kevindagame.DailyLeaderBoards;

import java.util.HashMap;
import java.util.Map;

public class CommandModuleFactory {
    public static Map<String, CommandModule> getCommandModules(DailyLeaderBoards dailyLeaderBoards) {
        Map<String, CommandModule> commandModules = new HashMap<>();
        commandModules.put("help", new HelpCommand(dailyLeaderBoards));
        commandModules.put("next", new NextEventCommand(dailyLeaderBoards));
        commandModules.put("test", new TestCommand(dailyLeaderBoards));
        commandModules.put("stop", new StopEventCommand(dailyLeaderBoards));
        commandModules.put("start", new StartEventCommand(dailyLeaderBoards));
        commandModules.put("status", new StatusEventCommand(dailyLeaderBoards));
        commandModules.put("save", new SaveEventCommand(dailyLeaderBoards));
        commandModules.put("lang", new LangCommand(dailyLeaderBoards));
        commandModules.put("reload", new ReloadCommand(dailyLeaderBoards));
        return commandModules;
    }
}
