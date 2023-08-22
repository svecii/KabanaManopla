package com.github.natanfoleto.kabanamanopla.database;

import com.github.natanfoleto.kabanamanopla.KabanaManopla;
import com.github.natanfoleto.kabanamanopla.entities.Database;
import com.github.natanfoleto.kabanamanopla.helpers.FileHelper;
import com.github.natanfoleto.kabanamanopla.storages.DatabaseStorage;

import java.io.File;
import java.sql.*;
import java.util.Objects;

public class DatabaseFactory {
    private static final File file = new File(KabanaManopla.getInstance().getDataFolder(), "database");

    public static Connection createConnection() {
        Database database = DatabaseStorage.getDatabase();

        switch (database.getType()) {
            case "MYSQL":
                return buildMYSQL(database);
            case "SQLITE":
                return buildSQLite(database);
            default:
                throw new UnsupportedOperationException("Database type unsupported!");
        }
    }

    private static Connection buildMYSQL(Database database) {
        try {
            return DriverManager.getConnection(
                    database.getUrl(),
                    database.getUser(),
                    database.getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection buildSQLite(Database database) {
        try {
            Class.forName("org.sqlite.JDBC");

            FileHelper.createFileIfNotExists(new File(file, Objects.requireNonNull(database.getFilename())));

            return DriverManager.getConnection(database.getUrl());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection(Connection conn, PreparedStatement stmt) {
        closeConnection(conn);

        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs) {
        closeConnection(conn, stmt);

        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

