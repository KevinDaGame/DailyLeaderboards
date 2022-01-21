package com.github.kevindagame.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandModuleFactory {
    public static Map<String, CommandModule> getCommandModules() {
        Map<String, CommandModule> commandModules = new HashMap<>();
        commandModules.put("help", new HelpCommand());
        return commandModules;
    }
}
