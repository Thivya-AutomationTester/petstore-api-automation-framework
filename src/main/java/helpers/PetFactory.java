package helpers;

import java.util.List;

import auth.AuthType;
import enums.HttpStatusCode;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Category;
import pojo.Pet;
import pojo.Tag;
import utils.DataGenerator;

/**
 * Common utility class containing reusable methods for creating pets and
 * manipulating payloads.
 * <p>
 * This class provides methods for creating pet objects, generating random pet
 * data, and creating API requests for adding pets.
 * </p>
 */

public class PetFactory {

	/**
	 * Creates a pet and returns its ID, while also adding it to the list for
	 * cleanup.
	 * <p>
	 * This method generates random data for a new pet and sends a request to create
	 * the pet. The pet ID is extracted from the response and returned.
	 * </p>
	 *
	 * @param requestSpec   the ThreadLocal holding the request specification
	 * @param createdPetIds the ThreadLocal list that holds IDs of created pets for
	 *                      cleanup
	 * @return the ID of the created pet
	 */

	public static long createPetAndGetId(ThreadLocal<RequestSpecification> requestSpec,
			ThreadLocal<List<Long>> createdPetIds) {

		// Generate random data for the pet

		long categoryId = DataGenerator.getRandomId();
		long tagId = DataGenerator.getRandomId();
		String name = DataGenerator.getRandomName();
		String status = DataGenerator.getRandomStatus().name();

		Pet createPet = PetFactory.createPetObject(categoryId, tagId, name, status);

		// Create the pet using the PetClient and validate status code
		Response response = PetClient.createPet(requestSpec, createPet, AuthType.NONE);
		response.then().statusCode(HttpStatusCode.OK.getCode());

		// Extract the pet ID from the response
		long id = response.as(Pet.class).getId();

		// Add the pet ID to the ThreadLocal list for cleanup
		createdPetIds.get().add(id);
		return id;
	}

	/**
	 * Generates an invalid payload for adding a pet, with dynamic values inserted.
	 * <p>
	 * This method allows for testing invalid pet creation by modifying the payload
	 * with invalid values for the specified category, tag, name, and status.
	 * </p>
	 *
	 * @param CategoryId the ID of the pet category
	 * @param tagId      the ID of the pet tag
	 * @param name       the name of the pet
	 * @param status     the status of the pet
	 * @return a String representing the invalid add pet payload
	 */

	public static String getInvalidAddPayload(Long CategoryId, Long tagId, String name, String status) {

		String tag = String.valueOf(tagId);
		String addPayload = PayloadManager.getAddPetPayload().replace("{{categoryId}}", CategoryId + "L")
				.replace("{{tagId}}", tag).replace("{{name}}", name).replace("{{status}}", status);
		return addPayload;

	}

	/**
	 * Creates a Pet object with the specified category, tag, name, and status.
	 * <p>
	 * This method builds a Pet object using the provided values, and assigns a
	 * category and tag to the pet.
	 * </p>
	 *
	 * @param categoryId the ID of the pet's category
	 * @param tagId      the ID of the pet's tag
	 * @param name       the name of the pet
	 * @param status     the status of the pet
	 * @return a Pet object populated with the given values
	 */
	public static Pet createPetObject(Long categoryId, Long tagId, String name, String status) {

		// Create and set the category for the pet
		Category category = new Category();
		category.setId(categoryId);
		category.setName("Domestic");

		// Create and set the tag for the pet
		Tag tag = new Tag();
		tag.setId(tagId);
		tag.setName("petCategory");

		// Create and set the pet object
		Pet pet = new Pet();
		pet.setCategory(category);
		pet.setName(name);
		pet.setPhotoUrls(List.of("http://google.com/cats", "http://google.com/dogs"));
		pet.setTags(List.of(tag));
		pet.setStatus(status);

		return pet;
	}

	public static String resolve(String path, String key, Object value) {
		return path.replace("{" + key + "}", String.valueOf(value));
	}

}
