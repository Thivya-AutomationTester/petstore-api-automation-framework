package testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import helpers.*;

import io.restassured.response.Response;
import pojo.Pet;
import utils.DataGenerator;

public class PetTests extends BaseTest {
	

	@Test
	public void createPet() {

		String payload = PayloadManager.getAddPetPayload().replace("{{name}}", DataGenerator.getRandomName())
				.replace("{{status}}", DataGenerator.getRandomStatus().name());

		// post:: new pet created and validated the response code
		Pet expectedPet = PetClient.createPet(requestSpec, payload).as(Pet.class);

		long id = expectedPet.getId();
		System.out.println("*********add done***************************");
		// Get:: retrieved the createdPet using petid

		Pet actualPet = PetClient.getPetById(requestSpec, id);
		System.out.println("************retrieval done************************");
		// Asserting if the retrieved pet matches the details of the created pet
		PetValidator.validateResponse(actualPet, expectedPet);
		System.out.println("************validation done************************");

		// delete pet(cleanup)
		PetClient.deletePetById(requestSpec, id);
		System.out.println("************cleanup done************************");

	}

	@Test
	public void updatePet() {

		// Create first
		String createPayload = PayloadManager.getAddPetPayload()
				.replace("{{name}}", DataGenerator.getRandomName())
				.replace("{{status}}", DataGenerator.getRandomStatus().name());

		Pet created = PetClient.createPet(requestSpec, createPayload).as(Pet.class);

		long id = created.getId();
		System.out.println(id);
		System.out.println("*********add done***************************");

		// put:: update the pet
	
		String updatedBody = PayloadManager.getUpdatePetPayload().replace("{{id}}", String.valueOf(id));
		Pet updatedPet = PetClient.updatePet(requestSpec, updatedBody).as(Pet.class);
		System.out.println("***********update done*************************");
		// Get:: retrieve the updatedPet and check response
		Pet actualAfterUpdate = PetClient.getPetById(requestSpec, id);
		System.out.println("************retrieval done************************");
		// validate response
		PetValidator.validateResponse(actualAfterUpdate, updatedPet);
		System.out.println("************validation done************************");
		// delete pet(cleanup)
		PetClient.deletePetById(requestSpec, id);
		System.out.println("**************clean up done**********************");
	}

	@Test(dataProvider = "statusProvider")
	public void getPetByStatus(String status) {

	
		// check if all returned pets have the requested status and assert the schema
		Response response = PetClient.getPetsByStatus(requestSpec, status);
		System.out.println("************retrieval done************************");
		// Schema validation
		PetValidator.validateSchema(response, "schema/schema.json");
		System.out.println("************validation done************************");
	}

	@Test
	public void deletePet() {
		// Create first
		String createPayload = PayloadManager.getAddPetPayload()
				.replace("{{name}}", DataGenerator.getRandomName())
				.replace("{{status}}", DataGenerator.getRandomStatus().name());

		Pet created = PetClient.createPet(requestSpec, createPayload).as(Pet.class);

		long id = created.getId();
		System.out.println("************add done************************");
		// deletePet
		PetClient.deletePetById(requestSpec, id);
		System.out.println("************delete done************************");
		// validated after deleting
		Response response = PetClient.getPetByIdResponse(requestSpec, id);
		Assert.assertEquals(response.getStatusCode(), 404);
		System.out.println("************validation done************************");

	}

}
