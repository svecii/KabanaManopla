package com.github.natanfoleto.kabanamanopla.loaders;

import com.github.natanfoleto.kabanamanopla.helpers.YamlHelper;
import org.bukkit.configuration.file.FileConfiguration;

public class SettingsLoader {
    static YamlHelper config;

    public static void run() {
        config = new YamlHelper(null, "config", false);
    }

    public static FileConfiguration getConfig() { return config.getFile(); }
}
