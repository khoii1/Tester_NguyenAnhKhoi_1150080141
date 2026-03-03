package utils;

import org.testng.annotations.DataProvider;

/**
 * RegisterDataProvider – cung cấp dữ liệu test cho RegisterTest.
 *
 * Mỗi DataProvider trả về Object[][] với các tham số khớp
 * chữ ký phương thức test tương ứng.
 */
public class RegisterDataProvider {

    /**
     * Dữ liệu hợp lệ dùng để đăng ký thành công.
     * Cột: fullName, username, email, phone, password, confirmPassword,
     *       dob, gender, referralCode, agreeTerms
     */
    @DataProvider(name = "validRegistration")
    public static Object[][] validRegistration() {
        return new Object[][] {
            {
                "Nguyễn Văn An",        // fullName
                "vanan2024",            // username
                "vanan2024@gmail.com",  // email
                "0912345678",           // phone
                "Shopvn@2024",          // password
                "Shopvn@2024",          // confirmPassword
                "2000-05-15",           // dob (yyyy-MM-dd)
                "male",                 // gender
                "SHOP2024",             // referralCode (hợp lệ+tồn tại)
                true                    // agreeTerms
            }
        };
    }

    /**
     * Dữ liệu không hợp lệ cho từng trường.
     * Dùng cho các test case âm (negative tests).
     *
     * Cột: testDescription, fullName, username, email, phone,
     *        password, confirmPassword, dob, gender, referralCode,
     *        agreeTerms, expectedErrorField, expectedErrorMsgContains
     */
    @DataProvider(name = "invalidRegistration")
    public static Object[][] invalidRegistration() {
        // Dữ liệu mặc định hợp lệ cho các trường không test
        final String goodName    = "Trần Thị Bình";
        final String goodUser    = "binhtest1";
        final String goodEmail   = "binhtest1@gmail.com";
        final String goodPhone   = "0987654321";
        final String goodPwd     = "Test@1234";
        final String goodDob     = "1998-06-20";
        final String goodGender  = "female";
        final String goodRef     = "";   // không nhập mã giới thiệu

        return new Object[][] {

            // ── Họ và tên ──────────────────────────────────────────
            {
                "TC02 – Họ và tên để trống",
                "",                                         // fullName rỗng
                goodUser, goodEmail, goodPhone, goodPwd, goodPwd,
                goodDob, goodGender, goodRef, true,
                "fullName", "không được để trống"
            },
            {
                "TC03 – Họ và tên dưới min length (1 ký tự)",
                "A",                                        // fullName quá ngắn
                goodUser, goodEmail, goodPhone, goodPwd, goodPwd,
                goodDob, goodGender, goodRef, true,
                "fullName", "ít nhất 2 ký tự"
            },
            {
                "TC04 – Họ và tên chứa ký tự đặc biệt",
                "Nguyen@123",                               // fullName chứa số/đặc biệt
                goodUser, goodEmail, goodPhone, goodPwd, goodPwd,
                goodDob, goodGender, goodRef, true,
                "fullName", "chỉ được chứa chữ cái"
            },

            // ── Tên đăng nhập ──────────────────────────────────────
            {
                "TC05 – Username không đúng format (chứa chữ hoa)",
                goodName,
                "UserABC",                                  // chứa chữ hoa
                goodEmail, goodPhone, goodPwd, goodPwd,
                goodDob, goodGender, goodRef, true,
                "username", "chữ thường, số và dấu gạch dưới"
            },
            {
                "TC06 – Username đã tồn tại",
                goodName,
                "admin",                                    // username đã tồn tại
                goodEmail, goodPhone, goodPwd, goodPwd,
                goodDob, goodGender, goodRef, true,
                "username", "đã tồn tại"
            },
            {
                "TC07 – Username dưới min length (4 ký tự)",
                goodName,
                "abc1",                                     // 4 ký tự < 5
                goodEmail, goodPhone, goodPwd, goodPwd,
                goodDob, goodGender, goodRef, true,
                "username", "ít nhất 5 ký tự"
            },

            // ── Email ──────────────────────────────────────────────
            {
                "TC08 – Email sai format",
                goodName, goodUser,
                "emailkhonghople",                          // thiếu @ và domain
                goodPhone, goodPwd, goodPwd,
                goodDob, goodGender, goodRef, true,
                "email", "không đúng định dạng"
            },
            {
                "TC09 – Email đã tồn tại",
                goodName, goodUser,
                "admin@shopvn.vn",                          // email đã tồn tại
                goodPhone, goodPwd, goodPwd,
                goodDob, goodGender, goodRef, true,
                "email", "đã được đăng ký"
            },

            // ── Số điện thoại ──────────────────────────────────────
            {
                "TC10 – Số điện thoại không đủ 10 số",
                goodName, goodUser, goodEmail,
                "091234",                                   // chỉ 6 chữ số
                goodPwd, goodPwd,
                goodDob, goodGender, goodRef, true,
                "phone", "đúng 10 chữ số"
            },
            {
                "TC11 – Số điện thoại không bắt đầu bằng 0",
                goodName, goodUser, goodEmail,
                "1912345678",                               // bắt đầu bằng 1
                goodPwd, goodPwd,
                goodDob, goodGender, goodRef, true,
                "phone", "bắt đầu bằng 0"
            },

            // ── Mật khẩu ──────────────────────────────────────────
            {
                "TC12 – Mật khẩu không đủ mạnh (thiếu chữ hoa)",
                goodName, goodUser, goodEmail, goodPhone,
                "test@1234",                                // không có chữ hoa
                "test@1234",
                goodDob, goodGender, goodRef, true,
                "password", "ít nhất 1 chữ hoa"
            },
            {
                "TC13 – Mật khẩu quá ngắn (< 8 ký tự)",
                goodName, goodUser, goodEmail, goodPhone,
                "Ab@1",                                     // 4 ký tự
                "Ab@1",
                goodDob, goodGender, goodRef, true,
                "password", "ít nhất 8 ký tự"
            },

            // ── Xác nhận mật khẩu ─────────────────────────────────
            {
                "TC14 – Confirm password không khớp",
                goodName, goodUser, goodEmail, goodPhone,
                goodPwd, "DifferentPwd@99",                 // khác mật khẩu gốc
                goodDob, goodGender, goodRef, true,
                "confirmPassword", "không khớp"
            },

            // ── Ngày sinh ──────────────────────────────────────────
            {
                "TC15 – Ngày sinh dưới 16 tuổi",
                goodName, goodUser, goodEmail, goodPhone,
                goodPwd, goodPwd,
                "2015-01-01",                               // < 16 tuổi (as of 2026)
                goodGender, goodRef, true,
                "dob", "đủ 16 tuổi"
            },
            {
                "TC16 – Ngày sinh từ 100 tuổi trở lên",
                goodName, goodUser, goodEmail, goodPhone,
                goodPwd, goodPwd,
                "1920-12-31",                               // > 100 tuổi
                goodGender, goodRef, true,
                "dob", "dưới 100"
            },

            // ── Mã giới thiệu ──────────────────────────────────────
            {
                "TC17 – Mã giới thiệu sai format (ít hơn 8 ký tự)",
                goodName, goodUser, goodEmail, goodPhone,
                goodPwd, goodPwd, goodDob, goodGender,
                "ABC",                                      // 3 ký tự, sai format
                true,
                "referralCode", "đúng 8 ký tự"
            },
            {
                "TC18 – Mã giới thiệu đúng format nhưng không tồn tại",
                goodName, goodUser, goodEmail, goodPhone,
                goodPwd, goodPwd, goodDob, goodGender,
                "XXXX9999",                                 // 8 ký tự hợp lệ nhưng không có trong DS
                true,
                "referralCode", "không tồn tại"
            },

            // ── Điều khoản ────────────────────────────────────────
            {
                "TC19 – Chưa tick đồng ý điều khoản",
                goodName, goodUser, goodEmail, goodPhone,
                goodPwd, goodPwd, goodDob, goodGender, goodRef,
                false,                                      // agreeTerms = false
                "agreeTerms", "phải đồng ý"
            },
        };
    }
}
