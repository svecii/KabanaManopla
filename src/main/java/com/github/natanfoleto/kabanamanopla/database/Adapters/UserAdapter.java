package com.github.natanfoleto.kabanamanopla.database.Adapters;

import com.github.natanfoleto.kabanamanopla.entities.User;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAdapter {
    public static User adapter(ResultSet rs) throws SQLException {
        final String key = rs.getString("key");
        final String json = rs.getString("json");

        Player player = Bukkit.getPlayer(key);

        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);

        return user;
    }
}
