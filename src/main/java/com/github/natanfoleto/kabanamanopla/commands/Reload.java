package com.github.natanfoleto.kabanamanopla.commands;

import com.github.natanfoleto.kabanamanopla.loaders.JewelLoader;
import com.github.natanfoleto.kabanamanopla.loaders.MenuLoader;
import com.github.natanfoleto.kabanamanopla.loaders.SettingsLoader;
import com.github.natanfoleto.kabanamanopla.schedulers.tasks.AutoSave;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static com.github.natanfoleto.kabanamanopla.loaders.SettingsLoader.*;

public class Reload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!sender.hasPermission("kabanamanopla.reload")) {
            sender.sendMessage(getMessages().getString("Outras.SemPermissao"));

            return false;
        }

        sender.sendMessage(getMessages().getString("Plugin.Recarregar"));

        // Salva todos os dados da memória no database
        new AutoSave().run();

        // Recarrega as configurações dos arquivos.yml
        SettingsLoader.run();
        MenuLoader.run();
        JewelLoader.run();

        sender.sendMessage(getMessages().getString("Plugin.Recarregado"));

        return true;
    }
}
