package com.github.natanfoleto.kabanamanopla.utils;

import com.github.natanfoleto.kabanamanopla.entities.Jewel;
import com.github.natanfoleto.kabanamanopla.entities.User;
import com.github.natanfoleto.kabanamanopla.storages.JewelStorage;
import com.github.natanfoleto.kabanamanopla.storages.UserStorage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;
import java.util.Map;

import static com.github.natanfoleto.kabanamanopla.loaders.MenuLoader.*;
import static com.github.natanfoleto.kabanamanopla.loaders.SettingsLoader.*;

public class InventoryUtils {
    public static Inventory createPrincipalMenu() {
        Inventory inv = Bukkit.createInventory(
                null,
                getPrincipal().getInt("Tamanho"),
                getPrincipal().getString("Nome")
        );

        for (String item : getPrincipal().getConfigurationSection("Itens").getKeys(false)) {
            ItemStack itemStack;

            boolean customSkull = getPrincipal().getBoolean("Itens." + item + ".CustomSkull");

            int itemSlot = getPrincipal().getInt("Itens." + item + ".Slot");
            String itemName = getPrincipal().getString("Itens." + item + ".Name");
            List<String> itemLore = getPrincipal().getStringList("Itens." + item + ".Lore");

            if (customSkull) {
                String url = getPrincipal().getString("Itens." + item + ".URL");

                itemStack = ItemUtils.createSkull(itemName, itemLore, url);
            } else {
                int itemId = getPrincipal().getInt("Itens." + item + ".Id");
                int itemData = getPrincipal().getInt("Itens." + item + ".Data");

                itemStack = ItemUtils.createItem(
                        itemName,
                        itemLore,
                        itemId,
                        (short) itemData
                );
            }

            inv.setItem(itemSlot, itemStack);
        }

        return inv;
    }

    public static Inventory createAjudaMenu() {
        Inventory inv = Bukkit.createInventory(
                null,
                getAjuda().getInt("Tamanho"),
                getAjuda().getString("Nome")
        );

        for (String item : getAjuda().getConfigurationSection("Itens").getKeys(false)) {
            ItemStack itemStack;

            boolean customSkull = getAjuda().getBoolean("Itens." + item + ".CustomSkull");

            int itemSlot = getAjuda().getInt("Itens." + item + ".Slot");
            String itemName = getAjuda().getString("Itens." + item + ".Name");
            List<String> itemLore = getAjuda().getStringList("Itens." + item + ".Lore");

            if (customSkull) {
                String url = getAjuda().getString("Itens." + item + ".URL");

                itemStack = ItemUtils.createSkull(itemName, itemLore, url);
            } else {
                int itemId = getAjuda().getInt("Itens." + item + ".Id");
                int itemData = getAjuda().getInt("Itens." + item + ".Data");

                itemStack = ItemUtils.createItem(
                        itemName,
                        itemLore,
                        itemId,
                        (short) itemData
                );
            }

            inv.setItem(itemSlot, itemStack);
        }

        return inv;
    }

    public static Inventory createManoplaMenu(Player player) {
        User user = UserStorage.getUsers().get(player.getDisplayName());

        Inventory inv = Bukkit.createInventory(
                null,
                getManopla().getInt("Tamanho"),
                getManopla().getString("Nome")
        );

        // Cria os itens normais (precisa ser primeiro, para as jóias terem prioridade)
        for (String item : getManopla().getConfigurationSection("Itens").getKeys(false)) {
            ItemStack itemStack;

            boolean customSkull = getManopla().getBoolean("Itens." + item + ".CustomSkull");

            int itemSlot = getManopla().getInt("Itens." + item + ".Slot");
            String itemName = getManopla().getString("Itens." + item + ".Name");
            List<String> itemLore = getManopla().getStringList("Itens." + item + ".Lore");

            if (customSkull) {
                String url = getManopla().getString("Itens." + item + ".URL");

                itemStack = ItemUtils.createSkull(itemName, itemLore, url);
            } else {
                int itemId = getManopla().getInt("Itens." + item + ".Id");
                int itemData = getManopla().getInt("Itens." + item + ".Data");

                itemStack = ItemUtils.createItem(
                        itemName,
                        itemLore,
                        itemId,
                        (short) itemData
                );
            }

            inv.setItem(itemSlot, itemStack);
        }

        // Cria as jóias no menu conforme as jóias existente no jogo
        // Precisa ser por último para as jóias sobrescreverem os itens normais
        for (Map.Entry<String, Jewel> jewel : JewelStorage.getJewels().entrySet()) {
            String key = jewel.getKey();

            ItemStack item;

            if (user.getJewels().contains(key)) {
                boolean customSkull = getJewels().getBoolean("Joias." + key + ".Item.CustomSkull");
                String itemUrl = getJewels().getString("Joias." + key + ".Item.URL");
                int itemId = getJewels().getInt("Joias." + key + ".Item.ID");
                int itemData = getJewels().getInt("Joias." + key + ".Item.Data");
                String itemName = getJewels().getString("Joias." + key + ".Item.Name");
                List<String> itemLore = getJewels().getStringList("Joias." + key + ".Item.CustomLore");

                if (customSkull) {
                    item = ItemUtils.createSkull(itemName, itemLore, itemUrl);
                } else {
                    item = ItemUtils.createItem(
                            itemName,
                            itemLore,
                            itemId,
                            (short) itemData
                    );
                }
            } else {
                boolean customSkull = getManopla().getBoolean("Itens." + key + ".CustomSkull");

                if (customSkull) {
                    item = ItemUtils.createSkull(
                            getManopla().getString("Itens." + key + ".Name"),
                            getManopla().getStringList("Itens." + key + ".Lore"),
                            getManopla().getString("Itens." + key + ".URL")
                    );
                } else {
                    item = ItemUtils.createItem(
                            getManopla().getString("Itens." + key + ".Name"),
                            getManopla().getStringList("Itens." + key + ".Lore"),
                            getManopla().getInt("Itens." + key + ".Id"),
                            (short) getManopla().getInt("Itens." + key + ".Data")
                    );
                }
            }

            inv.setItem(getManopla().getInt("Itens." + key + ".Slot"), item);
        }

        return inv;
    }

    public static int getEmptySlots(Player p) {
        PlayerInventory inventory = p.getInventory();

        ItemStack[] cont = inventory.getContents();

        int i = 0;

        for (ItemStack item : cont)
            if (item != null && item.getType() != Material.AIR)
                i++;

        return 36 - i;
    }

    public static int getFirstEmptySlot(Player p) {
        PlayerInventory inventory = p.getInventory();

        ItemStack[] cont = inventory.getContents();

        int i = 0;

        for (ItemStack item : cont) {
            if (i >= 36) return -1;

            if (item != null && item.getType() != Material.AIR)
                i++;
            else
                return i;
        }

        return -1;
    }
}
