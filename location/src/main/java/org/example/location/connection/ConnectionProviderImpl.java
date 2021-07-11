package org.example.location.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProviderImpl  implements ConnectionProvider{
    public Connection getConnection() throws SQLException {
        Properties properties = loadProperties();
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        return DriverManager.getConnection(url, username, password);
    }

    private Properties loadProperties(){
        Properties properties = new Properties();
        try(InputStream in = getClass().getClassLoader().getResourceAsStream("database.properties")){
            properties.load(in);
        } catch (IOException ex){
            throw new UncheckedIOException(ex);
        }
        return properties;
    }
}
