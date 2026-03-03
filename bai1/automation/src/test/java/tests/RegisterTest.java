package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.RegisterPage;
import utils.RegisterDataProvider;

/**
 * RegisterTest – Bộ test automation cho Form Đăng Ký Tài Khoản ShopVN.
 *
 * Tổng cộng: 16 test case (1 positive + 15 negative).
 *
 * Quy ước đặt tên: TC[xx]_MôTảNgắn
 * Priority: số nhỏ hơn = ưu tiên chạy trước (TestNG @Test priority)
 */
public class RegisterTest extends BaseTest {

    private RegisterPage registerPage;

    // Khởi tạo Page Object sau khi BaseTest.setUp() mở trình duyệt
    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        registerPage = new RegisterPage(driver, wait);
    }

    // =============================================================
    // TC01 – ĐĂNG KÝ THÀNH CÔNG
    // =============================================================

    /**
     * TC01: Điền đầy đủ dữ liệu hợp lệ → banner thành công hiện ra.
     */
    @Test(priority = 1,
          description = "TC01 – Đăng ký thành công với dữ liệu hợp lệ",
          dataProvider = "validRegistration",
          dataProviderClass = RegisterDataProvider.class)
    public void TC01_registerWithValidData(
            String fullName, String username, String email, String phone,
            String password, String confirmPassword,
            String dob, String gender, String referralCode, boolean agreeTerms) {

        registerPage.fillForm(fullName, username, email, phone,
                password, confirmPassword, dob, gender, referralCode, agreeTerms);
        registerPage.clickSubmit();

        Assert.assertTrue(registerPage.isSuccessBannerDisplayed(),
                "TC01 FAIL: Banner đăng ký thành công không hiện ra.");
    }

    // =============================================================
    // TC02 – HỌ VÀ TÊN ĐỂ TRỐNG
    // =============================================================

    @Test(priority = 2,
          description = "TC02 – Họ và tên để trống → lỗi bắt buộc")
    public void TC02_fullNameEmpty() {
        registerPage.fillForm(
                "",                         // fullName rỗng
                "binhtest2", "binhtest2@gmail.com", "0912000001",
                "Test@1234", "Test@1234",
                "1998-01-01", "male", "", true);
        registerPage.clickSubmit();

        String err = registerPage.getFullNameError();
        Assert.assertFalse(err.isEmpty(),
                "TC02 FAIL: Không hiện lỗi khi họ tên rỗng.");
        Assert.assertTrue(err.contains("không được để trống"),
                "TC02 FAIL: Thông báo lỗi không đúng: " + err);
    }

    // =============================================================
    // TC03 – HỌ VÀ TÊN DƯỚI MIN LENGTH
    // =============================================================

    @Test(priority = 3,
          description = "TC03 – Họ và tên chỉ 1 ký tự → lỗi min length")
    public void TC03_fullNameTooShort() {
        registerPage.fillForm(
                "A",                        // 1 ký tự
                "binhtest3", "binhtest3@gmail.com", "0912000002",
                "Test@1234", "Test@1234",
                "1998-01-01", "female", "", true);
        registerPage.clickSubmit();

        String err = registerPage.getFullNameError();
        Assert.assertTrue(err.contains("ít nhất 2 ký tự"),
                "TC03 FAIL: Thông báo lỗi không đúng: " + err);
    }

    // =============================================================
    // TC04 – USERNAME SAI FORMAT
    // =============================================================

    @Test(priority = 4,
          description = "TC04 – Username chứa chữ hoa → lỗi format")
    public void TC04_usernameInvalidFormat() {
        registerPage.fillForm(
                "Trần Văn Đức",
                "UserABC",                  // chứa chữ hoa
                "duc2024@gmail.com", "0912000003",
                "Test@1234", "Test@1234",
                "1995-03-10", "male", "", true);
        registerPage.clickSubmit();

        String err = registerPage.getUsernameError();
        Assert.assertFalse(err.isEmpty(),
                "TC04 FAIL: Không hiện lỗi khi username sai format.");
        Assert.assertTrue(err.contains("chữ thường"),
                "TC04 FAIL: Thông báo lỗi không đúng: " + err);
    }

    // =============================================================
    // TC05 – USERNAME ĐÃ TỒN TẠI
    // =============================================================

    @Test(priority = 5,
          description = "TC05 – Username đã tồn tại → lỗi trùng")
    public void TC05_usernameDuplicate() {
        registerPage.fillForm(
                "Lê Thị Hương",
                "admin",                    // username đã có trong EXISTING_USERNAMES
                "huong2024@gmail.com", "0912000004",
                "Test@1234", "Test@1234",
                "1997-07-07", "female", "", true);
        registerPage.clickSubmit();

        String err = registerPage.getUsernameError();
        Assert.assertTrue(err.contains("đã tồn tại"),
                "TC05 FAIL: Thông báo lỗi không đúng: " + err);
    }

    // =============================================================
    // TC06 – EMAIL SAI FORMAT
    // =============================================================

    @Test(priority = 6,
          description = "TC06 – Email thiếu @ → lỗi định dạng")
    public void TC06_emailInvalidFormat() {
        registerPage.fillForm(
                "Phạm Văn Khánh",
                "khanhpv5", "emailkhonghople",  // không có @
                "0912000005",
                "Test@1234", "Test@1234",
                "1996-09-15", "male", "", true);
        registerPage.clickSubmit();

        String err = registerPage.getEmailError();
        Assert.assertTrue(err.contains("không đúng định dạng"),
                "TC06 FAIL: Thông báo lỗi không đúng: " + err);
    }

    // =============================================================
    // TC07 – EMAIL ĐÃ TỒN TẠI
    // =============================================================

    @Test(priority = 7,
          description = "TC07 – Email đã được đăng ký → lỗi trùng email")
    public void TC07_emailDuplicate() {
        registerPage.fillForm(
                "Hoàng Thị Lan",
                "lanthi6", "admin@shopvn.vn",  // email đã tồn tại
                "0912000006",
                "Test@1234", "Test@1234",
                "1993-11-20", "female", "", true);
        registerPage.clickSubmit();

        String err = registerPage.getEmailError();
        Assert.assertTrue(err.contains("đã được đăng ký"),
                "TC07 FAIL: Thông báo lỗi không đúng: " + err);
    }

    // =============================================================
    // TC08 – SỐ ĐIỆN THOẠI KHÔNG ĐỦ 10 SỐ
    // =============================================================

    @Test(priority = 8,
          description = "TC08 – Số điện thoại chỉ 6 chữ số → lỗi định dạng")
    public void TC08_phoneTooShort() {
        registerPage.fillForm(
                "Ngô Văn Minh",
                "minhng7", "minhng7@gmail.com", "091234",  // chỉ 6 số
                "Test@1234", "Test@1234",
                "1994-04-04", "male", "", true);
        registerPage.clickSubmit();

        String err = registerPage.getPhoneError();
        Assert.assertTrue(err.contains("10 chữ số"),
                "TC08 FAIL: Thông báo lỗi không đúng: " + err);
    }

    // =============================================================
    // TC09 – MẬT KHẨU KHÔNG ĐỦ MẠNH
    // =============================================================

    @Test(priority = 9,
          description = "TC09 – Mật khẩu không có chữ hoa → lỗi độ mạnh")
    public void TC09_passwordNoUppercase() {
        registerPage.fillForm(
                "Vũ Thị Ngọc",
                "ngocvt8", "ngocvt8@gmail.com", "0912000007",
                "test@1234", "test@1234",  // không có chữ hoa
                "1999-06-06", "female", "", true);
        registerPage.clickSubmit();

        String err = registerPage.getPasswordError();
        Assert.assertTrue(err.contains("chữ hoa"),
                "TC09 FAIL: Thông báo lỗi không đúng: " + err);
    }

    // =============================================================
    // TC10 – MẬT KHẨU QUÁ NGẮN
    // =============================================================

    @Test(priority = 10,
          description = "TC10 – Mật khẩu < 8 ký tự → lỗi min length")
    public void TC10_passwordTooShort() {
        registerPage.fillForm(
                "Đỗ Văn Phúc",
                "phucdo9", "phucdo9@gmail.com", "0912000008",
                "Ab@1", "Ab@1",  // 4 ký tự
                "2000-08-08", "male", "", true);
        registerPage.clickSubmit();

        String err = registerPage.getPasswordError();
        Assert.assertTrue(err.contains("ít nhất 8 ký tự"),
                "TC10 FAIL: Thông báo lỗi không đúng: " + err);
    }

    // =============================================================
    // TC11 – CONFIRM PASSWORD KHÔNG KHỚP
    // =============================================================

    @Test(priority = 11,
          description = "TC11 – Confirm password khác password → lỗi không khớp")
    public void TC11_confirmPasswordMismatch() {
        registerPage.fillForm(
                "Trịnh Thị Quỳnh",
                "quynhtt10", "quynhtt10@gmail.com", "0912000009",
                "Test@1234", "DifferentPwd@99",  // không khớp
                "2001-02-14", "female", "", true);
        registerPage.clickSubmit();

        String err = registerPage.getConfirmPasswordError();
        Assert.assertTrue(err.contains("không khớp"),
                "TC11 FAIL: Thông báo lỗi không đúng: " + err);
    }

    // =============================================================
    // TC12 – NGÀY SINH DƯỚI 16 TUỔI
    // =============================================================

    @Test(priority = 12,
          description = "TC12 – Ngày sinh dưới 16 tuổi → lỗi tuổi")
    public void TC12_dobUnder16() {
        registerPage.fillForm(
                "Bùi Văn Sơn",
                "sonbv11", "sonbv11@gmail.com", "0912000010",
                "Test@1234", "Test@1234",
                "2015-06-01",  // < 16 tuổi (tính đến 2026)
                "male", "", true);
        registerPage.clickSubmit();

        String err = registerPage.getDobError();
        Assert.assertTrue(err.contains("16 tuổi"),
                "TC12 FAIL: Thông báo lỗi không đúng: " + err);
    }

    // =============================================================
    // TC13 – NGÀY SINH TỪ 100 TUỔI TRỞ LÊN
    // =============================================================

    @Test(priority = 13,
          description = "TC13 – Ngày sinh >= 100 tuổi → lỗi tuổi không hợp lệ")
    public void TC13_dobOver100() {
        registerPage.fillForm(
                "Lý Thị Thảo",
                "thaoly12", "thaoly12@gmail.com", "0912000011",
                "Test@1234", "Test@1234",
                "1920-12-31",  // > 100 tuổi
                "female", "", true);
        registerPage.clickSubmit();

        String err = registerPage.getDobError();
        Assert.assertTrue(err.contains("dưới 100"),
                "TC13 FAIL: Thông báo lỗi không đúng: " + err);
    }

    // =============================================================
    // TC14 – MÃ GIỚI THIỆU SAI FORMAT
    // =============================================================

    @Test(priority = 14,
          description = "TC14 – Mã giới thiệu chỉ 3 ký tự → lỗi format")
    public void TC14_referralCodeInvalidFormat() {
        registerPage.fillForm(
                "Cao Văn Tuấn",
                "tuancv13", "tuancv13@gmail.com", "0912000012",
                "Test@1234", "Test@1234",
                "1996-03-25", "male",
                "ABC",  // chỉ 3 ký tự, sai
                true);
        registerPage.clickSubmit();

        String err = registerPage.getReferralCodeError();
        Assert.assertTrue(err.contains("8 ký tự"),
                "TC14 FAIL: Thông báo lỗi không đúng: " + err);
    }

    // =============================================================
    // TC15 – MÃ GIỚI THIỆU ĐÚNG FORMAT NHƯNG KHÔNG TỒN TẠI
    // =============================================================

    @Test(priority = 15,
          description = "TC15 – Mã giới thiệu 8 ký tự nhưng không có trong hệ thống")
    public void TC15_referralCodeNotExist() {
        registerPage.fillForm(
                "Đinh Thị Uyên",
                "uyendt14", "uyendt14@gmail.com", "0912000013",
                "Test@1234", "Test@1234",
                "1997-10-10", "female",
                "XXXX9999",  // đúng format nhưng không tồn tại
                true);
        registerPage.clickSubmit();

        String err = registerPage.getReferralCodeError();
        Assert.assertTrue(err.contains("không tồn tại"),
                "TC15 FAIL: Thông báo lỗi không đúng: " + err);
    }

    // =============================================================
    // TC16 – CHƯA TICK ĐỒNG Ý ĐIỀU KHOẢN
    // =============================================================

    @Test(priority = 16,
          description = "TC16 – Không tick điều khoản → lỗi bắt buộc")
    public void TC16_agreeTermsNotChecked() {
        registerPage.fillForm(
                "Hà Văn Vinh",
                "vinhhv15", "vinhhv15@gmail.com", "0912000014",
                "Test@1234", "Test@1234",
                "1995-05-05", "male", "",
                false);  // chưa tick
        registerPage.clickSubmit();

        String err = registerPage.getAgreeTermsError();
        Assert.assertFalse(err.isEmpty(),
                "TC16 FAIL: Không hiện lỗi khi chưa tick điều khoản.");
        Assert.assertTrue(err.contains("phải đồng ý"),
                "TC16 FAIL: Thông báo lỗi không đúng: " + err);
        // Đảm bảo form không submit thành công
        Assert.assertFalse(registerPage.isSuccessBannerDisplayed(),
                "TC16 FAIL: Banner thành công hiện khi chưa tick điều khoản.");
    }

    // =============================================================
    // TC17 – CLICK "XEM ĐIỀU KHOẢN" MỞ MODAL
    // =============================================================

    @Test(priority = 17,
          description = "TC17 – Click 'Xem Điều khoản' → modal mở thành công")
    public void TC17_viewTermsModalOpens() {
        // Click link xem điều khoản
        registerPage.clickViewTerms();

        Assert.assertTrue(registerPage.isTermsModalDisplayed(),
                "TC17 FAIL: Modal điều khoản không hiện ra sau khi click.");

        String title = registerPage.getTermsModalTitle();
        Assert.assertFalse(title.isEmpty(),
                "TC17 FAIL: Tiêu đề modal trống.");

        Assert.assertTrue(registerPage.isTermsContentVisible(),
                "TC17 FAIL: Nội dung điều khoản không hiển thị trong modal.");
    }

    // =============================================================
    // BONUS TC18 – INVALID REGISTRATION (DataProvider-based)
    // =============================================================

    /**
     * TC18: Test các trường hợp không hợp lệ qua DataProvider.
     * Mỗi dòng trong DataProvider là một test case riêng.
     */
    @Test(priority = 18,
          description = "TC18 – Kiểm tra nhiều trường hợp không hợp lệ qua DataProvider",
          dataProvider = "invalidRegistration",
          dataProviderClass = RegisterDataProvider.class)
    public void TC18_invalidCases(
            String testDesc,
            String fullName, String username, String email, String phone,
            String password, String confirmPassword,
            String dob, String gender, String referralCode, boolean agreeTerms,
            String expectedField, String expectedErrorContains) {

        System.out.println("[TEST] " + testDesc);

        registerPage.fillForm(fullName, username, email, phone,
                password, confirmPassword, dob, gender, referralCode, agreeTerms);
        registerPage.clickSubmit();

        // Lấy thông báo lỗi của trường cần kiểm tra
        String actualError = getErrorByFieldId(expectedField);

        Assert.assertFalse(actualError.isEmpty(),
                testDesc + " – FAIL: Không hiện lỗi cho trường [" + expectedField + "].");
        Assert.assertTrue(actualError.toLowerCase().contains(expectedErrorContains.toLowerCase()),
                testDesc + " – FAIL: Lỗi expected chứa '" + expectedErrorContains
                        + "' nhưng actual là: '" + actualError + "'");
    }

    // -------------------------------------------------------
    // HELPER
    // -------------------------------------------------------

    /** Map tên field → phương thức lấy error message */
    private String getErrorByFieldId(String fieldId) {
        switch (fieldId) {
            case "fullName":        return registerPage.getFullNameError();
            case "username":        return registerPage.getUsernameError();
            case "email":           return registerPage.getEmailError();
            case "phone":           return registerPage.getPhoneError();
            case "password":        return registerPage.getPasswordError();
            case "confirmPassword": return registerPage.getConfirmPasswordError();
            case "dob":             return registerPage.getDobError();
            case "referralCode":    return registerPage.getReferralCodeError();
            case "agreeTerms":      return registerPage.getAgreeTermsError();
            default:                return "";
        }
    }
}
