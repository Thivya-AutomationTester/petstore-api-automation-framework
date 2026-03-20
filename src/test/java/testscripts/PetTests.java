package testscripts;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import enums.HttpStatusCode;
import helpers.*;

import io.restassured.response.Response;
import pojo.Pet;
import utils.DataGenerator;
import utils.RetryAnalyzer;

public class PetTests extends BaseTest {

	
	private Response response;
	

	@Test(retryAnalyzer = RetryAnalyzer.class)
	public void createPet() {

		// post:: Create a new pet using the /pet POST endpoint
		
		long categoryId = DataGenerator.getRandomId();
		long tagId = DataGenerator.getRandomId();
		String name = DataGenerator.getRandomName();
		String status = DataGenerator.getRandomStatus().name();

		Pet pet = ReusableMethods.createPetObject(categoryId, tagId, name, status);
		response = PetClient.createPet(requestSpec, pet);

		// Validate the response
		response.then().statusCode(HttpStatusCode.OK.getCode());

		// Get::Retrieve the created pet using the /pet/{petId} GET endpoint
		Pet expectedPet = response.as(Pet.class);
		long id = expectedPet.getId();
		createdPetIds.add(id);
		
		Response res = PetClient.getPetById(requestSpec,id);
		Pet actualPet =res.as(Pet.class);

		// Assert that the retrieved pet matches the details of the created pet
		PetValidator.validateResponse(actualPet, expectedPet);

	}

	@Test(retryAnalyzer = RetryAnalyzer.class)
	public void updatePet() {

		// post:: Create a new pet using the /pet POST endpoint
		long id=ReusableMethods.createPetAndGetId(requestSpec,createdPetIds);

		// put:: Update an existing pet using the /pet PUT endpoint
		long categoryId = DataGenerator.getRandomId();
		long tagId = DataGenerator.getRandomId();
		String name = DataGenerator.getRandomName();
		String status = DataGenerator.getRandomStatus().name();

		Pet updatedPet = ReusableMethods.createPetObject(categoryId, tagId, name, status);
		updatedPet.setId(id);
		
		Response response = PetClient.updatePet(requestSpec, updatedPet);
		response.then().statusCode(HttpStatusCode.OK.getCode());
	
		// Get:: Retrieve the pet to verify that the updates have been applied correctly
		Response res = PetClient.getPetById(requestSpec, id);
		Pet actualAfterUpdate = res.as(Pet.class);

		// Validate the response
		PetValidator.validateResponse(actualAfterUpdate, updatedPet);

	}

	@Test(dataProvider = "statusProvider")
	public void getPetByStatus(String status) {

		// Query the /pet/findByStatus GET endpoint with different statuses
		Response response = PetClient.getPetsByStatus(requestSpec,status);
		response.then().assertThat().statusCode(HttpStatusCode.OK.getCode());

		// Validate that the response contains only pets with the requested status
		response.then().assertThat().body("status", everyItem(equalTo(status)));

		// Assert the response schema matches the expected structure.
		List<Map<String, Object>> missingPets = PetValidator.getPetsMissingNameOrPhotoUrls(response);
		Assert.assertTrue(missingPets.isEmpty(), "Some pets are missing 'name' or 'photoUrls': " + missingPets);

		PetValidator.validateSchema(response, "schema/schema.json");

	}

	@Test(retryAnalyzer = RetryAnalyzer.class)
	public void deletePet() {
		//Create Pet
		long id=ReusableMethods.createPetAndGetId(requestSpec,createdPetIds);
				
		// Delete a pet using the /pet/{petId} DELETE endpoint
		Response deleteResponse=PetClient.deletePetById(requestSpec,id);
		deleteResponse.then().statusCode(HttpStatusCode.OK.getCode());
		createdPetIds.remove(id);

		// validate the response
		Response response = PetClient.getPetById(requestSpec,id);
		response.then().assertThat().statusCode(HttpStatusCode.NOT_FOUND.getCode()).body("message",equalTo("Pet not found"));

	}

}
