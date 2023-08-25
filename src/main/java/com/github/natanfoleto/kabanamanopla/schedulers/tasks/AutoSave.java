package com.github.natanfoleto.kabanamanopla.schedulers.tasks;

import com.github.natanfoleto.kabanamanopla.KabanaManopla;
import com.github.natanfoleto.kabanamanopla.database.repositories.UserRepository;
import com.github.natanfoleto.kabanamanopla.entities.User;
import com.github.natanfoleto.kabanamanopla.storages.UserStorage;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class AutoSave extends BukkitRunnable {
    @Override
    public void run() {
        KabanaManopla.getInstance().getLogger().info("Iniciando auto-save.");

        for (Map.Entry<String, User> user : UserStorage.getUsers().entrySet()) {
            UserRepository.updateUser(user.getValue());
        }

        KabanaManopla.getInstance().getLogger().info("Todos os dados foram salvos.");
    }
}
