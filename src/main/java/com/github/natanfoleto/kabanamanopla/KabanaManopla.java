package com.github.natanfoleto.kabanamanopla;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class KabanaManopla extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§f[Kabana] Carregando KabanaManopla v1.0");

        Bukkit.getConsoleSender().sendMessage("§f[Kabana] Carregado com sucesso.");
    }

    @Override
    public void onDisable() { Bukkit.getConsoleSender().sendMessage("§f[Kabana] KabanaManopla encerrado."); }

    public static KabanaManopla getInstance() { return getPlugin(KabanaManopla.class); }
}