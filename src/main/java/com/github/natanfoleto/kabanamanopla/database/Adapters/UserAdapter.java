package com.github.natanfoleto.kabanamanopla.database.Adapters;

import com.github.natanfoleto.kabanamanopla.entities.User;
import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAdapter {
    public static User adapter(ResultSet rs) throws SQLException {
        final String key = rs.getString("key");
        final String json = rs.getString("json");

        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);

        return user;
    }
}
