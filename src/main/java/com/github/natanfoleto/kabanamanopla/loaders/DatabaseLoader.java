package com.github.natanfoleto.kabanamanopla.loaders;

import com.github.natanfoleto.kabanamanopla.database.repositories.*;
import com.github.natanfoleto.kabanamanopla.managers.DatabaseManager;

public class DatabaseLoader {
    public static void run() {
        DatabaseManager.createDatabase();

        UserRepository.createTableUsers();
    }
}
