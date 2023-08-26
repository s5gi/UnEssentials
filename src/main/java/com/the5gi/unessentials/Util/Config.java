package com.the5gi.unessentials.Util;

import java.io.IOException;

import com.the5gi.unessentials.UnEssentials;
import org.simpleyaml.configuration.file.YamlFile;

public class Config {
    private static final YamlFile config = new YamlFile(UnEssentials.plugin.getDataFolder().getPath() + "/config.yml");

    public static void onStart() {
        try {
            if (!config.exists()) {
                UnEssentials.plugin.getLogger().info("Creating Config...");
                UnEssentials.plugin.getDataFolder().mkdir();
                config.createNewFile();
                UnEssentials.plugin.getLogger().info("Config Created!");
            }
            load();
            UnEssentials.plugin.getLogger().info("Loaded Config");
        } catch (Exception e) {
            e.printStackTrace();
        }
        config.setComment(BaseLevel.PLUGIN_PREFIX.getField(), """
                Prefix of Plugin Commands ex: "[UnEssentials] Your gamemode has been changed!"
                You can use the & sign to use colors. https://htmlcolorcodes.com/minecraft-color-codes/""");
        config.addDefault(BaseLevel.PLUGIN_PREFIX.getField(), "&c&l[UnEssentials]&r ");

        config.setBlankLine("chat");
        config.setComment(Chat.CHAT_FORMAT_ENABLED.getField(), "If enabled, Reformats chat messages using the format field below.");
        config.addDefault(Chat.CHAT_FORMAT_ENABLED.getField(), Boolean.TRUE);

        config.setComment(Chat.CHAT_FORMAT.getField(), """
                The format of chat messages.
                There are built in placeholders, but otherwise external placeholders require PlaceHolderAPI.
                %NAME% gives the player's username. %MESSAGE% gives the player's message.
                You can use colors with the & sign. https://htmlcolorcodes.com/minecraft-color-codes/""");
        if (UnEssentials.PAPI && UnEssentials.LUCKPERMS) {
            config.addDefault(Chat.CHAT_FORMAT.getField(), "%luckperms_prefix% %NAME% %luckperms_suffix%:&r %MESSAGE%");
        } else {
            config.addDefault(Chat.CHAT_FORMAT.getField(), "<%NAME%> %MESSAGE%");
        }
        config.setBlankLine(Chat.CHAT_FORMAT.getField());

        config.setComment(Tab.TAB_FORMAT_ENABLED.getField(), "If enabled, Reformats tab usernames using the format field below.");
        config.addDefault(Tab.TAB_FORMAT_ENABLED.getField(), Boolean.TRUE);

        config.setComment(Tab.TAB_FORMAT.getField(), """
                The format of usernames in tab.
                There are built in placeholders, but otherwise external placeholders require PlaceHolderAPI.
                %NAME% gives the player's username.
                You can use colors with the & sign. https://htmlcolorcodes.com/minecraft-color-codes/""");
        if (UnEssentials.PAPI && UnEssentials.LUCKPERMS) {
            config.addDefault(Tab.TAB_FORMAT.getField(), "%luckperms_prefix% %NAME% %luckperms_suffix%");
        } else {
            config.addDefault(Tab.TAB_FORMAT.getField(), "%NAME%");
        }
        config.setBlankLine(Tab.TAB_FORMAT.getField());
        save();
        reload();
    }

    public static void load() {
        try {
            config.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void reload() {
        try {
            config.load();
            UnEssentials.PREFIX = UnEssentials.color(getFieldString(BaseLevel.PLUGIN_PREFIX));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save() {
        try {
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFieldString(Chat chat) {
        return config.getString(chat.getField());
    }

    public static String getFieldString(Tab tab) {
        return config.getString(tab.getField());
    }

    public static boolean getFieldBool(Chat chat) {
        return config.getBoolean(chat.getField());
    }

    public static boolean getFieldBool(Tab tab) {
        return config.getBoolean(tab.getField());
    }

    public static String getFieldString(BaseLevel bl) {
        return config.getString(bl.getField());
    }

    public static boolean getFieldBool(BaseLevel bl) {
        return config.getBoolean(bl.getField());
    }

    public enum Chat {
        CHAT_FORMAT_ENABLED("chat.formatting_enabled"),
        CHAT_FORMAT("chat.format");

        private final String field;

        Chat(String field) {
            this.field = field;
        }

        public String getField() {
            return this.field;
        }
    }

    public enum Tab {
        TAB_FORMAT_ENABLED("tab.formatting_enabled"),
        TAB_FORMAT("tab.format");

        private final String field;

        Tab(String field) {
            this.field = field;
        }

        public String getField() {
            return this.field;
        }
    }

    public enum BaseLevel {
        PLUGIN_PREFIX("prefix");

        private final String field;

        BaseLevel(String field) {
            this.field = field;
        }

        public String getField() {
            return this.field;
        }
    }
}