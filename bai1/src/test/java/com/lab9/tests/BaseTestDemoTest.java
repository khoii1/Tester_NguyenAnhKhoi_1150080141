package com.lab9.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.lab9.core.BaseTest;

/**
 * Demo tests to validate BaseTest setup/teardown and screenshot-on-failure behavior.
 */
public class BaseTestDemoTest extends BaseTest {

    @Test
    public void passTestShouldOpenConfiguredUrl() {
        Assert.assertTrue(getDriver().getTitle().contains("Example"), "Title should contain 'Example'.");
    }

    @Test
    public void failTestShouldTriggerScreenshotCapture() {
        Assert.assertTrue(false, "Intentional failure to verify screenshot capture in target/screenshots.");
    }
}
