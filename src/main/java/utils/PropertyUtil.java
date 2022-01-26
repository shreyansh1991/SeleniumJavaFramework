package utils;

import factories.EnvFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
    public static Properties loadPropertiesUsingClassLoader(String fileName) {
        Properties prop = new Properties();
        try (InputStream input = EnvFactory.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                throw new RuntimeException("unable to find config_stage.properties");
            }
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}