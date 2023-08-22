package com.github.natanfoleto.kabanamanopla;

import com.github.natanfoleto.kabanamanopla.listeners.PlayerListener;
import com.github.natanfoleto.kabanamanopla.loaders.DatabaseLoader;
import com.github.natanfoleto.kabanamanopla.loaders.SettingsLoader;

import com.github.natanfoleto.kabanamanopla.loaders.UserLoader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class KabanaManopla extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("§f[KabanaManopla] Carregando KabanaManopla v1.0");

        runLoaders();
        registerListeners();

        getLogger().info("§f[KabanaManopla] Carregado com sucesso.");
    }

    @Override
    public void onDisable() { getLogger().info("§f[Kabana] KabanaManopla encerrado."); }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public void runLoaders() {
        SettingsLoader.run();
        DatabaseLoader.run();
        UserLoader.run();
    }
    public static KabanaManopla getInstance() { return getPlugin(KabanaManopla.class); }
}