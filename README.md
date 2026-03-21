## Petstore API Automation Framework
### Overview
This is a reusable automation framework for testing the /pet endpoints of the Swagger Petstore API. It’s built with Maven, Java, TestNG, and RestAssured.
### KeyFeatures
- Full CRUD automation for Pet APIs (Create, Read, Update, Delete).
- Modular design with reusable components (PetFactory, PetClient, etc.)
- Dynamic test data generation (IDs, names, statuses)
- JSON schema validation for response structure
- Response time and status code verification
- Supports positive and negative test scenarios
- Centralized logging and reporting (TestNG + Extent Reports)
- Retry mechanism for flaky tests
- Automatic cleanup of test data
### Project Structure
```
src
├── main/java
│ ├── auth        # Authentication handling(API Key, Bearer, Basic)
│ ├── enums       # Endpoints, Pet statuses, and HTTP codes
│ ├── helpers     # API client, validators, factory methods
│ ├── listeners   # TestNG listeners (Extent, Retry)
│ ├── pojo        # Request/Response models
│ ├── reports     # Extent report utilities
│ └── utils       # Config, data generator, file utils
│
├── test/java
│ ├── base        # BaseTest (setup & teardown)
│ └── testscripts # Test classes
│
└── test/resources
├── config        # config.properties
├── payloads      # JSON payloads
└── schema        # JSON schema file
```
### Covered Test Scenarios
#### 1. Create and Verify a Pet
- ```POST /pet``` → Create a new pet
- Validate response status and body
- ```GET /pet/{petId}``` → Retrieve created pet
Assert that the retrieved data matches the created data
#### 2. Update a Pet
- ```PUT /pet``` → Update existing pet
- Validate response
- Retrieve the pet again and verify updates
#### 3. Find Pets by Status
- ```GET /pet/findByStatus``` with different statuses
- Validate all returned pets match the requested status
- Perform JSON schema validation
#### 4. Delete a Pet
- ```DELETE /pet/{petId}```
- Validate response status
- Ensure the pet is removed
#### 5. Create a Pet with an invalid Payload
- ```POST /pet``` → Create a new pet with invalid Payload
-  Validate response code
#### 6. Get a pet with an invalid ID
- ```GET /pet/{petId}``` → Get a Pet with invalid ID
-  Validate response code
#### 7. Delete a non-existent Pet
- ```DELETE /pet/{petId}``` → Delete a Pet with invalid ID
-  Validate response code
### Setup & Execution
#### 1. Clone the Repository
```
git clone <your-repo-url>
cd <your-repo-folder>
```
#### 2. Install Prerequisites
- Java 8 or higher
- Maven 3.6 or higher
- Internet access to call the live Petstore API

Ensure Java and Maven are installed and working:
```
java -version
mvn -version
```
#### 3. Test Execution
Use the Maven commands to run all tests, a single class, or a single method.
#### a. Execute All Tests (Serial)
By default, tests run one after another:

 `mvn test`
#### b. Execute a Single Test Class
 `mvn -Dtest=PetTests test`
#### c. Execute a Single Test Method in Class
 `mvn -Dtest=PetTests#createPet test`
#### d. Execute Tests in Parallel
Tests, classes or methods can be executed in parallel by setting the `parallel` attribute and `thread-count` in testng.xml.
```
<suite name="PetstoreAPISuite" parallel="tests" thread-count="5">
    <test name="PetTests">
        <classes>
            <class name="testscripts.PetTests"/>
        </classes>
    </test>
</suite>
```
#### 4. Reports & Logs
After execution, view the Extent Report (logs/ExtentReport.html) or TestNG report (test-output/index.html). Debugging logs are in logs/.
#### TestNG Report
- Location: test-output/index.html
- Open in browser to see detailed test results (Passed, Failed, Skipped).
#### Extent Report
- Logs request & response details, status, and execution steps
- Location: logs/ExtentReport.html
#### Logging
- Detailed request/response logs are in:
  `logs/<class>_<method>_thread_<threadId>_<timestamp>.log`
- Useful for debugging, inspecting API calls, and troubleshooting failures
#### 5.Cleanup
All created pets are automatically deleted after test execution to keep the environment clean.
- All created pets are tracked
- Automatically deleted in ```@AfterMethod```
- Ensures a clean test environment
