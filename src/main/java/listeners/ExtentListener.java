package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import reports.ExtentManager;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * A listener class that integrates with TestNG to generate reports using
 * ExtentReports.
 * <p>
 * This class implements the {@link ITestListener} interface and logs the status
 * of the tests (such as start, success, failure, and skip) to an ExtentReport
 * instance for detailed reporting.
 * </p>
 */

public class ExtentListener implements ITestListener {

	/** Instance of ExtentReports to generate the report. */
	private static ExtentReports extent = ExtentManager.getInstance();

	/** ThreadLocal to store ExtentTest for each test in the current thread. */
	public static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

	/**
	 * This method is invoked when a test starts.
	 * <p>
	 * It creates a new ExtentTest instance and stores it in the {@link testThread}
	 * for the current thread.
	 * </p>
	 *
	 * @param result the result of the test execution
	 */
	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest test = extent.createTest(result.getMethod().getMethodName());
		testThread.set(test);
	}

	/**
	 * This method is invoked when a test is successful.
	 * <p>
	 * It logs the success status to the ExtentTest instance associated with the
	 * current thread.
	 * </p>
	 *
	 * @param result the result of the test execution
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		testThread.get().log(Status.PASS, "Test passed");
	}

	/**
	 * This method is invoked when a test fails.
	 * <p>
	 * It logs the failure status along with the exception message or stack trace to
	 * the ExtentTest instance.
	 * </p>
	 *
	 * @param result the result of the test execution
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		testThread.get().log(Status.FAIL, result.getThrowable());
	}

	/**
	 * This method is invoked when a test is skipped.
	 * <p>
	 * It logs the skip status to the ExtentTest instance.
	 * </p>
	 *
	 * @param result the result of the test execution
	 */
	@Override
	public void onTestSkipped(ITestResult result) {
		testThread.get().log(Status.SKIP, "Test skipped");
	}

	/**
	 * This method is invoked when all tests have finished executing.
	 * <p>
	 * It flushes the ExtentReports instance to generate the final report.
	 * </p>
	 *
	 * @param context the test context of the execution
	 */
	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}

}