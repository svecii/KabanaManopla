package com.github.natanfoleto.kabanamanopla.managers;

import com.github.natanfoleto.kabanamanopla.entities.Jewel;
import com.github.natanfoleto.kabanamanopla.storages.JewelStorage;
import com.github.natanfoleto.kabanamanopla.utils.ItemUtils;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static com.github.natanfoleto.kabanamanopla.loaders.SettingsLoader.getJewels;

public class JewelManager {
    public static void loadJewelStorage() {
        for (String jewelKey : getJewels().getConfigurationSection("Joias").getKeys(false)) {
            String name = getJewels().getString("Joias." + jewelKey + ".Nome");

            boolean customSkull = getJewels().getBoolean("Joias." + jewelKey + ".Item.CustomSkull");
            String itemUrl = getJewels().getString("Joias." + jewelKey + ".Item.URL");
            int itemId = getJewels().getInt("Joias." + jewelKey + ".Item.Id");
            int itemData = getJewels().getInt("Joias." + jewelKey + ".Item.Data");
            String itemName = getJewels().getString("Joias." + jewelKey + ".Item.Name");
            List<String> itemLore = getJewels().getStringList("Joias." + jewelKey + ".Item.Lore");

            List<String> enableCommands = getJewels().getStringList("Joias." + jewelKey + ".Buff.Ativar");
            List<String> disableCommands = getJewels().getStringList("Joias." + jewelKey + ".Buff.Desativar");

            ItemStack item;

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


            Jewel jewel = new Jewel(jewelKey, name, item, enableCommands, disableCommands);

            JewelStorage.create(jewelKey, jewel);
        }
    }
}
