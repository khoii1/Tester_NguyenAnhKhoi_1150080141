package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * RegisterPage – Page Object Model cho form đăng ký ShopVN.
 *
 * Mỗi phương thức đại diện cho một thao tác trên giao diện.
 * Locator được khai báo tại một chỗ, dễ bảo trì khi UI thay đổi.
 */
public class RegisterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // -------------------------------------------------------
    // LOCATORS
    // -------------------------------------------------------

    // --- Input fields ---
    private final By fullNameInput        = By.id("fullName");
    private final By usernameInput        = By.id("username");
    private final By emailInput           = By.id("email");
    private final By phoneInput           = By.id("phone");
    private final By passwordInput        = By.id("password");
    private final By confirmPasswordInput = By.id("confirmPassword");
    private final By dobInput             = By.id("dob");
    private final By genderSelect         = By.id("gender");
    private final By referralCodeInput    = By.id("referralCode");

    // --- Checkbox & links ---
    private final By agreeTermsCheckbox   = By.id("agreeTerms");
    private final By viewTermsLink        = By.id("viewTermsLink");

    // --- Buttons ---
    private final By submitButton         = By.id("submitBtn");
    private final By acceptTermsButton    = By.id("acceptTermsBtn");
    private final By closeModalButton     = By.id("closeModal");

    // --- Error message elements ---
    private final By fullNameError        = By.id("fullNameError");
    private final By usernameError        = By.id("usernameError");
    private final By emailError           = By.id("emailError");
    private final By phoneError           = By.id("phoneError");
    private final By passwordError        = By.id("passwordError");
    private final By confirmPasswordError = By.id("confirmPasswordError");
    private final By dobError             = By.id("dobError");
    private final By referralCodeError    = By.id("referralCodeError");
    private final By agreeTermsError      = By.id("agreeTermsError");

    // --- Success banner & modal ---
    private final By successBanner        = By.id("successBanner");
    private final By termsModal           = By.id("termsModal");
    private final By termsModalTitle      = By.id("termsTitle");
    private final By termsContent         = By.id("termsContent");

    // -------------------------------------------------------
    // CONSTRUCTOR
    // -------------------------------------------------------
    public RegisterPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait   = wait;
    }

    // -------------------------------------------------------
    // INPUT METHODS – nhập giá trị vào từng trường
    // -------------------------------------------------------

    /** Nhập họ và tên */
    public RegisterPage enterFullName(String value) {
        fillField(fullNameInput, value);
        return this;
    }

    /** Nhập tên đăng nhập */
    public RegisterPage enterUsername(String value) {
        fillField(usernameInput, value);
        return this;
    }

    /** Nhập email */
    public RegisterPage enterEmail(String value) {
        fillField(emailInput, value);
        return this;
    }

    /** Nhập số điện thoại */
    public RegisterPage enterPhone(String value) {
        fillField(phoneInput, value);
        return this;
    }

    /** Nhập mật khẩu */
    public RegisterPage enterPassword(String value) {
        fillField(passwordInput, value);
        return this;
    }

    /** Nhập xác nhận mật khẩu */
    public RegisterPage enterConfirmPassword(String value) {
        fillField(confirmPasswordInput, value);
        return this;
    }

    /**
     * Nhập ngày sinh theo định dạng yyyy-MM-dd.
     * Dùng JavascriptExecutor vì input[type=date] đôi khi khó sendKeys.
     */
    public RegisterPage enterDob(String yyyyMmDd) {
        if (yyyyMmDd == null || yyyyMmDd.isBlank()) return this;
        WebElement el = driver.findElement(dobInput);
        el.clear();
        // Thử sendKeys trước
        el.sendKeys(yyyyMmDd);
        // Fallback bằng JS nếu value chưa được set
        if (el.getAttribute("value") == null || el.getAttribute("value").isEmpty()) {
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('change'));",
                el, yyyyMmDd);
        }
        return this;
    }

    /**
     * Chọn giới tính.
     * @param value "male" | "female" | "other" | ""
     */
    public RegisterPage selectGender(String value) {
        if (value == null || value.isBlank()) return this;
        Select select = new Select(driver.findElement(genderSelect));
        select.selectByValue(value);
        return this;
    }

    /** Nhập mã giới thiệu */
    public RegisterPage enterReferralCode(String value) {
        fillField(referralCodeInput, value);
        return this;
    }

    /** Tick / bỏ tick checkbox đồng ý điều khoản */
    public RegisterPage setAgreeTerms(boolean agree) {
        WebElement cb = driver.findElement(agreeTermsCheckbox);
        if (cb.isSelected() != agree) {
            cb.click();
        }
        return this;
    }

    // -------------------------------------------------------
    // ACTION METHODS
    // -------------------------------------------------------

    /** Click nút Đăng ký */
    public void clickSubmit() {
        driver.findElement(submitButton).click();
    }

    /** Click link "Xem Điều khoản" */
    public RegisterPage clickViewTerms() {
        driver.findElement(viewTermsLink).click();
        return this;
    }

    /** Click nút "Đã hiểu & Đồng ý" trong modal */
    public RegisterPage clickAcceptTerms() {
        waitForModalVisible();
        driver.findElement(acceptTermsButton).click();
        return this;
    }

    /** Click nút đóng modal (×) */
    public RegisterPage closeTermsModal() {
        waitForModalVisible();
        driver.findElement(closeModalButton).click();
        return this;
    }

    /**
     * Điền toàn bộ form với dữ liệu đầu vào.
     * Trường null / rỗng sẽ được bỏ qua (không nhập).
     */
    public void fillForm(
            String fullName, String username, String email, String phone,
            String password, String confirmPassword,
            String dob, String gender, String referralCode,
            boolean agreeTerms) {

        if (fullName       != null) enterFullName(fullName);
        if (username       != null) enterUsername(username);
        if (email          != null) enterEmail(email);
        if (phone          != null) enterPhone(phone);
        if (password       != null) enterPassword(password);
        if (confirmPassword!= null) enterConfirmPassword(confirmPassword);
        if (dob            != null) enterDob(dob);
        if (gender         != null) selectGender(gender);
        if (referralCode   != null) enterReferralCode(referralCode);
        setAgreeTerms(agreeTerms);
    }

    // -------------------------------------------------------
    // GETTER METHODS – lấy thông báo lỗi
    // -------------------------------------------------------

    public String getFullNameError()        { return getErrorText(fullNameError);        }
    public String getUsernameError()        { return getErrorText(usernameError);        }
    public String getEmailError()           { return getErrorText(emailError);           }
    public String getPhoneError()           { return getErrorText(phoneError);           }
    public String getPasswordError()        { return getErrorText(passwordError);        }
    public String getConfirmPasswordError() { return getErrorText(confirmPasswordError); }
    public String getDobError()             { return getErrorText(dobError);             }
    public String getReferralCodeError()    { return getErrorText(referralCodeError);    }
    public String getAgreeTermsError()      { return getErrorText(agreeTermsError);      }

    // -------------------------------------------------------
    // STATE CHECK METHODS
    // -------------------------------------------------------

    /** Kiểm tra banner thành công có hiện không */
    public boolean isSuccessBannerDisplayed() {
        try {
            WebElement el = wait.until(
                ExpectedConditions.visibilityOfElementLocated(successBanner));
            return el.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Kiểm tra modal điều khoản có đang mở không */
    public boolean isTermsModalDisplayed() {
        try {
            waitForModalVisible();
            return driver.findElement(termsModal).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Lấy tiêu đề modal điều khoản */
    public String getTermsModalTitle() {
        waitForModalVisible();
        return driver.findElement(termsModalTitle).getText();
    }

    /** Kiểm tra nội dung điều khoản có hiện trong modal */
    public boolean isTermsContentVisible() {
        try {
            return driver.findElement(termsContent).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Kiểm tra field có class "invalid" (viền đỏ) */
    public boolean isFieldInvalid(String fieldId) {
        String classes = driver.findElement(By.id(fieldId)).getAttribute("class");
        return classes != null && classes.contains("invalid");
    }

    /** Kiểm tra field có class "valid" (viền xanh) */
    public boolean isFieldValid(String fieldId) {
        String classes = driver.findElement(By.id(fieldId)).getAttribute("class");
        return classes != null && classes.contains("valid");
    }

    // -------------------------------------------------------
    // PRIVATE HELPERS
    // -------------------------------------------------------

    /** Clear và nhập giá trị vào input field */
    private void fillField(By locator, String value) {
        if (value == null) return;
        WebElement el = driver.findElement(locator);
        el.clear();
        if (!value.isEmpty()) {
            el.sendKeys(value);
        }
        // Trigger blur để kích hoạt real-time validation
        ((JavascriptExecutor) driver).executeScript("arguments[0].blur();", el);
    }

    /** Lấy text của element lỗi, trả về "" nếu không tìm thấy */
    private String getErrorText(By locator) {
        try {
            return driver.findElement(locator).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /** Đợi modal visible trước khi tương tác */
    private void waitForModalVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(termsModal));
    }
}
