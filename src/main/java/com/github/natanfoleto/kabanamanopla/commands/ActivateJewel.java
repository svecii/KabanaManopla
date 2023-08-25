package com.github.natanfoleto.kabanamanopla.commands;

import com.github.natanfoleto.kabanamanopla.database.repositories.UserRepository;
import com.github.natanfoleto.kabanamanopla.entities.Jewel;
import com.github.natanfoleto.kabanamanopla.entities.User;
import com.github.natanfoleto.kabanamanopla.storages.JewelStorage;
import com.github.natanfoleto.kabanamanopla.storages.UserStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;

import java.util.List;

import static com.github.natanfoleto.kabanamanopla.loaders.SettingsLoader.getConfig;
import static com.github.natanfoleto.kabanamanopla.loaders.SettingsLoader.getMessages;

public class ActivateJewel implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("kabanamanopla.activatejewel")) {
            sender.sendMessage(getMessages().getString("Outras.SemPermissao"));

            return false;
        }

        if (args.length < 2) {
            sender.sendMessage("§cUso: /ativarjoia <jogador> <joia>");

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

        if (user.getJewels().contains(jewelName)) {
            sender.sendMessage(getMessages().getString("Joia.StaffJaAtivou"));

            return false;
        }

        user.getJewels().add(jewelName);
        UserRepository.updateUser(user);

        // Roda os comandos para ativar os buffs desta jóia
        for (String eCommand : jewel.getEnableCommands()) {
            Bukkit.dispatchCommand(sender, eCommand.replace("{player}", targetName));
        }

        // Verifica se o jogador tem todas as jóias ativadas
        if (user.getJewels().size() == JewelStorage.getJewels().size()) {
            List<String> enableCommands = getConfig().getStringList("EstalarDeDedos.Buff.Ativar");
            List<String> announcement = getConfig().getStringList("EstalarDeDedos.Anuncio");


            // Roda os comandos para ativar os buffs do estalar de dedos
            for (String eCommand : enableCommands) {
                Bukkit.dispatchCommand(sender, eCommand.replace("{player}", targetName));
            }

            if (getConfig().getBoolean("EstalarDeDedos.Animacoes.Raio")) {
                World world = target.getWorld();
                Location playerLocation = target.getLocation();

                world.strikeLightning(playerLocation);
            }

            announcement.stream()
                    .map(message -> message.replace("{name}", targetName))
                    .forEach(sender::sendMessage);
        }

        sender.sendMessage(
                getMessages().getString("Joia.StaffAtivou")
                        .replace("{jewel}", jewel.getName())
                        .replace("{name}", targetName)
        );

        return true;
    }
}
