package helpers;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Pet;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;

public class PetClient {

	public static Response createPet(RequestSpecification spec, String body) {
		return given().body(body).when().post("/pet").then().extract().response();
	}

	public static Pet getPetById(RequestSpecification spec, long id) {

		return given().pathParam("petid", id).when().get("/pet/{petid}").then().extract().as(Pet.class);
	}

	public static Response getPetByIdResponse(RequestSpecification spec, long id) {

		return given().pathParam("petid", id).when().get("/pet/{petid}").then().extract().response();
	}

	public static Response updatePet(RequestSpecification spec, String body) {

		return given().body(body).when().put("/pet").then().extract().response();
	}

	public static Response deletePetById(RequestSpecification spec, long id) {

		return given().pathParam("petid", id).when().delete("/pet/{petid}").then().extract().response();

	}

	public static Response getPetsByStatus(RequestSpecification spec, String status) {

		return given().queryParam("status", status).when().get("/pet/findByStatus").then().statusCode(200)
				.body("status", everyItem(equalTo(status))).extract().response();
	}
}