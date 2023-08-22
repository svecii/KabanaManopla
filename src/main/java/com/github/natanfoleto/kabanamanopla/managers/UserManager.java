package com.github.natanfoleto.kabanamanopla.managers;

import com.github.natanfoleto.kabanamanopla.database.repositories.UserRepository;
import com.github.natanfoleto.kabanamanopla.entities.User;
import com.github.natanfoleto.kabanamanopla.storages.UserStorage;

import org.bukkit.entity.Player;

import java.util.Set;

public class UserManager {
    public static void loadUserStorage() {
        Set<User> users = UserRepository.getAllUsers();

        for (User user : users) UserStorage.create(user.getName(), user);
    }

    public static void createUserStorage(Player player) {
        if (UserStorage.getUsers().get(player.getDisplayName()) == null) {
            User user = new User(player.getDisplayName());

            UserRepository.createUser(user);
            UserStorage.create(player.getDisplayName(), user);
        }
    }

    public static void destroyUserStorage(Player player) {
        UserStorage.destroy(player.getDisplayName());
    }
}
