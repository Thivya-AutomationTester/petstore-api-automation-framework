package testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import enums.HttpStatusCode;
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
		Assert.assertEquals(response.getStatusCode(),  HttpStatusCode.NOT_FOUND.getCode(), "Expected 404 for invalid pet ID");
	}

	
	@Test
	public void createPetWithInvalidPayload() {
		// Create pet with invalid payload
		long categoryId = DataGenerator.getRandomId();
		long tagId = DataGenerator.getRandomId();
		String name = DataGenerator.getRandomName();
		String status = DataGenerator.getRandomStatus().name();
		
		String payload = ReusableMethods.getInvalidAddPayload( categoryId,tagId,  name, status);
		
		//AssertResponse
		Response response = PetClient.createPetFromJson(requestSpec,payload);
		Assert.assertTrue(response.getStatusCode() >=  HttpStatusCode.BAD_REQUEST.getCode(), "Unexpected status code  for invalid payload");
	}

	
	@Test
	public void deleteNonExistingPet() {
		// Delete non-existing pet
		long invalidId = DataGenerator.getInvalidId();
		Response response = PetClient.deletePetById(requestSpec,invalidId);
		//AssertResponse
		Assert.assertEquals(response.getStatusCode(), HttpStatusCode.NOT_FOUND.getCode(), "Unexpected status code for deleting non-existing pet");
	}

}
