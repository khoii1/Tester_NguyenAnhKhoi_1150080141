import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

import util.Validator;

/**
 * Test cases for Validator class using:
 * - Equivalence Partitioning
 * - Boundary Value Analysis
 * - Decision Table Testing
 */
public class ValidatorTest {
    
    // ========== Customer ID Tests ==========
    
    @Test
    public void testCustomerId_Valid() {
        // Valid: 6-10 characters, alphanumeric
        assertNull(Validator.validateCustomerId("KH0001")); // 6 chars
        assertNull(Validator.validateCustomerId("ABC123")); // 6 chars
        assertNull(Validator.validateCustomerId("Customer1")); // 9 chars
        assertNull(Validator.validateCustomerId("1234567890")); // 10 chars
    }
    
    @Test
    public void testCustomerId_Empty() {
        assertNotNull(Validator.validateCustomerId(""));
        assertNotNull(Validator.validateCustomerId(null));
    }
    
    @Test
    public void testCustomerId_TooShort() {
        // Boundary: < 6 characters
        assertNotNull(Validator.validateCustomerId("KH01")); // 4 chars
        assertNotNull(Validator.validateCustomerId("ABC12")); // 5 chars
    }
    
    @Test
    public void testCustomerId_TooLong() {
        // Boundary: > 10 characters
        assertNotNull(Validator.validateCustomerId("12345678901")); // 11 chars
        assertNotNull(Validator.validateCustomerId("ABCDEFGHIJK")); // 11 chars
    }
    
    @Test
    public void testCustomerId_BoundaryValues() {
        // Exactly 6 characters (lower boundary)
        assertNull(Validator.validateCustomerId("ABC123"));
        
        // Exactly 10 characters (upper boundary)
        assertNull(Validator.validateCustomerId("ABCDEFGHIJ"));
    }
    
    @Test
    public void testCustomerId_InvalidCharacters() {
        assertNotNull(Validator.validateCustomerId("KH@001")); // Special char
        assertNotNull(Validator.validateCustomerId("KH 001")); // Space
        assertNotNull(Validator.validateCustomerId("KH-001")); // Hyphen
        assertNotNull(Validator.validateCustomerId("Khách01")); // Vietnamese char
    }
    
    // ========== Full Name Tests ==========
    
    @Test
    public void testFullName_Valid() {
        assertNull(Validator.validateFullName("Nguyen Van A"));
        assertNull(Validator.validateFullName("Nguyễn Văn Á"));
        assertNull(Validator.validateFullName("Trần Thị Bình"));
        assertNull(Validator.validateFullName("Le Hoang Minh Chau"));
    }
    
    @Test
    public void testFullName_Empty() {
        assertNotNull(Validator.validateFullName(""));
        assertNotNull(Validator.validateFullName(null));
        assertNotNull(Validator.validateFullName("   "));
    }
    
    @Test
    public void testFullName_TooShort() {
        // < 5 characters
        assertNotNull(Validator.validateFullName("Tran")); // 4 chars
        assertNotNull(Validator.validateFullName("A B")); // 3 chars
    }
    
    @Test
    public void testFullName_TooLong() {
        // > 50 characters
        String longName = "Nguyen Van Anh Tuan Hoang Minh Duc Long Thanh Binh Phuong";
        assertNotNull(Validator.validateFullName(longName)); // 58 chars
    }
    
    @Test
    public void testFullName_BoundaryValues() {
        // Exactly 5 characters (lower boundary)
        assertNull(Validator.validateFullName("An Bi"));
        
        // Exactly 50 characters (upper boundary)
        assertNull(Validator.validateFullName("Nguyen Van Anh Tuan Hoang Minh Duc Long Thanh"));
    }
    
    // ========== Email Tests ==========
    
    @Test
    public void testEmail_Valid() {
        assertNull(Validator.validateEmail("test@example.com"));
        assertNull(Validator.validateEmail("user123@gmail.com"));
        assertNull(Validator.validateEmail("nguyen.van.a@company.vn"));
        assertNull(Validator.validateEmail("admin@test.co.uk"));
    }
    
    @Test
    public void testEmail_Empty() {
        assertNotNull(Validator.validateEmail(""));
        assertNotNull(Validator.validateEmail(null));
    }
    
    @Test
    public void testEmail_InvalidFormat() {
        assertNotNull(Validator.validateEmail("notanemail"));
        assertNotNull(Validator.validateEmail("@example.com"));
        assertNotNull(Validator.validateEmail("test@"));
        assertNotNull(Validator.validateEmail("test@@example.com"));
        assertNotNull(Validator.validateEmail("test @example.com")); // Space
        assertNotNull(Validator.validateEmail("test@.com"));
    }
    
    // ========== Phone Number Tests ==========
    
