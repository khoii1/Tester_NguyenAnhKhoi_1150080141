# BEFORE_REFACTOR Marker

This file marks the baseline used before Bai7 refactor actions.

## Baseline Source

- The Bai7 project was initialized by copying the Bai6 framework snapshot into `bai7/`.

## Before Files (Baseline)

- `src/main/java/com/lab9/core/BasePage.java`
- `src/test/java/com/lab9/core/BaseTest.java`
- `src/main/java/com/lab9/pages/LoginPage.java`
- `src/main/java/com/lab9/pages/InventoryPage.java`
- `src/main/java/com/lab9/pages/CartPage.java`
- `src/test/java/com/lab9/tests/ConfigReaderDemoTest.java`
- `src/test/java/com/lab9/tests/UserLoginTest.java`
- `src/test/java/com/lab9/tests/LoginDataDrivenTest.java`
- `src/test/java/com/lab9/tests/CheckoutFakerTest.java`
- `src/test/java/com/lab9/tests/FlakySimulationTest.java`

## Refactor Scope in Bai7

- Keep POM + DDT + Config + Retry framework.
- Remove remaining test hardcode for credentials from tests.
- Ensure test suite runs via centralized framework classes and data sources.
- Verify no `Thread.sleep()` and no direct WebDriver creation in test classes.
