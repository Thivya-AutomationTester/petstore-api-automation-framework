package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * A custom TestNG retry analyzer that retries failed tests a specified number of times.
 * <p>
 * This class implements the {@link IRetryAnalyzer} interface and allows failed tests to be retried a certain 
 * number of times (defined by {@code maxRetryCount}).
 * </p>
 */
public class RetryAnalyzer implements IRetryAnalyzer {
	
	/** Counter to track the number of retries for a failed test. */
    private int retryCount = 0;
    
    /** Maximum number of retries for a failed test. */
    private static final int maxRetryCount = 5; 

    /**
     * Determines whether the failed test should be retried.
     * <p>
     * If the retry count is less than the maximum retry count, the test is retried.
     * The retry count is incremented after each retry.
     * </p>
     *
     * @param result the test result containing information about the failed test
     * @return {@code true} if the test should be retried, {@code false} otherwise
     */
    
       
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            System.out.println("Retrying test " + result.getName() + " for the " + retryCount + " time(s).");
            return true;
        }
        return false;
    }
}