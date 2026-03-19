package utils;

import java.io.InputStream;


// Utility to read files from resources folder

public class FileUtils {
	
    public static String readFromResources(String filePath) {

        try (InputStream is = 
                FileUtils.class.getClassLoader().getResourceAsStream(filePath)) {

            if (is == null) {
                throw new RuntimeException("File not found in resources: " + filePath);
            }

            return new String(is.readAllBytes());

        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }
    }
    
   
}