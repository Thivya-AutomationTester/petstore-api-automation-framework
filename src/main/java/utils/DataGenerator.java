package utils;

import java.util.Random;

import enums.PetStatus;

public class DataGenerator {

    private static final PetStatus[] statuses = PetStatus.values();

    public static String getRandomName() {
        return "pet_" + System.currentTimeMillis();
    }

    public static PetStatus getRandomStatus() {
        return statuses[new Random().nextInt(statuses.length)];
    }
   
    
    public static long getRandomId() {
        return System.currentTimeMillis();
    }
    
 // Invalid ID for negative tests
    public static long getInvalidId() {
        return -1L;
    }
    
        public static String getInvalidIdAsString() {
        return "abc";     
    }
}