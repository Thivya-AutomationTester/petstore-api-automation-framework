package base;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import enums.PetStatus;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.ConfigReader;

public class BaseTest {

	  protected RequestSpecification requestSpec;
	    protected ResponseSpecification responseSpec;

	@BeforeClass
	public void setup() {

		requestSpec = new RequestSpecBuilder().setContentType("application/json").setAccept("application/json")
				.setBaseUri(ConfigReader.get("base.url")).setBasePath(ConfigReader.get("base.path")).log(LogDetail.ALL)
				.build();

		responseSpec = new ResponseSpecBuilder().log(LogDetail.ALL).build();
		RestAssured.requestSpecification = requestSpec;
		RestAssured.responseSpecification = responseSpec;
	}
	
	

	@DataProvider(name = "statusProvider")
	public Object[][] statusProvider() {
		 PetStatus[] statuses = PetStatus.values();

		    Object[][] data = new Object[statuses.length][1];

		    for (int i = 0; i < statuses.length; i++) {
		        data[i][0] = statuses[i].name();
		    }

		    return data;
	}
}
