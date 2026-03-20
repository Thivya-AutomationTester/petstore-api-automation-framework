package utils;

import java.util.Random;
import java.util.UUID;

import enums.PetStatus;

/**
 * Utility class to generate dynamic test data for pets.
 * <p>
 * This class provides methods for generating random pet names, random pet statuses,
 * random pet IDs, and invalid test data, which can be used for testing purposes
 * such as creating, updating, or deleting pets.
 * </p>
 */

public class DataGenerator {

	 /** Array containing all possible pet statuses. */
	private static final PetStatus[] statuses = PetStatus.values();

	 /**
     * Generates a random pet name using a unique UUID.
     * <p>
     * This method generates a name in the format "pet_<unique ID>", where the ID
     * is the unique.
     * </p>
     *
     * @return a random pet name string
     */
	public static String getRandomName() {
		return "pet_" + Math.abs(UUID.randomUUID().getMostSignificantBits());
	}

	 /**
     * Randomly selects a valid pet status from the {@link PetStatus} enum.
     * <p>
     * The method picks a random value from the {@link PetStatus} enum, which
     * could be one of the predefined statuses such as "available", "sold", or "pending".
     * </p>
     *
     * @return a randomly selected pet status
     */
	public static PetStatus getRandomStatus() {
		return statuses[new Random().nextInt(statuses.length)];
	}

	 /**
     * Generates a unique ID using a UUID.
     * <p>
     * This method returns the randomly generated UUID, ensuring that each ID is unique.
     * </p>
     *
     * @return a random, unique pet ID
     */
	public static long getRandomId() {
		 return Math.abs(UUID.randomUUID().getMostSignificantBits());
	}

	 /**
     * Generates an invalid pet ID for testing negative scenarios.
     * <p>
     * This method simply returns a hardcoded invalid ID (-1) which can be used for tests
     * that need to check how the system behaves when an invalid ID is provided.
     * </p>
     *
     * @return an invalid pet ID (-1)
     */
	public static long getInvalidId() {
		return -1L;
	}
	  /**
     * Generates an invalid pet ID in the form of a string for testing.
     * <p>
     * This method returns a non-numeric string ("abc") which is not a valid pet ID.
     * It can be used to test scenarios where invalid string IDs are passed to the system.
     * </p>
     *
     * @return an invalid pet ID as a string ("abc")
     */
	public static String getInvalidIdAsString() {
		return "abc";
	}
}