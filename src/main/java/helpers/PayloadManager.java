package helpers;

import utils.ConfigReader;
import utils.FileUtils;

public class PayloadManager {

    public static String getAddPetPayload() {
        return FileUtils.readFromResources(
                ConfigReader.get("add.pet.payload"));
    }

    public static String getUpdatePetPayload() {
        return FileUtils.readFromResources(
                ConfigReader.get("update.pet.payload"));
    }

    public static String getInvalidPetPayload() {
        return FileUtils.readFromResources(
                ConfigReader.get("invalid.pet.payload"));
    }
}