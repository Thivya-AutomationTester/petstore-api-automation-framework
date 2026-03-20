package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * A class responsible for creating and managing a single instance of
 * ExtentReports.
 * <p>
 * This class configures the ExtentSparkReporter and attaches it to the
 * ExtentReports instance.
 * </p>
 */

public class ExtentManager {

	/** The single instance of ExtentReports */
	private static ExtentReports extent;

	/**
	 * Returns the instance of ExtentReports.
	 * <p>
	 * This method checks if the ExtentReports instance is already created. If not,
	 * it initializes it and attaches the ExtentSparkReporter for generating an HTML
	 * report. It also configures the title and report name.
	 * </p>
	 *
	 * @return the ExtentReports instance
	 */
	public synchronized static ExtentReports getInstance() {
		// If the instance is not created, initialize it
		if (extent == null) {
			ExtentSparkReporter spark = new ExtentSparkReporter("logs/ExtentReport.html");
			spark.config().setDocumentTitle("API Automation Report");
			spark.config().setReportName("Petstore API Tests");

			extent = new ExtentReports();
			extent.attachReporter(spark);
		}
		return extent;
	}
}