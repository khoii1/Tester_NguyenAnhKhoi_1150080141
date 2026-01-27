import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Test Runner for Customer Registration Form
 * Runs all unit tests and displays results
 */
public class TestRunner {
    public static void main(String[] args) {
        System.out.println("========================================================");
        System.out.println("   CUSTOMER REGISTRATION FORM - UNIT TEST RUNNER");
        System.out.println("========================================================");
        System.out.println();
        
        // Run Validator tests
        System.out.println("Running Validator Tests...");
        System.out.println("--------------------------------------------------------");
        Result result = JUnitCore.runClasses(ValidatorTest.class);
        
        // Display results
        System.out.println("\n========== TEST RESULTS ==========");
        System.out.println("Total tests run: " + result.getRunCount());
        System.out.println("Tests passed: " + (result.getRunCount() - result.getFailureCount()));
        System.out.println("Tests failed: " + result.getFailureCount());
        System.out.println("Tests ignored: " + result.getIgnoreCount());
        System.out.println("Time taken: " + result.getRunTime() + " ms");
        System.out.println("==================================");
        
        // Display failures
        if (result.getFailureCount() > 0) {
            System.out.println("\n========== FAILED TESTS ==========");
            for (Failure failure : result.getFailures()) {
                System.out.println("\nFAILED: " + failure.getTestHeader());
                System.out.println("   " + failure.getMessage());
                System.out.println("   at " + failure.getException().getStackTrace()[0]);
            }
            System.out.println("==================================");
        }
        
        // Final result
        System.out.println();
        if (result.wasSuccessful()) {
            System.out.println("ALL TESTS PASSED SUCCESSFULLY!");
        } else {
            System.out.println("SOME TESTS FAILED!");
        }
        System.out.println();
        System.out.println("========================================================");
    }
}
