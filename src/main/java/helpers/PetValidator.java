package helpers;

import org.testng.Assert;

import enums.HttpStatusCode;
import io.restassured.response.Response;
import pojo.Pet;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Helper class for validating API responses related to pets.
 * <p>
 * Provides methods to compare pet objects, validate response status, and check for missing fields.
 * Also includes functionality to validate responses against JSON schemas and time constraints.
 * </p>
 */

public class PetValidator {


    /**
     * Validates that the actual pet object matches the expected pet object.
     * <p>
     * Compares fields like ID, name, status, category, photo URLs, and tags.
     * </p>
     *
     * @param actual   the actual pet object returned in the response
     * @param expected the expected pet object to validate against
     */

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

	 /**
     * Returns a list of pets from the response that are missing either the "name" or "photoUrls" fields.
     * <p>
     * This method is useful for identifying incomplete pet objects in the response.
     * </p>
     *
     * @param response the API response containing pet data
     * @return a list of pets missing required fields ("name" or "photoUrls")
     */
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

	 /**
     * Validates the API response against a JSON schema.
     * <p>
     * This is useful to ensure that the response adheres to the expected structure and format.
     * </p>
     * @param response   the API response to validate
     * @param schemaFile the path to the JSON schema file in the classpath
     */
	public static void validateSchema(Response response, String schemaFile) {
		response.then().body(matchesJsonSchemaInClasspath(schemaFile));
	}
	  /**
     * Validates that the status of all pets in the response matches the expected status.
     * <p>
     * This method checks if the "status" field for all pets in the response is equal to the expected value.
     * </p>
     * @param response      the API response to validate
     * @param expectedStatus the expected status value to match
     */
	public static void validateStatusForAll(Response response, String expectedStatus) {
        response.then().assertThat().body("status", everyItem(equalTo(expectedStatus)));
    }
	/**
     * Validates the response status code and checks if the response time is within a specified limit.
     * <p>
     * This method is useful for performance testing to ensure the API responds within an acceptable time frame.
     * </p>
     *
     * @param response      the API response to validate
     * @param expectedStatus the expected HTTP status code
     * @param maxTime       the maximum allowed response time in milliseconds
     */
	public static void validateStatusAndTime(Response response, int expectedStatus, long maxTime) {
	    response.then().statusCode(expectedStatus);
	    response.then().time(org.hamcrest.Matchers.lessThan(maxTime));
	}
	  /**
     * Validates that the "Pet not found" message is returned when a pet is not found.
     * <p>
     * This method checks if the API returns the correct status code and message for a non-existing pet.
     * </p>
     *
     * @param response the API response to validate
     */
	 public static void validatePetNotFound(Response response) {
	        response.then() .assertThat().statusCode(HttpStatusCode.NOT_FOUND.getCode()).body("message", equalTo("Pet not found"));
	    }

}