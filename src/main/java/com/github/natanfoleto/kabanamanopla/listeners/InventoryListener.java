package com.github.natanfoleto.kabanamanopla.listeners;

import com.github.natanfoleto.kabanamanopla.database.repositories.UserRepository;
import com.github.natanfoleto.kabanamanopla.entities.Jewel;
import com.github.natanfoleto.kabanamanopla.entities.User;
import com.github.natanfoleto.kabanamanopla.storages.JewelStorage;
import com.github.natanfoleto.kabanamanopla.storages.UserStorage;
import com.github.natanfoleto.kabanamanopla.utils.InventoryUtils;
import com.github.natanfoleto.kabanamanopla.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;
import java.util.Map;

import static com.github.natanfoleto.kabanamanopla.loaders.MenuLoader.*;
import static com.github.natanfoleto.kabanamanopla.loaders.SettingsLoader.getConfig;
import static com.github.natanfoleto.kabanamanopla.loaders.SettingsLoader.getMessages;

public class InventoryListener implements Listener {
    @EventHandler
    public void onClickInventory(InventoryClickEvent e) {
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)
            return;

        // Menu manopla aberto
        if (e.getView().getTitle().equalsIgnoreCase(getManopla().getString("Nome"))) {
            e.setCancelled(true);

            Player player = (Player) e.getWhoClicked();
            User user = UserStorage.getUsers().get(player.getDisplayName());

            player.playSound(player.getLocation(), Sound.CLICK, 1, 2f);

            if (e.isRightClick()) {
                for (Map.Entry<String, Jewel> jewel : JewelStorage.getJewels().entrySet()) {
                    String key = jewel.getKey();
                    Jewel value = jewel.getValue();

                    if (
                            e.getSlot() == getManopla().getInt("Itens." + key + ".Slot") &&
                            e.getCurrentItem().getItemMeta().getDisplayName() == value.getItem().getItemMeta().getDisplayName()
                    ) {
                        if (!user.getJewels().contains(key)) {
                            player.sendMessage(getMessages().getString("Joia.PlayerNaoAtivou"));

                            return;
                        }

                        int emptySlots = InventoryUtils.getEmptySlots(player);

                        if (1 > emptySlots) {
                            player.sendMessage(getMessages().getString("Joia.SemEspacoNoInventario"));

                            return;
                        }

                        user.getJewels().remove(key);
                        UserRepository.updateUser(user);

                        // Cria o item padrão da jóia não ativada
                        ItemStack defaultItem = ItemUtils.createItem(
                                getManopla().getString("Itens." + key + ".Name"),
                                getManopla().getStringList("Itens." + key + ".Lore"),
                                getManopla().getInt("Itens." + key + ".Id"),
                                (short) getManopla().getInt("Itens." + key + ".Data")
                        );

                        // Troca a jóia do menu pelo item padrão
                        e.getClickedInventory().setItem(e.getSlot(), defaultItem);

                        // Retorna o primeiro slot vazio do inventário do jogador
                        int firstEmptySlot = InventoryUtils.getFirstEmptySlot(player);

                        // Volta a jóia para o inventário do jogador
                        PlayerInventory inv = player.getInventory();

                        inv.setItem(firstEmptySlot, value.getItem());
                        player.updateInventory();

                        // Roda os comandos para desativar os buffs desta jóia
                        for (String dCommand : value.getDisableCommands()) {
                            Bukkit.dispatchCommand(player, dCommand.replace("{player}", player.getDisplayName()));
                        }

                        // Verifica se o jogador não tem mais todas as jóias ativadas
                        if (user.getJewels().size() == JewelStorage.getJewels().size() - 1) {
                            List<String> disableCommands = getConfig().getStringList("EstalarDeDedos.Buff.Desativar");

                            // Roda os comandos para desativar os buffs do estalar de dedos
                            for (String dCommand : disableCommands) {
                                Bukkit.dispatchCommand(player, dCommand.replace("{player}", player.getDisplayName()));
                            }
                        }

                        player.sendMessage(
                                getMessages().getString("Joia.PlayerDesativou")
                                        .replace("{jewel}", value.getName())
                        );

                        break;
                    }
                }
            }
        }
    }
}
