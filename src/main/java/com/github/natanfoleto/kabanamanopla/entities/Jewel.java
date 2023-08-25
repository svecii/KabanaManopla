package com.github.natanfoleto.kabanamanopla.entities;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Jewel {
    private String id;
    private String name;
    private ItemStack item;
    private final List<String> enableCommands;
    private final List<String> disableCommands;

    public Jewel(
            String id,
            String name,
            ItemStack item,
            List<String> enableCommands,
            List<String> disableCommands
    ) {
        this.id = id;
        this.name = name;
        this.item = item;
        this.enableCommands = enableCommands;
        this.disableCommands = disableCommands;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ItemStack getItem() { return item; }
    public void setItem(ItemStack item) { this.item = item; }

    public List<String> getEnableCommands() { return enableCommands; }

    public List<String> getDisableCommands() { return disableCommands; }
}
