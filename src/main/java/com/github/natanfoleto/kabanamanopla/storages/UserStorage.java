package com.github.natanfoleto.kabanamanopla.storages;

import com.github.natanfoleto.kabanamanopla.entities.User;

import java.util.HashMap;
import java.util.Map;

public class UserStorage {
    private static final Map<String, User> users = new HashMap<>();

    public static void create(String name, User user) { users.put(name, user); }

    public static void destroy(String name) { users.remove(name); }

    public static Map<String, User> getUsers() { return users; }

}
