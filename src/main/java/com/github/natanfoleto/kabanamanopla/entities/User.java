package com.github.natanfoleto.kabanamanopla.entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private final List<String> jewels;

    public User(String name) {
        this.name = name;
        this.jewels = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getJewels() { return jewels; }
}
