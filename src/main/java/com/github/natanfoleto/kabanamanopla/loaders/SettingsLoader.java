package com.github.natanfoleto.kabanamanopla.loaders;

import com.github.natanfoleto.kabanamanopla.helpers.YamlHelper;

public class SettingsLoader {
    static YamlHelper config;

    public static void run() {
        config = new YamlHelper(null, "config", false);
    }
}
