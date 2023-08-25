package com.github.natanfoleto.kabanamanopla.storages;

import com.github.natanfoleto.kabanamanopla.entities.Jewel;

import java.util.HashMap;
import java.util.Map;

public class JewelStorage {
    public static Map<String, Jewel> jewels = new HashMap<>();

    public static void create(String name, Jewel jewel) {
        jewels.put(name, jewel);
    }

    public static Map<String, Jewel> getJewels() { return jewels; }

    public static String getNames() {
        return "[" + String.join(", ", jewels.keySet()) + "]";
    }
}
