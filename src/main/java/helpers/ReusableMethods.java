package helpers;

import java.util.List;

import enums.HttpStatusCode;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Category;
import pojo.Pet;
import pojo.Tag;
import utils.DataGenerator;

// Common reusable methods for creating pets and payload manipulation

public class ReusableMethods {

	// Create a pet and return its ID, also add to list for cleanup

	public static long createPetAndGetId(RequestSpecification requestSpec, List<Long> createdPetIds) {

		long categoryId = DataGenerator.getRandomId();
		long tagId = DataGenerator.getRandomId();
		String name = DataGenerator.getRandomName();
		String status= DataGenerator.getRandomStatus().name();

		Pet createPet = ReusableMethods.createPetObject(categoryId, tagId, name, status);

		Response response = PetClient.createPet(requestSpec, createPet);
		response.then().statusCode(HttpStatusCode.OK.getCode());

		long id = response.as(Pet.class).getId();

		createdPetIds.add(id);

		return id;
	}

	// Dynamically generate invalid add pet payload

	public static String getInvalidAddPayload(Long CategoryId, Long tagId, String name, String status) {
		
		String tag = String.valueOf(tagId);
		String addPayload = PayloadManager.getAddPetPayload().replace("{{categoryId}}", CategoryId+ "L")
				.replace("{{tagId}}", tag).replace("{{name}}", name).replace("{{status}}", status);
		return addPayload;

	}
	
	// setting values to pet object
	public static Pet createPetObject(Long categoryId, Long tagId, String name, String status) {

		Category category = new Category();
		category.setId(categoryId);
		category.setName("Domestic");

		Tag tag = new Tag();
		tag.setId(tagId);
		tag.setName("petCategory");

		Pet pet = new Pet();
		pet.setCategory(category);
		pet.setName(name);
		pet.setPhotoUrls(List.of("http://google.com/cats", "http://google.com/dogs"));
		pet.setTags(List.of(tag));
		pet.setStatus(status);

		return pet;
	}

}
