package utils;

import java.io.InputStream;
import java.util.Properties;

//Reads configuration properties from config/config.properties

public class ConfigReader {

	private static Properties properties;

    static {
        try {
            InputStream is = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("config/config.properties");

            properties = new Properties();
            properties.load(is);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config file");
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
