package com.github.natanfoleto.kabanamanopla.loaders;

import com.github.natanfoleto.kabanamanopla.helpers.YamlHelper;
import org.bukkit.configuration.file.FileConfiguration;

public class SettingsLoader {
    static YamlHelper config;
    static YamlHelper messages;
    static YamlHelper jewels;

    public static void run() {
        config = new YamlHelper(null, "config", false);
        messages = new YamlHelper(null, "messages", false);
        jewels = new YamlHelper(null, "joias", false);
    }

    public static FileConfiguration getConfig() { return config.getFile(); }
    public static  FileConfiguration getMessages() { return messages.getFile(); }
    public static  FileConfiguration getJewels() { return jewels.getFile(); }
}
