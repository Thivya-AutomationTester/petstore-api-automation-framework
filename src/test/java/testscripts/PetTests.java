package testscripts;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;

import org.testng.annotations.Test;

import base.BaseTest;
import helpers.*;

import io.restassured.response.Response;
import pojo.Pet;
import utils.DataGenerator;

public class PetTests extends BaseTest {

	
	private Response response;
	private String payload = ReusableMethods.getAddPayload(DataGenerator.getRandomId(),
			DataGenerator.getRandomName(),DataGenerator.getRandomStatus().name(),false);



	@Test
	public void createPet() {

		// post:: Create a new pet using the /pet POST endpoint
		response = PetClient.createPet(requestSpec,payload);

		// Validate the response
		response.then().statusCode(200);

		// Get::Retrieve the created pet using the /pet/{petId} GET endpoint
		Pet expectedPet = response.as(Pet.class);
		 long id = expectedPet.getId();
		 createdPetIds.add(id);
		
		Response res = PetClient.getPetById(requestSpec,id);
		Pet actualPet =res.as(Pet.class);

		// Assert that the retrieved pet matches the details of the created pet
		PetValidator.validateResponse(actualPet, expectedPet);

	}

	@Test
	public void updatePet() {

		//Create Pet
		 long id = ReusableMethods.createPetAndGetId(requestSpec,createdPetIds,payload);
				
		// put:: Update an existing pet using the /pet PUT endpoint
		String updatedBody = PayloadManager.getUpdatePetPayload().replace("{{id}}", String.valueOf(id));
		response = PetClient.updatePet(requestSpec,updatedBody);

		// Validate the response
		response.then().assertThat().statusCode(200);

		// Get:: Retrieve the pet to verify that the updates have been applied correctly
		Pet updatedPet = response.as(Pet.class);
		Response res = PetClient.getPetById(requestSpec,id);
		Pet actualAfterUpdate =res.as(Pet.class);
		
		PetValidator.validateResponse(actualAfterUpdate, updatedPet);

	}

	@Test(dataProvider = "statusProvider")
	public void getPetByStatus(String status) {

		// Query the /pet/findByStatus GET endpoint with different statuses
		Response response = PetClient.getPetsByStatus(requestSpec,status);
		response.then().assertThat().statusCode(200);

		// Validate that the response contains only pets with the requested status
		response.then().assertThat().body("status", everyItem(equalTo(status)));

		// Assert the response schema matches the expected structure.
		PetValidator.validateSchema(response, "schema/schema.json");

	}

	@Test
	public void deletePet() {
		//Create Pet
		 long id = ReusableMethods.createPetAndGetId(requestSpec,createdPetIds,payload);
				
		// Delete a pet using the /pet/{petId} DELETE endpoint
		PetClient.deletePetById(requestSpec,id);
		createdPetIds.remove(id);

		// validate the response
		Response response = PetClient.getPetById(requestSpec,id);
		response.then().assertThat().statusCode(404).body("message",equalTo("Pet not found"));

	}

}
