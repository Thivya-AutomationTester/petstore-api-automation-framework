package helpers;

import static io.restassured.RestAssured.given;

import enums.EndPoints;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Pet;

// Helper class to perform all pet API requests
public class PetClient {

	// POST
	public static Response createPet(RequestSpecification spec, Pet body) {
		return given().spec(spec).body(body).when().post(EndPoints.PET.getPath()).then().extract().response();
	}

	// GET - findById
	public static Response getPetById(RequestSpecification spec, long id) {

		return given().spec(spec).pathParam("petid", id).when().get(EndPoints.PET_BY_ID.getPath()).then().extract()
				.response();
	}

	// PUT
	public static Response updatePet(RequestSpecification spec, Pet body) {

		return given().spec(spec).body(body).when().put(EndPoints.PET.getPath()).then().extract().response();
	}

	// DELETE
	public static Response deletePetById(RequestSpecification spec, long id) {

		return given().spec(spec).pathParam("petid", id).when().delete(EndPoints.PET_BY_ID.getPath()).then().extract()
				.response();

	}

	// GET - findByStatus
	public static Response getPetsByStatus(RequestSpecification spec, String status) {

		return given().spec(spec).queryParam("status", status).when().get(EndPoints.FIND_BY_STATUS.getPath()).then()
				.extract().response();
	}

	// POST
	public static Response createPetFromJson(RequestSpecification spec, String body) {
		return given().spec(spec).body(body).when().post(EndPoints.PET.getPath()).then().extract().response();
	}

}