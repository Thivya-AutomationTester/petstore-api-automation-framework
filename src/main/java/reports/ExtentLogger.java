package reports;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;

/**
 * A class to log request and response details to an ExtentTest instance.
 * <p>
 * This class provides static methods to log detailed information about the request 
 * (including method, URL, and body) and the response (including the body of the response) 
 * during the execution of API tests using ExtentReports.
 * </p>
 */
public class ExtentLogger {


    /**
     * Logs the details of the HTTP request to the ExtentTest instance.
     * <p>
     * This method logs the HTTP method, endpoint, and request body to the ExtentTest 
     * instance associated with the current test.
     * </p>
     *
     * @param test    the ExtentTest instance to log the information to
     * @param method  the HTTP method (e.g., GET, POST, PUT, DELETE)
     * @param endpoint the API endpoint for the request
     * @param body    the request body
     */
    public static void logRequest(ExtentTest test, String method, String endpoint, String body) {
    	
    	// If the test instance is null, do nothing
    	if (test == null) return;
       
    	test.info("===== REQUEST =====");
        test.info("Body: " + body);
        test.info("method: " + method);
        test.info("Url: " + endpoint);
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
    	if (test == null) return;
       
    	test.info("===== RESPONSE =====");
        test.info("Body: " + response.getBody().asPrettyString());
    }
}