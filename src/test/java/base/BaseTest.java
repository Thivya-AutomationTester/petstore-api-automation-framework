package base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.DataProvider;

import auth.AuthType;
import enums.PetStatus;
import helpers.PetClient;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;

/**
 * Base test class that sets up the necessary environment for REST API tests.
 * <p>
 * This class handles the setup and cleanup for tests by configuring the request
 * specifications (including logging), reading configuration values, and
 * managing resources like created pets.
 * </p>
 */
@Listeners(listeners.ExtentListener.class)
public class BaseTest {
	/** ThreadLocal variable to hold request specifications for each thread. */
	protected ThreadLocal<RequestSpecification> requestSpec = new ThreadLocal<>();

	/** ThreadLocal variable to hold request specifications for each thread. */
	protected ThreadLocal<List<Long>> createdPetIds = ThreadLocal.withInitial(ArrayList::new);

	/**
	 * Setup method that runs before each test method.
	 * <p>
	 * This method reads the environment configuration (such as base URL and base
	 * path), sets up the request specifications for API calls, configures logging
	 * for requests and responses, and prepares a log file for each test method.
	 * </p>
	 *
	 * @param result the test result containing method details for naming the log
	 *               file
	 * @throws FileNotFoundException if there is an error in setting up log files
	 */
	@BeforeMethod
	public void setup(ITestResult result) throws FileNotFoundException {

		// Read active environment (e.g., qa, sit) from config
		String env = ConfigReader.get("env");
		String baseUrl = ConfigReader.get(env + ".base.url");
		String basePath = ConfigReader.get(env + ".base.path");

		// Get current test class name and method name for logging
		String className = this.getClass().getSimpleName();
		String methodName = result.getMethod().getMethodName();
		long timestamp = System.currentTimeMillis();

		// Thread ID for unique logging per thread
		String threadId = String.valueOf(Thread.currentThread().getId());

		// Create logs directory if it not exist
		File logDir = new File("logs");
		if (!logDir.exists()) {
			logDir.mkdirs();
		}
		// Log file name formatting: <class>_<method>_<threadId>_<timestamp>.log
		String logFileName = String.format("logs/%s_%s_thread_%s_%d.log", className, methodName, threadId, timestamp);

		// Create a PrintStream to write logs to the specified log file
		PrintStream log = new PrintStream(new FileOutputStream(logFileName));

		// Build request specification with base URL, base path, and logging filters
		RequestSpecification spec = new RequestSpecBuilder().setContentType("application/json")
				.setAccept("application/json").setBaseUri(baseUrl).setBasePath(basePath)
				.addFilter(RequestLoggingFilter.logRequestTo(log)).addFilter(ResponseLoggingFilter.logResponseTo(log))
				.build();

		// Set ThreadLocal value for request specification
		requestSpec.set(spec);

	}

	/**
	 * DataProvider for pet status values. Provides data for tests that need
	 * different pet statuses.
	 * <p>
	 * This method returns all the values from the {@link PetStatus} enum, which are
	 * used in test methods to check various pet statuses.
	 * </p>
	 *
	 * @return an array of pet statuses
	 */

	@DataProvider(name = "statusProvider")
	public Object[][] statusProvider() {

		// Get all pet statuses from the PetStatus enum
		PetStatus[] statuses = PetStatus.values();

		// Prepare the data array for DataProvider
		Object[][] data = new Object[statuses.length][1];

		// Fill the data array with pet status values
		for (int i = 0; i < statuses.length; i++) {
			data[i][0] = statuses[i].name();
		}

		return data;
	}

	/**
	 * Cleanup method that runs after each test method.
	 * <p>
	 * This method deletes any pets that were created during the test execution,
	 * ensuring that tests don't leave behind any resources. It uses the list of
	 * created pet IDs and attempts to delete them using the {@link PetClient}.
	 * </p>
	 */
	@AfterMethod
	public void cleanup() {

		// Retrieve the list of created pet IDs from ThreadLocal
		List<Long> ids = createdPetIds.get();

		// If there are any pets created, attempt to delete them
		if (!ids.isEmpty()) {
			for (Long petId : ids) {
				try {
					// Delete the pet by ID (no authentication in this case)
					PetClient.deletePetById(requestSpec, petId, AuthType.NONE);
				} catch (Exception e) {
					// Log any errors encountered during cleanup
					System.out.println("Cleanup failed for ID: " + petId);
				}
			}
			// Log any errors encountered during cleanup
			ids.clear();
		}
	}
}
