package com.github.natanfoleto.kabanamanopla.loaders;

import com.github.natanfoleto.kabanamanopla.helpers.YamlHelper;
import org.bukkit.configuration.file.FileConfiguration;

public class MenuLoader {
    static YamlHelper manopla;

    public static void run() {
        manopla = new YamlHelper("menus", "manopla", false);
    }

    public static FileConfiguration getManopla() { return manopla.getFile(); }
}
