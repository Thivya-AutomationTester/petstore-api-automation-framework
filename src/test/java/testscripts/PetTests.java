package testscripts;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import auth.AuthType;
import base.BaseTest;
import enums.HttpStatusCode;
import helpers.*;

import io.restassured.response.Response;
import pojo.Pet;
import utils.ConfigReader;
import utils.DataGenerator;
import listeners.RetryAnalyzer;

/**
 * Test class for performing CRUD operations on pets in the Petstore API.
 * <p>
 * This class contains test cases to create, update, retrieve, and delete pets.
 * It also includes validation for various aspects such as response time, status
 * codes and response content.
 * </p>
 */
public class PetTests extends BaseTest {

	/**
	 * Test case to create a new pet and then retrieve it to validate the creation.
	 * <p>
	 * This test first creates a pet using the POST endpoint, then verifies that the
	 * pet was created by retrieving it using the GET endpoint. The response time
	 * and status code are also validated.
	 * </p>
	 */

	@Test(retryAnalyzer = RetryAnalyzer.class)
	public void createPet() {

		// Post:: Create a new pet using the /pet POST endpoint

		long categoryId = DataGenerator.getRandomId();
		long tagId = DataGenerator.getRandomId();
		String name = DataGenerator.getRandomName();
		String status = DataGenerator.getRandomStatus().name();

		Pet pet = PetFactory.createPetObject(categoryId, tagId, name, status);
		Response response = PetClient.createPet(requestSpec, pet, AuthType.NONE);

		// Validate the response
		long maxTime = Long.parseLong(ConfigReader.get("max.response.time"));
		PetValidator.validateStatusAndTime(response, HttpStatusCode.OK.getCode(), maxTime);

		// Get::Retrieve the created pet using the /pet/{petId} GET endpoint
		Pet expectedPet = response.as(Pet.class);
		long id = expectedPet.getId();
		createdPetIds.get().add(id);

		Response res = PetClient.getPetById(requestSpec, id, AuthType.NONE);
		Pet actualPet = res.as(Pet.class);

		// Assert that the retrieved pet matches the details of the created pet
		PetValidator.validateResponse(actualPet, expectedPet);

	}

	/**
	 * Test case to update an existing pet's details.
	 * <p>
	 * This test first creates a pet, then updates its details using the PUT
	 * endpoint. After updating, it retrieves the pet and verifies that the changes
	 * have been applied correctly.
	 * </p>
	 */
	@Test(retryAnalyzer = RetryAnalyzer.class)
	public void updatePet() {

		// Post:: Create a new pet using the /pet POST endpoint
		long id = PetFactory.createPetAndGetId(requestSpec, createdPetIds);

		// put: Update the created pet's details
		long categoryId = DataGenerator.getRandomId();
		long tagId = DataGenerator.getRandomId();
		String name = DataGenerator.getRandomName();
		String status = DataGenerator.getRandomStatus().name();

		Pet updatedPet = PetFactory.createPetObject(categoryId, tagId, name, status);
		updatedPet.setId(id);

		// Put:: Update pet using the PUT endpoint
		Response response = PetClient.updatePet(requestSpec, updatedPet, AuthType.NONE);

		// Validate response time and status code
		long maxTime = Long.parseLong(ConfigReader.get("max.response.time"));
		PetValidator.validateStatusAndTime(response, HttpStatusCode.OK.getCode(), maxTime);

		// Get:: Retrieve the pet to verify that the updates have been applied correctly
		Response res = PetClient.getPetById(requestSpec, id, AuthType.NONE);
		Pet actualAfterUpdate = res.as(Pet.class);

		// Validate that the updated pet matches the expected updated pet details
		PetValidator.validateResponse(actualAfterUpdate, updatedPet);

	}

	/**
	 * Test case to retrieve pets by their status.
	 * <p>
	 * This test queries the /pet/findByStatus endpoint for pets of a specific
	 * status, and validates the response to ensure that all retrieved pets have the
	 * correct status. Additionally, it checks for any missing required fields and
	 * validates the response schema.
	 * </p>
	 * 
	 * @param status The status of the pets to retrieve (provided via DataProvider).
	 */
	@Test(dataProvider = "statusProvider")
	public void getPetByStatus(String status) {

		// GET: Query the /pet/findByStatus endpoint with the given pet status
		Response response = PetClient.getPetsByStatus(requestSpec, status, AuthType.NONE);

		// Validate response time and status code
		long maxTime = Long.parseLong(ConfigReader.get("max.response.time"));
		PetValidator.validateStatusAndTime(response, HttpStatusCode.OK.getCode(), maxTime);

		// Validate that all retrieved pets have the expected status
		PetValidator.validateStatusForAll(response, status);

		// Ensure that all pets contain 'name' and 'photoUrls' fields
		List<Map<String, Object>> missingPets = PetValidator.getPetsMissingNameOrPhotoUrls(response);
		Assert.assertTrue(missingPets.isEmpty(), "Some pets are missing 'name' or 'photoUrls': " + missingPets);

		// Validate response schema against the expected JSON schema
		PetValidator.validateSchema(response, ConfigReader.get("schema.pet.getSchema"));

	}

	 /**
     * Test case to delete an existing pet.
     * <p>
     * This test first creates a pet, then deletes it using the DELETE endpoint. After deletion,
     * it attempts to retrieve the pet again to ensure that it no longer exists.
     * </p>
     */
	
	@Test(retryAnalyzer = RetryAnalyzer.class)
	public void deletePet() {
		 // POST: Create a new pet to be deleted
		long id = PetFactory.createPetAndGetId(requestSpec, createdPetIds);

		// DELETE: Delete the created pet using its ID
		Response deleteResponse = PetClient.deletePetById(requestSpec, id, AuthType.NONE);
		
		// Validate response time and status code
		long maxTime = Long.parseLong(ConfigReader.get("max.response.time"));
		PetValidator.validateStatusAndTime(deleteResponse, HttpStatusCode.OK.getCode(), maxTime);
		
		// Remove the pet ID from the createdPetIds list after deletion
		createdPetIds.get().remove(id);

		 // Verify that the pet has been deleted by trying to retrieve it
		Response response = PetClient.getPetById(requestSpec, id, AuthType.NONE);
		PetValidator.validatePetNotFound(response);
	}

}
