package com.lab9.tests;

import java.util.concurrent.atomic.AtomicInteger;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.lab9.core.BaseTest;
import com.lab9.utils.ConfigReader;

/**
 * Simulates a flaky test: fails first 2 attempts, passes on 3rd attempt.
 */
public class FlakySimulationTest extends BaseTest {

    private static final AtomicInteger CALL_COUNT = new AtomicInteger(0);

    @Test
    public void flakyTestShouldPassOnThirdAttempt() {
        int currentAttempt = CALL_COUNT.incrementAndGet();
        int maxRetry = ConfigReader.getInstance().getRetryCount();

        System.out.println("[FlakySimulationTest] Test running attempt #" + currentAttempt
            + ", configured max retry = " + maxRetry);

        if (currentAttempt <= 2) {
            Assert.fail("Simulated flaky failure at attempt #" + currentAttempt);
        }

        Assert.assertTrue(true, "Test passes on attempt #" + currentAttempt);
    }
}
