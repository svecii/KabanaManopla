package com.github.natanfoleto.kabanamanopla.commands;

import com.github.natanfoleto.kabanamanopla.database.repositories.UserRepository;
import com.github.natanfoleto.kabanamanopla.entities.Jewel;
import com.github.natanfoleto.kabanamanopla.entities.User;
import com.github.natanfoleto.kabanamanopla.storages.JewelStorage;
import com.github.natanfoleto.kabanamanopla.storages.UserStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static com.github.natanfoleto.kabanamanopla.loaders.SettingsLoader.*;

public class DeactivateJewel implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("kabanamanopla.deactivatejewel")) {
            sender.sendMessage(getMessages().getString("Outras.SemPermissao"));

            return false;
        }

        if (args.length < 2) {
            sender.sendMessage("§cUso: /desativarjoia <jogador> <joia>");

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

        User user = UserStorage.getUsers().get(targetName);

        if (!user.getJewels().contains(jewelName)) {
            sender.sendMessage(getMessages().getString("Joia.StaffNaoAtivou"));

            return false;
        }

        user.getJewels().remove(jewelName);
        UserRepository.updateUser(user);

        // Roda os comandos para desativar os buffs desta jóia
        for (String dCommand : jewel.getDisableCommands()) {
            Bukkit.dispatchCommand(sender, dCommand.replace("{player}", targetName));
        }

        // Verifica se o jogador não tem mais todas as jóias ativadas
        if (user.getJewels().size() == JewelStorage.getJewels().size() - 1) {
            List<String> disableCommands = getConfig().getStringList("EstalarDeDedos.Buff.Desativar");

            // Roda os comandos para desativar os buffs do estalar de dedos
            for (String dCommand : disableCommands) {
                Bukkit.dispatchCommand(sender, dCommand.replace("{player}", targetName));
            }
        }

        sender.sendMessage(
                getMessages().getString("Joia.StaffDesativou")
                        .replace("{jewel}", jewel.getName())
                        .replace("{name}", targetName)
        );

        return true;
    }
}
