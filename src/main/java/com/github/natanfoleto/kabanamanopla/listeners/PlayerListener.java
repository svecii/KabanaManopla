package com.github.natanfoleto.kabanamanopla.listeners;

import com.github.natanfoleto.kabanamanopla.database.repositories.UserRepository;
import com.github.natanfoleto.kabanamanopla.entities.Jewel;
import com.github.natanfoleto.kabanamanopla.entities.User;
import com.github.natanfoleto.kabanamanopla.managers.UserManager;

import com.github.natanfoleto.kabanamanopla.schedulers.cooldowns.JewelCooldown;
import org.bukkit.event.EventHandler;
import com.github.natanfoleto.kabanamanopla.storages.JewelStorage;
import com.github.natanfoleto.kabanamanopla.storages.UserStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

import static com.github.natanfoleto.kabanamanopla.loaders.SettingsLoader.*;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        UserManager.createUserStorage(player);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        User user = UserStorage.getUsers().get(player.getDisplayName());

        if (e.getAction().toString().contains("RIGHT_CLICK") && e.hasItem()) {
            ItemStack heldItem = e.getItem();

            if (heldItem.hasItemMeta()) {
                ItemMeta itemMeta = heldItem.getItemMeta();

                if (itemMeta.hasDisplayName()) {
                   String itemName = itemMeta.getDisplayName();

                   for (Map.Entry<String, Jewel> jewel : JewelStorage.getJewels().entrySet()) {
                       String key = jewel.getKey();
                       Jewel value = jewel.getValue();

                       if (itemName.equals(value.getItem().getItemMeta().getDisplayName())) {
                           e.setCancelled(true);

                           // Verifica se o jogador já ativou esta jóia
                           if (user.getJewels().contains(key)) {
                               player.sendMessage(getMessages().getString("Joia.PlayerJaAtivou"));

                               return;
                           }

                           // Verifica se o jogador pode ativar a jóia, ou se precisa esperar o cooldown
                           if (!JewelCooldown.can(player, key)) return;

                           // Adiciona a jóia na lista de jóias ativas do user
                           user.getJewels().add(key);
                           UserRepository.updateUser(user);

                           // Roda os comandos para ativar os buffs desta jóia
                           for (String eCommand : value.getEnableCommands()) {
                               Bukkit.dispatchCommand(player, eCommand.replace("{player}", player.getName()));
                           }

                           // Se tiver mais de 1 item, decrementa, senão limpa o item da mão
                           if (heldItem != null && heldItem.getAmount() > 1) heldItem.setAmount(heldItem.getAmount() - 1);
                           else player.setItemInHand(null);

                           player.sendMessage(
                                   getMessages().getString("Joia.PlayerAtivou")
                                           .replace("{jewel}", value.getName())
                           );

                           // Verifica se o jogador tem todas as jóias ativadas
                           if (user.getJewels().size() == JewelStorage.getJewels().size()) {
                               List<String> enableCommands = getConfig().getStringList("EstalarDeDedos.Buff.Ativar");
                               List<String> announcement = getConfig().getStringList("EstalarDeDedos.Anuncio");


                               // Roda os comandos para ativar os buffs do estalar de dedos
                               for (String eCommand : enableCommands) {
                                   Bukkit.dispatchCommand(player, eCommand.replace("{player}", player.getName()));
                               }

                               if (getConfig().getBoolean("EstalarDeDedos.Animacoes.Raio")) {
                                   World world = player.getWorld();
                                   Location playerLocation = player.getLocation();

                                   world.strikeLightning(playerLocation);
                               }

                               announcement.stream()
                                       .map(message -> message.replace("{name}", player.getDisplayName()))
                                       .forEach(player::sendMessage);
                           }
                       }
                    }
                }
            }
        }
    }
}