    @Test
    public void testPhoneNumber_Valid() {
        assertNull(Validator.validatePhoneNumber("0123456789")); // 10 digits
        assertNull(Validator.validatePhoneNumber("0987654321")); // 10 digits
        assertNull(Validator.validatePhoneNumber("012345678901")); // 12 digits
    }
    
    @Test
    public void testPhoneNumber_Empty() {
        assertNotNull(Validator.validatePhoneNumber(""));
        assertNotNull(Validator.validatePhoneNumber(null));
    }
    
    @Test
    public void testPhoneNumber_NotStartWithZero() {
        assertNotNull(Validator.validatePhoneNumber("1234567890"));
        assertNotNull(Validator.validatePhoneNumber("9876543210"));
    }
    
    @Test
    public void testPhoneNumber_TooShort() {
        // < 10 digits
        assertNotNull(Validator.validatePhoneNumber("012345678")); // 9 digits
        assertNotNull(Validator.validatePhoneNumber("0123")); // 4 digits
    }
    
    @Test
    public void testPhoneNumber_TooLong() {
        // > 12 digits
        assertNotNull(Validator.validatePhoneNumber("0123456789012")); // 13 digits
    }
    
    @Test
    public void testPhoneNumber_BoundaryValues() {
        // Exactly 10 digits (lower boundary)
        assertNull(Validator.validatePhoneNumber("0123456789"));
        
        // Exactly 12 digits (upper boundary)
        assertNull(Validator.validatePhoneNumber("012345678901"));
    }
    
    @Test
    public void testPhoneNumber_InvalidCharacters() {
        assertNotNull(Validator.validatePhoneNumber("012-345-6789"));
        assertNotNull(Validator.validatePhoneNumber("(012) 345-6789"));
        assertNotNull(Validator.validatePhoneNumber("012.345.6789"));
        assertNotNull(Validator.validatePhoneNumber("012 345 6789"));
        assertNotNull(Validator.validatePhoneNumber("012abc5678"));
    }
    
    // ========== Address Tests ==========
    
    @Test
    public void testAddress_Valid() {
        assertNull(Validator.validateAddress("123 Nguyen Hue Street"));
        assertNull(Validator.validateAddress("Số 45, Đường Lê Lợi, Phường 1, Quận 1, TP.HCM"));
        assertNull(Validator.validateAddress("Apartment 5B, Building A"));
    }
    
    @Test
    public void testAddress_Empty() {
        assertNotNull(Validator.validateAddress(""));
        assertNotNull(Validator.validateAddress(null));
        assertNotNull(Validator.validateAddress("   "));
    }
    
    @Test
    public void testAddress_TooLong() {
        // > 255 characters
        String longAddress = "A".repeat(256);
        assertNotNull(Validator.validateAddress(longAddress));
    }
    
    @Test
    public void testAddress_BoundaryValue() {
        // Exactly 255 characters (upper boundary)
        String maxAddress = "A".repeat(255);
        assertNull(Validator.validateAddress(maxAddress));
    }
    
    // ========== Password Tests ==========
    
    @Test
    public void testPassword_Valid() {
        assertNull(Validator.validatePassword("password123"));
        assertNull(Validator.validatePassword("MyP@ssw0rd"));
        assertNull(Validator.validatePassword("12345678")); // Exactly 8 chars
    }
    
    @Test
    public void testPassword_Empty() {
        assertNotNull(Validator.validatePassword(""));
        assertNotNull(Validator.validatePassword(null));
    }
    
    @Test
    public void testPassword_TooShort() {
        // < 8 characters
        assertNotNull(Validator.validatePassword("pass")); // 4 chars
        assertNotNull(Validator.validatePassword("1234567")); // 7 chars
    }
    
    @Test
    public void testPassword_BoundaryValue() {
        // Exactly 8 characters (lower boundary)
        assertNull(Validator.validatePassword("password"));
    }
    
    // ========== Confirm Password Tests ==========
    
    @Test
    public void testConfirmPassword_Match() {
        assertNull(Validator.validateConfirmPassword("password123", "password123"));
        assertNull(Validator.validateConfirmPassword("MyP@ss", "MyP@ss"));
    }
    
    @Test
    public void testConfirmPassword_NotMatch() {
        assertNotNull(Validator.validateConfirmPassword("password123", "password124"));
        assertNotNull(Validator.validateConfirmPassword("MyPass", "mypass")); // Case sensitive
    }
    
    @Test
    public void testConfirmPassword_Empty() {
        assertNotNull(Validator.validateConfirmPassword("password", ""));
        assertNotNull(Validator.validateConfirmPassword("", "password"));
        assertNotNull(Validator.validateConfirmPassword("", ""));
    }
    
    // ========== Date of Birth Tests ==========
    
    @Test
    public void testDateOfBirth_Valid() {
        // Over 18 years old
        LocalDate date20YearsAgo = LocalDate.now().minusYears(20);
        assertNull(Validator.validateDateOfBirth(date20YearsAgo));
        
        LocalDate date30YearsAgo = LocalDate.now().minusYears(30);
        assertNull(Validator.validateDateOfBirth(date30YearsAgo));
    }
    
