package com.github.kevindagame.Lang;

import com.github.kevindagame.ColorUtils;
import com.github.kevindagame.DailyLeaderBoards;
import org.bukkit.Bukkit;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public enum Message {
    ALREADY_EVENT_RUNNING_ERROR,
    AUTORUN_DISABLED_ERROR,
    COMMAND_HELP_DESCRIPTION,
    COMMAND_HELP_LABEL,
    COMMAND_HELP_USAGE,
    COMMAND_LANG_DESCRIPTION,
    COMMAND_LANG_LABEL,
    COMMAND_LANG_USAGE,
    COMMAND_NEXT_DESCRIPTION,
    COMMAND_NEXT_LABEL,
    COMMAND_NEXT_USAGE,
    COMMAND_RELOAD_DESCRIPTION,
    COMMAND_RELOAD_LABEL,
    COMMAND_RELOAD_USAGE,
    COMMAND_REWARDS_DESCRIPTION,
    COMMAND_REWARDS_LABEL,
    COMMAND_REWARDS_USAGE,
    COMMAND_SAVE_DESCRIPTION,
    COMMAND_SAVE_LABEL,
    COMMAND_SAVE_USAGE,
    COMMAND_START_DESCRIPTION,
    COMMAND_START_LABEL,
    COMMAND_START_USAGE,
    COMMAND_STATUS_DESCRIPTION,
    COMMAND_STATUS_LABEL,
    COMMAND_STATUS_USAGE,
    COMMAND_STOP_DESCRIPTION,
    COMMAND_STOP_LABEL,
    COMMAND_STOP_USAGE,
    HELP_HEADER,
    HELP_ROW,
    INVALID_ARGUMENT_ERROR,
    INVALID_EVENT_ERROR,
    INVALID_NUMBER_ERROR,
    INVALID_PLAYER_ERROR,
    LANG_RELOADED,
    LANG_RESET,
    LANG_RESET_ERROR,
    LEADERBOARD_DESCRIPTION,
    LEADERBOARD_DESCRIPTION_NO_EVENT,
    LEADERBOARD_HEADER,
    LEADERBOARD_HEADER_NO_EVENT,
    LEADERBOARD_INVALID_VALUE,
    LEADERBOARD_RANK,
    LEADERBOARD_RANK_NO_EVENT,
    LEADERBOARD_RANK_NO_PLAYER,
    NO_CURRENT_EVENT_ERROR,
    NO_PERMISSION_ERROR,
    PREFIX,
    REWARD_GIVE_MESSAGE,
    SAVE_EVENT_MESSAGE,
    SCORE_NO_EVENT,
    SCORE_NULL,
    START_EVENT_BROADCAST,
    START_EVENT_TIMER_BROADCAST,
    STATUS_EVENT_MESSAGE,


    STOP_EVENT_BROADCAST, STOP_EVENT_MESSAGE, TOO_FEW_ARGUMENTS_ERROR, TOO_MANY_ARGUMENTS_ERROR;


    private static final DailyLeaderBoards plugin = DailyLeaderBoards.getPlugin(DailyLeaderBoards.class);
    private MessageContainer message;

    public static void load() {
        DailyLeaderBoards.log("Loading messages");
        File langFile = new File(plugin.getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            plugin.saveResource("lang.yml", false);
        }
        FileConfiguration lang = YamlConfiguration.loadConfiguration(langFile);
        for (Message message : values()) {
            String langMessage = lang.getString(message.name());
            if (langMessage == null) {
                plugin.getLogger().warning("Unable to load message " + message.name() + ". Please make sure it exists in the lang file.");
                langMessage = "Error when loading message. Please contact an admin";
            }
//            message.setMessage(new RawMessage(message.name(), ChatColor.translateAlternateColorCodes('&', langMessage)));
            message.setMessage(new RawMessage(message.name(), ColorUtils.colorize(langMessage)));
        }
        DailyLeaderBoards.log("Finished loading messages");
    }

    private void setMessage(MessageContainer messageContainer) {
        message = messageContainer;
    }

    public void send(CommandSender sender, Object... objects) {
        sender.sendMessage(PREFIX.getMessage() + " " + Message.replaceArgs(message.getMessage(), objects));
    }

    public void sendNoPrefix(CommandSender sender, Object... objects) {
        sender.sendMessage(Message.replaceArgs(message.getMessage(), objects));
    }

    public String getMessage(Object... objects) {
        return Message.replaceArgs(message.getMessage(), objects);
    }

    public void broadcast(Object... objects) {
        Bukkit.broadcastMessage(PREFIX.getMessage() + " " + Message.replaceArgs(message.getMessage(), objects));
    }

    private static String replaceArgs(String msg, Object... objects) {
        if (msg == null)
            return null;

        for (int i = 0; i < objects.length; i++) {
            String objectString = objects[i].toString();
            msg = ColorUtils.colorize(msg.replace("{" + i + "}", objectString));
        }

        return msg;
    }

    private static abstract class MessageContainer {

        protected final String name;

        MessageContainer(String name) {
            this.name = name;
        }

        abstract String getMessage();

    }

    private static final class RawMessage extends MessageContainer {

        private final String message;

        RawMessage(String name, String message) {
            super(name);
            this.message = message;
        }

        @Override
        String getMessage() {
            return message;
        }
    }
}
