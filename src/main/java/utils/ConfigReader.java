package utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * A utility class for reading configuration properties from a properties file.
 * <p>
 * This class loads the properties from the "config/config.properties" file and
 * provides a method to access the properties by key.
 * </p>
 */

public class ConfigReader {

	/**
	 * The properties object that holds the key-value pairs loaded from the config
	 * file
	 */
	private static Properties properties;

	/**
	 * Static block that initializes the properties object by loading the
	 * "config/config.properties" file from the classpath.
	 * <p>
	 * The properties file is loaded once when the class is first loaded, ensuring
	 * the properties are available for the lifetime of the application.
	 * </p>
	 * <p>
	 * If the properties file is not found or there is an error during loading, a
	 * runtime exception is thrown.
	 * </p>
	 */
	static {
		try {
			InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("config/config.properties");
			properties = new Properties();
			properties.load(is);

		} catch (Exception e) {
			throw new RuntimeException("Failed to load config file");
		}
	}

	/**
	 * Retrieves the value associated with the given key from the configuration
	 * file.
	 * <p>
	 * If the key is not found in the properties file, this method will return null.
	 * </p>
	 *
	 * @param key the key for which the value needs to be retrieved
	 * @return the value associated with the specified key, or null if the key is
	 *         not found
	 */
	public static String get(String key) {
		return properties.getProperty(key);
	}
}
