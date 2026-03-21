package helpers;

import static io.restassured.RestAssured.given;

import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import auth.AuthManager;
import auth.AuthType;
import enums.EndPoints;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import listeners.ExtentListener;
import pojo.Pet;
import reports.ExtentLogger;

/**
 * Client for interacting with the Pet API endpoints.
 * <p>
 * Provides methods for common HTTP operations (POST, GET, PUT, DELETE) to
 * create, update, delete, and fetch pet information in the Petstore API.
 * </p>
 */

public class PetClient {

	/**
	 * Generic method to log the request and response details to ExtentReports.
	 *
	 * @param test     the ExtentTest instance for logging
	 * @param method   the HTTP method (e.g., "GET", "POST")
	 * @param endpoint the API endpoint
	 * @param body     the body of the request (as a String)
	 * @param response the response received from the API
	 */
	private static void logRequestAndResponse(ExtentTest test, String method, String endpoint, String body,
			Response response) {

		// Log request to ExtentReports
		ExtentLogger.logRequest(test, method, endpoint, body);

		// Log response to ExtentReports
		ExtentLogger.logResponse(test, response);
	}

	/**
	 * POST - Creates a new pet in the Petstore.
	 *
	 * @param requestSpec the ThreadLocal holding the request specification
	 * @param body        the pet object to be added
	 * @param authType    the authentication type (e.g., API_KEY, BEARER)
	 * @return the response from the API
	 */
	public static Response createPet(ThreadLocal<RequestSpecification> requestSpec, Pet body, AuthType authType) {
		RequestSpecification request = AuthManager.applyAuth(requestSpec, authType);

		String bodyAsString = serializeBody(body);

		// Send request
		Response response = given().spec(request).body(body).when().post(EndPoints.PET.getPath()).then().extract()
				.response();

		// Log request and response
		logRequestAndResponse(ExtentListener.testThread.get(), "POST", EndPoints.PET.getPath(), bodyAsString, response);

		return response;
	}

	/**
	 * GET - Retrieves a pet by its ID.
	 *
	 * @param requestSpec the ThreadLocal holding the request specification
	 * @param id          the ID of the pet to fetch
	 * @param authType    the authentication type (e.g., API_KEY, BEARER)
	 * @return the response from the API
	 */
	public static Response getPetById(ThreadLocal<RequestSpecification> requestSpec, long id, AuthType authType) {
		RequestSpecification request = AuthManager.applyAuth(requestSpec, authType);

		// No body for GET request
		String bodyAsString = "";

		// Send request
		Response response = given().spec(request).pathParam("petid", id).when().get(EndPoints.PET_BY_ID.getPath())
				.then().extract().response();

		// Resolve path param using ReusableMethods to get full endpoint with actual pet
		// ID
		String resolvedEndpoint = PetFactory.resolve(EndPoints.PET_BY_ID.getPath(), "petid", id);

		// Log request and response
		logRequestAndResponse(ExtentListener.testThread.get(), "GET", resolvedEndpoint, bodyAsString, response);

		return response;
	}

	/**
	 * PUT - Updates an existing pet.
	 *
	 * @param requestSpec the ThreadLocal holding the request specification
	 * @param body        the updated pet object
	 * @param authType    the authentication type (e.g., API_KEY, BEARER)
	 * @return the response from the API
	 */
	public static Response updatePet(ThreadLocal<RequestSpecification> requestSpec, Pet body, AuthType authType) {
		RequestSpecification request = AuthManager.applyAuth(requestSpec, authType);

		String bodyAsString = serializeBody(body);

		// Send request
		Response response = given().spec(request).body(body).when().put(EndPoints.PET.getPath()).then().extract()
				.response();

		// Log request and response
		logRequestAndResponse(ExtentListener.testThread.get(), "PUT", EndPoints.PET.getPath(), bodyAsString, response);

		return response;
	}

	/**
	 * DELETE-Deletes a pet by its ID.
	 *
	 * @param requestSpec the ThreadLocal holding the request specification
	 * @param id          the ID of the pet to delete
	 * @param authType    the authentication type (e.g., API_KEY, BEARER)
	 * @return the response from the API
	 */
	public static Response deletePetById(ThreadLocal<RequestSpecification> requestSpec, long id, AuthType authType) {
		RequestSpecification request = AuthManager.applyAuth(requestSpec, authType);

		// No body for DELETE request
		String bodyAsString = "";

		// Send request
		Response response = given().spec(request).pathParam("petid", id).when().delete(EndPoints.PET_BY_ID.getPath())
				.then().extract().response();

		// Resolve path param using ReusableMethods to get full endpoint with actual pet
		// ID
		String resolvedEndpoint = PetFactory.resolve(EndPoints.PET_BY_ID.getPath(), "petid", id);

		// Log request and response
		logRequestAndResponse(ExtentListener.testThread.get(), "DELETE", resolvedEndpoint, bodyAsString, response);

		return response;
	}

	/**
	 * GET- Finds pets by their status.
	 *
	 * @param requestSpec the ThreadLocal holding the request specification
	 * @param status      the status of the pets to retrieve (e.g., "available")
	 * @param authType    the authentication type (e.g., API_KEY, BEARER)
	 * @return the response from the API
	 */
	public static Response getPetsByStatus(ThreadLocal<RequestSpecification> requestSpec, String status,
			AuthType authType) {
		RequestSpecification request = AuthManager.applyAuth(requestSpec, authType);

		// No body for GET request
		String bodyAsString = "";

		// Send request
		Response response = given().spec(request).queryParam("status", status).when()
				.get(EndPoints.FIND_BY_STATUS.getPath()).then().extract().response();

		// Log request and response
		logRequestAndResponse(ExtentListener.testThread.get(), "GET", EndPoints.FIND_BY_STATUS.getPath(), bodyAsString,
				response);

		return response;
	}

	/**
	 * Creates a new pet from a JSON string.
	 *
	 * @param requestSpec the ThreadLocal holding the request specification
	 * @param body        the JSON string representing the pet
	 * @param authType    the authentication type (e.g., API_KEY, BEARER)
	 * @return the response from the API
	 */
	public static Response createPetFromJson(ThreadLocal<RequestSpecification> requestSpec, String body,
			AuthType authType) {
		RequestSpecification request = AuthManager.applyAuth(requestSpec, authType);

		// Send request
		Response response = given().spec(request).body(body).when().post(EndPoints.PET.getPath()).then().extract()
				.response();

		// Log request and response
		logRequestAndResponse(ExtentListener.testThread.get(), "POST", EndPoints.PET.getPath(), body, response);

		return response;
	}

	/**
	 * Serializes the Pet object to a JSON string.
	 *
	 * @param body the Pet object to serialize
	 * @return the serialized JSON string
	 */
	private static String serializeBody(Pet body) {
		String bodyAsString = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			bodyAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			bodyAsString = "{}";
		}
		return bodyAsString;
	}
}