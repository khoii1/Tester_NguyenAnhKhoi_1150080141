import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(PaymentCalculatorTest.class);
        
        System.out.println("=================================================");
        System.out.println("PAYMENT CALCULATOR TEST RESULTS");
        System.out.println("=================================================");
        System.out.println("Total tests run: " + result.getRunCount());
        System.out.println("Tests passed: " + (result.getRunCount() - result.getFailureCount()));
        System.out.println("Tests failed: " + result.getFailureCount());
        System.out.println("Ignored tests: " + result.getIgnoreCount());
        System.out.println("Time elapsed: " + result.getRunTime() + " ms");
        System.out.println("=================================================");
        
        if (result.getFailureCount() > 0) {
            System.out.println("\nFAILURES:");
            System.out.println("=================================================");
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
                System.out.println(failure.getTrace());
                System.out.println("-------------------------------------------------");
            }
        }
        
        System.out.println("\nTest Result: " + (result.wasSuccessful() ? "SUCCESS" : "FAILED"));
    }
}
