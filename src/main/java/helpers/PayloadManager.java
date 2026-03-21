package helpers;

import utils.ConfigReader;
import utils.FileUtils;

/**
 * Utility class for managing JSON payloads used in API requests.
 * <p>
 * Provides methods to read JSON payloads from the resources folder
 * based on configuration keys defined in {@link ConfigReader}.
 * </p>
 */
public class PayloadManager {
	
	 /**
     * Retrieves the JSON payload for adding a pet.
     * <p>
     * The file path is obtained from the configuration key {@code payloads.pet.payload}.
     * </p>
     *
     * @return the JSON payload as a String
     */

    public static String getAddPetPayload() {
        return FileUtils.readFromResources(
                ConfigReader.get("payloads.pet.payload"));
    }
    public static String getschemaPayload() {
        return FileUtils.readFromResources(
                ConfigReader.get("schema.pet.getSchema"));
    }

     
}