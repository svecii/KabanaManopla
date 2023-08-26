package com.github.natanfoleto.kabanamanopla.loaders;

import com.github.natanfoleto.kabanamanopla.helpers.YamlHelper;
import org.bukkit.configuration.file.FileConfiguration;

public class MenuLoader {
    static YamlHelper ajuda;
    static YamlHelper manopla;

    public static void run() {
        ajuda = new YamlHelper("menus", "ajuda", false);
        manopla = new YamlHelper("menus", "manopla", false);
    }

    public static FileConfiguration getAjuda() { return ajuda.getFile(); }
    public static FileConfiguration getManopla() { return manopla.getFile(); }
}
