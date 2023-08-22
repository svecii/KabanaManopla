package com.github.natanfoleto.kabanamanopla.entities;

public class Database {
    private final String type;
    private String filename;
    private String host;
    private String user;
    private String password;
    private String databaseName;
    private String url;

    public Database(String type) {
        this.type = type;
    }

    public String getType() { return type; }

    public String getFilename() { return filename; }
    public void setFilnename(String filename) { this.filename = filename; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDatabaseName() { return databaseName; }
    public void setDatabaseName(String databaseName) { this.databaseName = databaseName; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}