    @Test
    public void testDateOfBirth_Under18() {
        // Under 18 years old
        LocalDate date17YearsAgo = LocalDate.now().minusYears(17);
        assertNotNull(Validator.validateDateOfBirth(date17YearsAgo));
        
        LocalDate date10YearsAgo = LocalDate.now().minusYears(10);
        assertNotNull(Validator.validateDateOfBirth(date10YearsAgo));
        
        LocalDate today = LocalDate.now();
        assertNotNull(Validator.validateDateOfBirth(today));
    }
    
    @Test
    public void testDateOfBirth_BoundaryValue() {
        // Exactly 18 years old (boundary)
        LocalDate exactly18YearsAgo = LocalDate.now().minusYears(18);
        assertNull(Validator.validateDateOfBirth(exactly18YearsAgo));
        
        // One day before 18 years
        LocalDate almostBdaysAgo = LocalDate.now().minusYears(18).plusDays(1);
        assertNotNull(Validator.validateDateOfBirth(almostBdaysAgo));
    }
    
    @Test
    public void testDateOfBirth_Future() {
        // Future date
        LocalDate futureDate = LocalDate.now().plusDays(1);
        assertNotNull(Validator.validateDateOfBirth(futureDate));
    }
    
    // ========== Terms Acceptance Tests ==========
    
    @Test
    public void testTermsAcceptance_Accepted() {
        assertNull(Validator.validateTermsAcceptance(true));
    }
    
    @Test
    public void testTermsAcceptance_NotAccepted() {
        assertNotNull(Validator.validateTermsAcceptance(false));
    }
    
    // ========== FAILING TESTS (Bugs to be Fixed) ==========
    
    /**
     * BUG #1: Validator không kiểm tra email domain hợp lệ
     * Theo yêu cầu mới: chỉ chấp nhận email có domain trong whitelist
     * Expected: Email với domain không hợp lệ như @hacker.com nên bị reject
     * CURRENTLY FAILING - Domain validation not implemented
     */
    @Test
    public void testEmail_InvalidDomain() {
        // Bug: Should reject suspicious domains
        assertNotNull(Validator.validateEmail("test@suspicious-site.xyz"));
        assertNotNull(Validator.validateEmail("user@tempmail.com"));
    }
    
    /**
     * BUG #2: Validator cho phép số điện thoại có ký tự đặc biệt
     * Theo yêu cầu: chỉ chấp nhận số 0-9
     * Expected: Reject phone numbers with special chars
     * CURRENTLY FAILING - Character validation incomplete
     */
    @Test
    public void testPhoneNumber_SpecialCharacters() {
        // Bug: Should reject phone with special characters
        assertNotNull(Validator.validatePhoneNumber("0123-456-789"));
        assertNotNull(Validator.validatePhoneNumber("0123.456.789"));
        assertNotNull(Validator.validatePhoneNumber("(012)3456789"));
    }
    
    /**
     * BUG #3: Password validator không kiểm tra độ mạnh của mật khẩu
     * Theo yêu cầu mới: mật khẩu phải có ít nhất 1 chữ hoa, 1 chữ thường, 1 số
     * Expected: Reject weak passwords like "12345678" or "abcdefgh"
     * CURRENTLY FAILING - Password strength validation not implemented
     */
    @Test
    public void testPassword_WeakPassword() {
        // Bug: Should reject passwords without uppercase, lowercase, and numbers
        assertNotNull(Validator.validatePassword("12345678")); // Only numbers
        assertNotNull(Validator.validatePassword("abcdefgh")); // Only lowercase
        assertNotNull(Validator.validatePassword("ABCDEFGH")); // Only uppercase
    }
    
    /**
     * BUG #4: Customer ID cho phép ký tự đặc biệt trong một số trường hợp
     * Expected: Strict alphanumeric validation
     * CURRENTLY FAILING - Regex validation has edge cases
     */
    @Test
    public void testCustomerId_EdgeCaseCharacters() {
        // Bug: Should reject special characters in all positions
        assertNotNull(Validator.validateCustomerId("KH_001"));
        assertNotNull(Validator.validateCustomerId("KH.001"));
    }
    
    /**
     * BUG #5: Full name validator không kiểm tra định dạng tên hợp lệ
     * Theo yêu cầu: tên phải có ít nhất 2 từ (họ và tên)
     * Expected: Reject single word names
     * CURRENTLY FAILING - Word count validation not implemented
     */
    @Test
    public void testFullName_SingleWord() {
        // Bug: Should require at least first name and last name
        assertNotNull(Validator.validateFullName("SingleName"));
        assertNotNull(Validator.validateFullName("Khôi"));
    }
}
