package testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import auth.AuthType;
import base.BaseTest;
import enums.HttpStatusCode;
import helpers.*;

import io.restassured.response.Response;
import utils.DataGenerator;

/**
 * Test class for negative test cases related to Pet API.
 * <p>
 * This class contains tests that check for expected failures (e.g., invalid pet
 * ID, invalid payload, non-existing pet deletion) to ensure the system behaves
 * correctly with invalid input.
 * </p>
 */
public class PetNegativeTests extends BaseTest {

	/**
	 * Test case to fetch a pet using an invalid pet ID.
	 * <p>
	 * This test verifies that when an invalid pet ID is provided, the API should
	 * respond with a 404 status code.
	 * </p>
	 */

	@Test
	public void getPetWithInvalidId() {

		// Generate an invalid pet ID using the data generator
		long invalidId = DataGenerator.getInvalidId();

		// Send request to get pet by invalid ID
		Response response = PetClient.getPetById(requestSpec, invalidId, AuthType.NONE);

		// Assert that the status code is 404 (NOT_FOUND)
		Assert.assertEquals(response.getStatusCode(), HttpStatusCode.NOT_FOUND.getCode(),
				"Expected 404 for invalid pet ID");
	}

	/**
	 * Test case to create a pet using an invalid payload.
	 * <p>
	 * This test ensures that when an invalid payload is provided while creating a
	 * pet, the API responds with an appropriate error status code (e.g., 400 Bad
	 * Request).
	 * </p>
	 */

	@Test
	public void createPetWithInvalidPayload() {

		// Generate random data for a new pet, but create an invalid payload
		long categoryId = DataGenerator.getRandomId();
		long tagId = DataGenerator.getRandomId();
		String name = DataGenerator.getRandomName();
		String status = DataGenerator.getRandomStatus().name();

		// Get the invalid payload using the helper method
		String payload = ReusableMethods.getInvalidAddPayload(categoryId, tagId, name, status);

		// Send request to create pet with invalid payload
		Response response = PetClient.createPetFromJson(requestSpec, payload, AuthType.NONE);
		// Assert that the status code indicates a client error (e.g., 400 Bad Request)
		Assert.assertTrue(response.getStatusCode() >= HttpStatusCode.BAD_REQUEST.getCode(),
				"Unexpected status code  for invalid payload");
	}

	/**
	 * Test case to delete a non-existing pet.
	 * <p>
	 * This test ensures that when trying to delete a pet with an invalid ID (one
	 * that does not exist), the API should respond with a 404 status code.
	 * </p>
	 */
	@Test
	public void deleteNonExistingPet() {
		// Generate an invalid pet ID for deletion
		long invalidId = DataGenerator.getInvalidId();
		// Send request to delete pet with invalid ID
		Response response = PetClient.deletePetById(requestSpec, invalidId, AuthType.NONE);
		// Assert that the status code is 404 (NOT_FOUND)
		Assert.assertEquals(response.getStatusCode(), HttpStatusCode.NOT_FOUND.getCode(),
				"Unexpected status code for deleting non-existing pet");
	}

}
