package utils;

import java.util.Random;

import enums.PetStatus;

//Utility to generate dynamic test data

public class DataGenerator {

	private static final PetStatus[] statuses = PetStatus.values();

	// Generate a unique pet name
	public static String getRandomName() {
		return "pet_" + System.currentTimeMillis();
	}

	 // Randomly selects a valid pet status from the PetStatus enum
	public static PetStatus getRandomStatus() {
		return statuses[new Random().nextInt(statuses.length)];
	}

	// Generate a unique ID
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