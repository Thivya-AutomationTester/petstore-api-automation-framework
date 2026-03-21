package reports;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import utils.ConfigReader;

/**
 * A class to log request and response details to an ExtentTest instance.
 * <p>
 * This class provides static methods to log detailed information about the
 * request (including method, URL, and body) and the response (including the
 * body of the response) during the execution of API tests using ExtentReports.
 * </p>
 */
public class ExtentLogger {

	/**
	 * Logs the details of the HTTP request to the ExtentTest instance.
	 * <p>
	 * This method logs the HTTP method, endpoint, and request body to the
	 * ExtentTest instance associated with the current test.
	 * </p>
	 *
	 * @param test     the ExtentTest instance to log the information to
	 * @param method   the HTTP method (e.g., GET, POST, PUT, DELETE)
	 * @param endpoint the API endpoint for the request
	 * @param body     the request body
	 */
	public static void logRequest(ExtentTest test, String method, String endpoint, String body) {

		// If the test instance is null, do nothing
		if (test == null)
			return;
		
		// Extract base URI from config
		String env = ConfigReader.get("env");
		String baseUrl = ConfigReader.get(env + ".base.url");
		String basePath = ConfigReader.get(env + ".base.path");
	    
		// Log request section header
		test.info("===== REQUEST =====");
		
		// Log endpoint (URL path)
		test.info("Url: " + baseUrl+basePath+endpoint);
		
		// Log HTTP method type
		test.info("method: " + method);
		
		// Log request payload (can be empty for GET/DELETE)
		test.info("Body: " + body);

	}

	/**
	 * Logs the details of the HTTP response to the ExtentTest instance.
	 * <p>
	 * This method logs the body of the HTTP response to the ExtentTest instance.
	 * </p>
	 *
	 * @param test     the ExtentTest instance to log the information to
	 * @param response the HTTP response to log
	 */

	public static void logResponse(ExtentTest test, Response response) {

		// If the test instance is null, do nothing
		if (test == null)
			return;

		// Log response section header
		test.info("===== RESPONSE =====");

		// Log HTTP status code
		test.info("Status Code: " + response.getStatusCode());

		// Convert response body to string for size evaluation
		String responseBody = response.getBody().asString();

		// Skip logging if response is too large (e.g., > 2000 chars)
		if (responseBody.length() > 2000) {
			test.info("Body: [SKIPPED - Large Response | Size: " + responseBody.length() + " chars]");
		} else {
			test.info("Body: " + response.getBody().asPrettyString());
		}

	}
}