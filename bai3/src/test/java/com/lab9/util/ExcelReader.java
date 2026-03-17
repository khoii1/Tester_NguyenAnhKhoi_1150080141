package com.lab9.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Utility for reading test data from an Excel (.xlsx) workbook.
 *
 * <p>Usage pattern: call {@link #readSheet(String, String)} with the file path and
 * sheet name. The first row is treated as the header row. Each subsequent row
 * becomes a {@code Map<header, value>} so callers can access values by column name,
 * making tests resilient to column-order changes in the spreadsheet.
 *
 * <p>Adding new rows to the Excel file automatically adds new test cases without
 * any Java code changes.
 */
public class ExcelReader {

    private ExcelReader() {
        // utility class
    }

    /**
     * Reads all data rows from the given sheet and returns them as an ordered list
     * of header-to-value maps.
     *
     * @param filePath  absolute or project-relative path to the .xlsx file
     * @param sheetName name of the sheet to read
     * @return list of row maps; never null; empty if sheet has no data rows
     */
    public static List<Map<String, String>> readSheet(String filePath, String sheetName) {
        List<Map<String, String>> result = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath))) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found in workbook: " + sheetName);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                return result;
            }

            // Collect header names from first row
            List<String> headers = new ArrayList<>();
            for (int c = 0; c < headerRow.getLastCellNum(); c++) {
                headers.add(getCellValue(headerRow.getCell(c)));
            }

            // Collect data rows
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }

                Map<String, String> rowData = new LinkedHashMap<>();
                for (int c = 0; c < headers.size(); c++) {
                    rowData.put(headers.get(c), getCellValue(row.getCell(c)));
                }
                result.add(rowData);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + filePath, e);
        }

        return result;
    }

    /**
     * Converts a cell to its string representation, handling all cell types:
     * null, STRING, NUMERIC (integer or decimal), BOOLEAN, FORMULA, BLANK.
     *
     * @param cell the cell to read; may be null
     * @return string value; never null; empty string for null / blank cells
     */
    static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        CellType type = cell.getCellType();

        if (type == CellType.FORMULA) {
            // For formula cells, use the cached result type
            type = cell.getCachedFormulaResultType();
        }

        switch (type) {
            case STRING:
                return cell.getStringCellValue();

            case NUMERIC:
                double numVal = cell.getNumericCellValue();
                // Return as long when there is no fractional part to avoid "1.0" strings
                if (numVal == Math.floor(numVal) && !Double.isInfinite(numVal)) {
                    return String.valueOf((long) numVal);
                }
                return String.valueOf(numVal);

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case BLANK:
            case _NONE:
            default:
                return "";
        }
    }
}
