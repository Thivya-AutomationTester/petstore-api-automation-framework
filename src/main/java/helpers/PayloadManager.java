package helpers;

import utils.ConfigReader;
import utils.FileUtils;

//Reads JSON payloads from resources
public class PayloadManager {

    public static String getAddPetPayload() {
        return FileUtils.readFromResources(
                ConfigReader.get("add.pet.payload"));
    }

    public static String getUpdatePetPayload() {
        return FileUtils.readFromResources(
                ConfigReader.get("update.pet.payload"));
    }

   
}