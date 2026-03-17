package com.lab9.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.lab9.core.BaseTest;
import com.lab9.utils.ConfigReader;

/**
 * Small demo test to verify environment-based configuration loading.
 */
public class ConfigReaderDemoTest extends BaseTest {

    @Test
    public void testConfigLoadedByEnv() {
        String env = System.getProperty("env", "dev");
        ConfigReader config = ConfigReader.getInstance();

        if ("staging".equalsIgnoreCase(env)) {
            Assert.assertEquals(config.getExplicitWait(), 20, "Expected staging explicit wait = 20.");
        } else {
            Assert.assertEquals(config.getExplicitWait(), 15, "Expected dev explicit wait = 15.");
        }

        Assert.assertFalse(config.getBaseUrl().isBlank(), "Base URL from config should not be blank.");
    }
}
