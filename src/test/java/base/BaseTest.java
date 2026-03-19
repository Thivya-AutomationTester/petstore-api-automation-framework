package base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;

import org.testng.annotations.*;
import org.testng.annotations.DataProvider;

import enums.PetStatus;
import helpers.PetClient;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;

// Sets up RequestSpecification, logging, and cleanup

public class BaseTest {

	protected RequestSpecification requestSpec;
	protected List<Long> createdPetIds = new ArrayList<>();

	@BeforeClass
	public void setup() throws FileNotFoundException {
		String className = this.getClass().getSimpleName();
		// Create logs folder if it not exist
		File logDir = new File("logs");
		if (!logDir.exists()) {
			logDir.mkdirs();
		}
		// Log requests and responses to file
		PrintStream log = new PrintStream(new FileOutputStream("logs/logging_" + className + ".txt"));

		// Build request specification with base URL, headers and logging filters for requests and responses
		requestSpec = new RequestSpecBuilder().setContentType("application/json").setAccept("application/json")
				.setBaseUri(ConfigReader.get("base.url")).setBasePath(ConfigReader.get("base.path"))
				.addFilter(RequestLoggingFilter.logRequestTo(log)).addFilter(ResponseLoggingFilter.logResponseTo(log))
				.build();

	}

	//DataProvider for PetStatus
	
	@DataProvider(name = "statusProvider")
	public Object[][] statusProvider() {
		PetStatus[] statuses = PetStatus.values();

		Object[][] data = new Object[statuses.length][1];

		for (int i = 0; i < statuses.length; i++) {
			data[i][0] = statuses[i].name();
		}

		return data;
	}

	// Delete any pets created during tests
	@AfterMethod
	public void cleanup() {
		if (!createdPetIds.isEmpty()) {
			for (Long petId : createdPetIds) {
				try {
					PetClient.deletePetById(requestSpec, petId);
				} catch (Exception e) {

				}
			}
			createdPetIds.clear();
		}
	}
}
