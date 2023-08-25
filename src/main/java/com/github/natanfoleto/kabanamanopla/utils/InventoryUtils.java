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
    public static Inventory createManoplaMenu(Player player) {
        User user = UserStorage.getUsers().get(player.getDisplayName());

        Inventory inv = Bukkit.createInventory(
                null,
                getManopla().getInt("Tamanho"),
                getManopla().getString("Nome")
        );

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
                item = ItemUtils.createItem(
                        getManopla().getString("Itens." + key + ".Name"),
                        getManopla().getStringList("Itens." + key + ".Lore"),
                        getManopla().getInt("Itens." + key + ".Id"),
                        (short) getManopla().getInt("Itens." + key + ".Data")
                );
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
