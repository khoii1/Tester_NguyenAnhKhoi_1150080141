package com.lab9.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.lab9.utils.ConfigReader;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Base test class that provides driver lifecycle, environment resolution,
 * and failure screenshot support for all test classes.
 */
public class BaseTest {

    private static final ThreadLocal<WebDriver> DRIVER_HOLDER = new ThreadLocal<>();
    private static final DateTimeFormatter SCREENSHOT_TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    /**
     * Returns the current thread driver.
     *
     * @return active WebDriver for current test thread
     */
    protected WebDriver getDriver() {
        WebDriver driver = DRIVER_HOLDER.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized for current thread.");
        }
        return driver;
    }

    /**
     * Initializes browser and navigates to base URL before each test method.
     *
     * @param browser target browser name from testng.xml
     * @param env target environment name from testng.xml
     */
    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "env"})
    public void setUp(@Optional("chrome") String browser, @Optional("dev") String env) {
        String activeEnv = System.getProperty("env", env);
        System.setProperty("env", activeEnv);
        ConfigReader configReader = ConfigReader.getInstance();

        WebDriver driver = createDriver(browser);
        DRIVER_HOLDER.set(driver);
        driver.manage().window().maximize();

        System.out.println("Explicit wait hiện tại: " + configReader.getExplicitWait());
        driver.get(configReader.getBaseUrl());
    }

    /**
     * Captures screenshot on failure and releases driver after each test method.
     *
     * @param result TestNG result object
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            if (ITestResult.FAILURE == result.getStatus()) {
                captureScreenshot(result.getName());
            }
        } finally {
            WebDriver driver = DRIVER_HOLDER.get();
            if (driver != null) {
                driver.quit();
            }
            DRIVER_HOLDER.remove();
        }
    }

    private WebDriver createDriver(String browser) {
        if ("chrome".equalsIgnoreCase(browser)) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            return new ChromeDriver(options);
        }
        throw new IllegalArgumentException("Unsupported browser: " + browser);
    }

    private void captureScreenshot(String testName) {
        WebDriver driver = DRIVER_HOLDER.get();
        if (driver == null) {
            return;
        }

        try {
            Path screenshotDir = Path.of("target", "screenshots");
            Files.createDirectories(screenshotDir);

            String timestamp = LocalDateTime.now().format(SCREENSHOT_TS);
            String fileName = testName + "_" + timestamp + ".png";
            Path targetPath = screenshotDir.resolve(fileName);

            Path sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath();
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to capture screenshot for test: " + testName, ex);
        }
    }
}
