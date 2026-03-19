package testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import helpers.*;

import io.restassured.response.Response;
import utils.DataGenerator;

public class PetNegativeTests extends BaseTest {

	// Get pet with invalid ID
	@Test
	public void getPetWithInvalidId() {
		long invalidId = DataGenerator.getInvalidId();

		Response response = PetClient.getPetByIdResponse(requestSpec, invalidId);

		Assert.assertEquals(response.getStatusCode(), 404, "Expected 404 for invalid pet ID");
	}

	// Create pet with invalid payload
	@Test
	public void createPetWithInvalidPayload() {
		// String invalidPayload = ReusableMethods.getInvalidPetPayload();
		String invalidPayload = PayloadManager.getInvalidPetPayload()
				.replace("{{invalidName}}", DataGenerator.getRandomName())
				.replace("{{invalidId}}", DataGenerator.getInvalidIdAsString());
		Response response = PetClient.createPet(requestSpec, invalidPayload);
	
		Assert.assertTrue(response.getStatusCode() >= 400, "Expected error status code for invalid payload");
	}

	// Delete non-existing pet
	@Test
	public void deleteNonExistingPet() {

		long invalidId = DataGenerator.getInvalidId();

		Response response = PetClient.deletePetById(requestSpec, invalidId);

		Assert.assertTrue(response.getStatusCode() == 404, "Unexpected status code for deleting non-existing pet");
	}

}
