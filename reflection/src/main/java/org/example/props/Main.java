package org.example.props;

import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties = PropertiesUtils.loadProperties("reflection/config/app.properties");

        PropertiesMapper propertiesMapper = new PropertiesMapper();
        AppProperties appProperties = propertiesMapper.map(AppProperties.class, properties);

        System.out.println("appProperties = " + appProperties);
    }
}
