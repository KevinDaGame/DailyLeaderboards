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
    PREFIX,
    TEST_MESSAGE,
    TEST2_MESSAGE,
    STATUS_EVENT_MESSAGE,
    NO_CURRENT_EVENT_ERROR,
    ALREADY_EVENT_RUNNING_ERROR,
    INVALID_EVENT_ERROR,
    TOO_MANY_ARGUMENTS_ERROR,
    TOO_FEW_ARGUMENTS_ERROR,
    NO_PERMISSION_ERROR,
    SAVE_EVENT_MESSAGE,
    START_EVENT_BROADCAST,
    STOP_EVENT_BROADCAST,
    START_EVENT_TIMER_BROADCAST,
    STOP_EVENT_MESSAGE,
    AUTORUN_DISABLED_ERROR,
    LEADERBOARD_RANK,
    LEADERBOARD_RANK_NO_EVENT,
    LEADERBOARD_HEADER,
    LEADERBOARD_HEADER_NO_EVENT,
    LEADERBOARD_INVALID_VALUE,
    LEADERBOARD_RANK_NO_PLAYER,
    LANG_RELOADED,
    LANG_RESET,
    LANG_RESET_ERROR,

    ;


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

    public String getMessage(Object... objects) {
        return Message.replaceArgs(message.getMessage(), objects);
    }

    public void broadcast(Object... objects){
        Bukkit.broadcastMessage(PREFIX.getMessage() + " " + Message.replaceArgs(message.getMessage(), objects));
    }

    private static String replaceArgs(String msg, Object... objects) {
        if (msg == null)
            return null;

        for (int i = 0; i < objects.length; i++) {
            String objectString = objects[i].toString();
            msg = msg.replace("{" + i + "}", objectString);
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
