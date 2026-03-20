package helpers;

import utils.ConfigReader;
import utils.FileUtils;

//Reads JSON payloads from resources
public class PayloadManager {

    public static String getAddPetPayload() {
        return FileUtils.readFromResources(
                ConfigReader.get("payloads.pet.payload"));
    }

     
}