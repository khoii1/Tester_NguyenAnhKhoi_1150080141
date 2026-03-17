package com.lab9.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Creates the {@code login_data.xlsx} test-data workbook on first run.
 *
 * <p>Once the file exists it is never overwritten, so testers can freely edit
 * cell values, add or remove rows, and the data-driven tests will reflect those
 * changes on the next run without any code changes.
 *
 * <p>Call {@link #ensureExists(String)} from {@code @BeforeSuite} before any
 * DataProvider tries to read the file.
 */
public class TestDataInitializer {

    private TestDataInitializer() {
        // utility class
    }

    /**
     * Creates the Excel file at {@code filePath} if it does not already exist.
     *
     * @param filePath project-relative or absolute path for the output file
     */
    public static void ensureExists(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return;
        }

        file.getParentFile().mkdirs();

        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(file)) {

            createSmokeSheet(workbook);
            createNegativeSheet(workbook);
            createBoundarySheet(workbook);

            workbook.write(fos);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create test data file: " + filePath, e);
        }
    }

    // ------------------------------------------------------------------
    // Sheet builders
    // ------------------------------------------------------------------

    private static void createSmokeSheet(Workbook wb) {
        Sheet sheet = wb.createSheet("SmokeCases");

        writeRow(sheet, 0, "username", "password", "expected_url", "description");

        // SauceDemo accounts that should reach /inventory.html
        writeRow(sheet, 1,
            "standard_user", "secret_sauce",
            "https://www.saucedemo.com/inventory.html",
            "Login with valid standard user");
        writeRow(sheet, 2,
            "performance_glitch_user", "secret_sauce",
            "https://www.saucedemo.com/inventory.html",
            "Login with performance glitch user");
        writeRow(sheet, 3,
            "problem_user", "secret_sauce",
            "https://www.saucedemo.com/inventory.html",
            "Login with problem user");
    }

    private static void createNegativeSheet(Workbook wb) {
        Sheet sheet = wb.createSheet("NegativeCases");

        writeRow(sheet, 0, "username", "password", "expected_error", "description");

        writeRow(sheet, 1,
            "", "",
            "Epic sadface: Username is required",
            "Empty username and empty password");
        writeRow(sheet, 2,
            "standard_user", "",
            "Epic sadface: Password is required",
            "Valid username but empty password");
        writeRow(sheet, 3,
            "", "secret_sauce",
            "Epic sadface: Username is required",
            "Empty username with valid password");
        writeRow(sheet, 4,
            "invalid_user_xyz", "secret_sauce",
            "Epic sadface: Username and password do not match any user in this service",
            "Non-existent username with valid password");
        writeRow(sheet, 5,
            "locked_out_user", "secret_sauce",
            "Epic sadface: Sorry, this user has been locked out.",
            "Locked out user");
    }

    private static void createBoundarySheet(Workbook wb) {
        Sheet sheet = wb.createSheet("BoundaryCases");

        writeRow(sheet, 0, "username", "password", "expected_error", "description");

        writeRow(sheet, 1,
            " ", "secret_sauce",
            "Epic sadface: Username and password do not match any user in this service",
            "Whitespace-only username");
        writeRow(sheet, 2,
            "standard_user", " ",
            "Epic sadface: Username and password do not match any user in this service",
            "Whitespace-only password");
        writeRow(sheet, 3,
            "a".repeat(64), "secret_sauce",
            "Epic sadface: Username and password do not match any user in this service",
            "Very long username (64 chars)");
        writeRow(sheet, 4,
            "standard_user", "a".repeat(64),
            "Epic sadface: Username and password do not match any user in this service",
            "Very long password (64 chars)");
    }

    // ------------------------------------------------------------------
    // Helper
    // ------------------------------------------------------------------

    private static void writeRow(Sheet sheet, int rowIndex, String... values) {
        Row row = sheet.createRow(rowIndex);
        for (int i = 0; i < values.length; i++) {
            row.createCell(i).setCellValue(values[i]);
        }
    }
}
