package helpers;

import java.util.List;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Pet;

// Common reusable methods for creating pets and payload manipulation

public class ReusableMethods {
	
    // Create a pet and return its ID, also add to list for cleanup
	
	public static long createPetAndGetId(RequestSpecification requestSpec,List<Long> createdPetIds, String payload) {

		Response response = PetClient.createPet(requestSpec,payload);
		response.then().statusCode(200);

		long id = response.as(Pet.class).getId();

		createdPetIds.add(id);

		return id;
	}
	
	// Dynamically generate add pet payload
	
	public static String getAddPayload(Long CategoryId,String name,String status,boolean invalidForTest) {
		String categoryIdStr = String.valueOf(CategoryId);
	     if (invalidForTest) {
	    	 categoryIdStr += "L";
		    }
		 String addPayload = PayloadManager.getAddPetPayload().replace("{{categoryId}}",categoryIdStr).replace("{{name}}", name)
				.replace("{{status}}",status);
		return addPayload;

	}
	

}
