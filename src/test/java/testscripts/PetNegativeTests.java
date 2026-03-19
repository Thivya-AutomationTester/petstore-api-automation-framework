package testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import helpers.*;

import io.restassured.response.Response;
import utils.DataGenerator;

public class PetNegativeTests extends BaseTest {

	
	@Test
	public void getPetWithInvalidId() {
		
		// Get pet with invalid ID
		long invalidId = DataGenerator.getInvalidId();
		Response response = PetClient.getPetById(requestSpec,invalidId);
		//AssertResponse
		Assert.assertEquals(response.getStatusCode(), 404, "Expected 404 for invalid pet ID");
	}

	
	@Test
	public void createPetWithInvalidPayload() {
		// Create pet with invalid payload
		String payload = ReusableMethods.getAddPayload(DataGenerator.getRandomId(),DataGenerator.getRandomName(),DataGenerator.getRandomStatus().name(),true);
		Response response = PetClient.createPet(requestSpec,payload);
		//AssertResponse
		Assert.assertTrue(response.getStatusCode() >= 400, "Unexpected status code  for invalid payload");
	}

	
	@Test
	public void deleteNonExistingPet() {
		// Delete non-existing pet
		long invalidId = DataGenerator.getInvalidId();
		Response response = PetClient.deletePetById(requestSpec,invalidId);
		//AssertResponse
		Assert.assertEquals(response.getStatusCode(),404, "Unexpected status code for deleting non-existing pet");
	}

}
