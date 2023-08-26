package com.github.natanfoleto.kabanamanopla.commands;

import com.cryptomorin.xseries.messages.Titles;
import com.github.natanfoleto.kabanamanopla.entities.Jewel;
import com.github.natanfoleto.kabanamanopla.storages.JewelStorage;
import com.github.natanfoleto.kabanamanopla.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import static com.github.natanfoleto.kabanamanopla.loaders.SettingsLoader.*;

public class GiveJewel implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("kabanamanopla.give")) {
            sender.sendMessage(getMessages().getString("Outras.SemPermissao"));

            return false;
        }

        if (args.length < 2) {
            sender.sendMessage("§cUso: /darjoia <jogador> <joia>");

            return false;
        }

        String targetName = args[0];
        String jewelName = args[1];

        Player target = Bukkit.getPlayer(targetName);

        if (target == null) {
            sender.sendMessage(getMessages().getString("Outras.JogadorOffline"));

            return false;
        }

        Jewel jewel = JewelStorage.getJewels().get(jewelName);

        if (jewel == null) {
            sender.sendMessage(getMessages().getString("Joia.NaoEncontrada"));
            sender.sendMessage("§cJóias disponíveis: §f" + JewelStorage.getNames());

            return false;
        }

        PlayerInventory inv = target.getInventory();

        int emptySlots = InventoryUtils.getEmptySlots(target);

        if (1 > emptySlots) {
            sender.sendMessage(getMessages().getString("Joia.SemEspacoNoInventario"));

            return false;
        }

        inv.addItem(jewel.getItem());

        sender.sendMessage(
                getMessages().getString("Joia.Entregue")
                        .replace("{jewel}", jewel.getName())
                        .replace("{name}", targetName)
        );

        return true;
    }
}
