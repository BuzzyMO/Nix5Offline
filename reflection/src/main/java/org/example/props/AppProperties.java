package org.example.props;

public class AppProperties {
    @PropertyKey(key = "application.name")
    public String appName;
    @PropertyKey(key = "connections.limit")
    public int maxConnections;
    @PropertyKey(key = "server.port")
    public int port;
    @PropertyKey(key = "db.url")
    public String urlDB;

    @Override
    public String toString() {
        return "AppProperties{" +
                "appName='" + appName + '\'' +
                ", maxConnections=" + maxConnections +
                ", port=" + port +
                ", urlDB='" + urlDB + '\'' +
                '}';
    }
}
