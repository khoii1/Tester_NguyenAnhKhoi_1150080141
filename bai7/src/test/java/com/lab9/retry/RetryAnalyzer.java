package com.lab9.retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.lab9.utils.ConfigReader;

/**
 * Retry analyzer that retries failed tests based on config retry.count.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private final int maxRetry;
    private int retryCounter;

    public RetryAnalyzer() {
        this.maxRetry = ConfigReader.getInstance().getRetryCount();
        this.retryCounter = 0;
    }

    @Override
    public boolean retry(ITestResult result) {
        if (retryCounter < maxRetry) {
            retryCounter++;
            System.out.println("[RetryAnalyzer] RETRY " + retryCounter + "/" + maxRetry
                + " for test: " + result.getName());
            return true;
        }

        System.out.println("[RetryAnalyzer] No more retries (max=" + maxRetry
            + ") for test: " + result.getName());
        return false;
    }
}
