package com.github.natanfoleto.kabanamanopla;

import com.github.natanfoleto.kabanamanopla.commands.*;
import com.github.natanfoleto.kabanamanopla.listeners.InventoryListener;
import com.github.natanfoleto.kabanamanopla.listeners.PlayerListener;
import com.github.natanfoleto.kabanamanopla.loaders.*;

import com.github.natanfoleto.kabanamanopla.schedulers.tasks.AutoSave;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class KabanaManopla extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Carregando KabanaManopla v1.0");

        runLoaders();
        registerListeners();
        registerCommands();

        getLogger().info("Carregado com sucesso.");
    }

    @Override
    public void onDisable() {
        new AutoSave().run();

        getLogger().info("Encerrado.");
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
    }

    public void registerCommands() {
        getCommand("gauntlet").setExecutor(new Gauntlet());
        getCommand("givejewel").setExecutor(new GiveJewel());
        getCommand("activatejewel").setExecutor(new ActivateJewel());
        getCommand("deactivatejewel").setExecutor(new DeactivateJewel());
        getCommand("kabanamanopla").setExecutor(new Reload());
    }

    public void runLoaders() {
        SettingsLoader.run();
        MenuLoader.run();
        DatabaseLoader.run();
        UserLoader.run();
        JewelLoader.run();
    }

    public static KabanaManopla getInstance() { return getPlugin(KabanaManopla.class); }
}