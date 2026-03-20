package helpers;

import org.testng.Assert;

import io.restassured.response.Response;
import pojo.Pet;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Helper class to validate API responses

public class PetValidator {

	// Validate that actual pet matches expected pet

	public static void validateResponse(Pet actual, Pet expected) {

		Assert.assertEquals(actual.getId(), expected.getId());
		Assert.assertEquals(actual.getName(), expected.getName());
		Assert.assertEquals(actual.getStatus(), expected.getStatus());

		Assert.assertEquals(actual.getCategory().getId(), expected.getCategory().getId());
		Assert.assertEquals(actual.getCategory().getName(), expected.getCategory().getName());

		Assert.assertEquals(actual.getPhotoUrls(), expected.getPhotoUrls());

		Assert.assertEquals(actual.getTags().size(), expected.getTags().size());

		for (int i = 0; i < actual.getTags().size(); i++) {
			Assert.assertEquals(actual.getTags().get(i).getId(), expected.getTags().get(i).getId());

			Assert.assertEquals(actual.getTags().get(i).getName(), expected.getTags().get(i).getName());
		}
	}

	// Returns a list of pets that are missing "name" or "photoUrls".
	public static List<Map<String, Object>> getPetsMissingNameOrPhotoUrls(Response response) {
		List<Map<String, Object>> pets = response.jsonPath().getList("");
		List<Map<String, Object>> missingPets = new ArrayList<>();
		 int index = 0;

		for (Map<String, Object> pet : pets) {
			if (!pet.containsKey("name") || !pet.containsKey("photoUrls")) {
				  System.out.println("Warning: Pet at index " + index + " is missing 'name' or 'photoUrls': " + pet);

				missingPets.add(pet);
			}
			 index++;

		}

		return missingPets;
	}

	// Validate response against JSON schema
	public static void validateSchema(Response response, String schemaFile) {
		response.then().body(matchesJsonSchemaInClasspath(schemaFile));
	}

}