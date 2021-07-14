package org.example.props;

import java.io.*;
import java.util.Properties;

public class PropertiesUtils {

    public static Properties loadProperties(String path){
        Properties properties = new Properties();
        File propsFile = new File(path);
        try(Reader in = new FileReader(propsFile)){
            properties.load(in);
        } catch (IOException ex){
            throw new UncheckedIOException(ex);
        }
        return properties;
    }
}
