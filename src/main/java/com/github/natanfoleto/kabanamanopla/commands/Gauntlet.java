package com.github.natanfoleto.kabanamanopla.commands;

import com.github.natanfoleto.kabanamanopla.utils.InventoryUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import static com.github.natanfoleto.kabanamanopla.loaders.SettingsLoader.getMessages;

public class Gauntlet implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("kabanamanopla.use")) {
            sender.sendMessage(getMessages().getString("Outras.SemPermissao"));

            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(getMessages().getString("ComandoNaoPermitidoNoConsole"));

            return false;
        }

        Inventory inv = InventoryUtils.createManoplaMenu((Player) sender);

        ((Player) sender).openInventory(inv);

        return true;
    }
}
