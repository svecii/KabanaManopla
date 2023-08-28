package com.github.natanfoleto.kabanamanopla.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import java.lang.reflect.Field;
import org.apache.commons.codec.binary.Base64;
import java.util.List;
import java.util.UUID;

public class ItemUtils {
    public static ItemStack createItem(
            String name,
            List<String> lore,
            Integer idplayer,
            short data
    ) {
        ItemStack icon = new ItemStack(Material.getMaterial(id), 1, data);

        ItemMeta itemMeta = icon.getItemMeta();

        if (itemMeta != null) {
            itemMeta.setDisplayName(name);
            itemMeta.setLore(lore);

            icon.setItemMeta(itemMeta);
        }

        return icon;
    }

    public static ItemStack createSkull(
            String name,
            List<String> lore,
            String url
    ) {
        ItemStack skull = new ItemStack(397, 1, (short) 3);

        if (url == null || url.isEmpty()) return skull;

        SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
        skullMeta.setDisplayName(name);
        skullMeta.setLore(lore);

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        byte[] encodedData =
            Base64.encodeBase64(
                String.format("{textures:{SKIN:{url:\"%s\"}}}", new Object[] { "http://textures.minecraft.net/texture/" + url })
                    .getBytes()
            );

        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));

        Field profileField = null;

        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException|SecurityException e) {
            e.printStackTrace();
        }

        profileField.setAccessible(true);

        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException|IllegalAccessException e) {
            e.printStackTrace();
        }

        skull.setItemMeta(skullMeta);

        return skull;
    }
}
