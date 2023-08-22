package com.github.natanfoleto.kabanamanopla.loaders;

import com.github.natanfoleto.kabanamanopla.managers.UserManager;

public class UserLoader {
    public static void run() {
        UserManager.loadUserStorage();
    }
}
