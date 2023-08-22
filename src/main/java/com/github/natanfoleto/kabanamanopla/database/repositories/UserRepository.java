package com.github.natanfoleto.kabanamanopla.database.repositories;

import com.github.natanfoleto.kabanamanopla.database.Adapters.UserAdapter;
import com.github.natanfoleto.kabanamanopla.database.DatabaseFactory;
import com.github.natanfoleto.kabanamanopla.entities.User;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class UserRepository {
    public static void createTableUsers() {
        Connection conn = DatabaseFactory.createConnection();

        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS kabanamanopla_users(" +
                    "`key` varchar(128) NOT NULL, " +
                    "`json` LONGTEXT NOT NULL" +
                    ")"
            );

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseFactory.closeConnection(conn, stmt);
        }
    }

    public static Set<User> getAllUsers() {
        Connection conn = DatabaseFactory.createConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        Set<User> users = new HashSet<>();

        try {
            stmt = conn.prepareStatement("SELECT * FROM kabanamanopla_users");

            rs = stmt.executeQuery();

            while (rs.next()) users.add(UserAdapter.adapter(rs));

            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseFactory.closeConnection(conn, stmt, rs);
        }
    }

    public static void createUser(User user) {
        Connection conn = DatabaseFactory.createConnection();

        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("REPLACE INTO kabanamanopla_users" +
                    "(`key`, `json`) " +
                    "VALUES(?, ?)"
            );

            Gson gson = new Gson();
            String json = gson.toJson(user);

            stmt.setString(1, user.getName());
            stmt.setString(2, json);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }  finally {
            DatabaseFactory.closeConnection(conn, stmt);
        }
    }
}
