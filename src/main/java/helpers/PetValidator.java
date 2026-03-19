package helpers;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pojo.Pet;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PetValidator {

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

    public static void validateSchema(Response response, String schemaFile) {
        response.then().body(matchesJsonSchemaInClasspath(schemaFile));
    }
    public static String getStatus(String response) {
		JsonPath js = new JsonPath(response);
		return js.getString("status");
	}
    
}