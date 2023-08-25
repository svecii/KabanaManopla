package com.github.natanfoleto.kabanamanopla.schedulers.cooldowns;

import com.github.natanfoleto.kabanamanopla.KabanaManopla;
import com.github.natanfoleto.kabanamanopla.storages.JewelStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.Map;

import static com.github.natanfoleto.kabanamanopla.loaders.SettingsLoader.*;

public class JewelCooldown {
    private static final BukkitScheduler scheduler = Bukkit.getScheduler();

    private static final Map<String, Integer> tasks = new HashMap<>();
    private static final Map<String, Integer> cooldowns = new HashMap<>();

    public static boolean can(
            Player player,
            String jewelKey
    ) {
        String key = player.getName() + jewelKey;

        if(tasks.containsKey(key)) {
            String jewelName = JewelStorage.getJewels().get(jewelKey).getName();

            player.sendMessage(
                    getMessages().getString("Joia.EspereParaAtivar")
                            .replace("{minutes}", cooldowns.get(key).toString())
                            .replace("{jewel}", jewelName)
            );

            return false;
        }

        int taskId = scheduler.scheduleSyncRepeatingTask(KabanaManopla.getInstance(), new Runnable() {
            int cooldown = getConfig().getInt("Joia.DelayAtivarNovamente");

            @Override
            public void run() {
                if (cooldown > 0) {
                    cooldown--;
                    cooldowns.put(key, cooldown);
                }

                if (cooldown == 0) {
                    scheduler.cancelTask(tasks.get(key));
                    cooldowns.remove(key);
                    tasks.remove(key);
                }
            }
        }, 0, 1200);

        tasks.put(key, taskId);

        return true;
    }
}
