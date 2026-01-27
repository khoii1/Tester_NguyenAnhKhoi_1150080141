package util;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class Validator {
    
    // Validate Customer ID: 6-10 characters, alphanumeric only
    public static String validateCustomerId(String customerId) {
        if (customerId == null || customerId.trim().isEmpty()) {
            return "Mã khách hàng không được để trống";
        }
        
        customerId = customerId.trim();
        
        if (customerId.length() < 6 || customerId.length() > 10) {
            return "Mã khách hàng phải có độ dài từ 6 đến 10 ký tự";
        }
        
        if (!Pattern.matches("^[a-zA-Z0-9]+$", customerId)) {
            return "Mã khách hàng chỉ được chứa chữ cái và số";
        }
        
        return null; // Valid
    }
    
    // Validate Full Name: 5-50 characters, Vietnamese with spaces
    public static String validateFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "Họ và tên không được để trống";
        }
        
        fullName = fullName.trim();
        
        if (fullName.length() < 5 || fullName.length() > 50) {
            return "Họ và tên phải có độ dài từ 5 đến 50 ký tự";
        }
        
        // Allow Vietnamese characters, spaces, and basic letters
        if (!Pattern.matches("^[a-zA-ZÀ-ỹ\\s]+$", fullName)) {
            return "Họ và tên chỉ được chứa chữ cái và khoảng trắng";
        }
        
        return null; // Valid
    }
    
    // Validate Email
    public static String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Email không được để trống";
        }
        
        email = email.trim();
        
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!Pattern.matches(emailRegex, email)) {
            return "Email không đúng định dạng (ví dụ: nguyenvana@email.com)";
        }
        
        return null; // Valid
    }
    
    // Validate Phone Number: 10-12 digits, starts with 0
    public static String validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return "Số điện thoại không được để trống";
        }
        
        phoneNumber = phoneNumber.trim();
        
        if (!Pattern.matches("^[0-9]+$", phoneNumber)) {
            return "Số điện thoại chỉ được chứa số";
        }
        
        if (phoneNumber.length() < 10 || phoneNumber.length() > 12) {
            return "Số điện thoại phải có độ dài từ 10 đến 12 số";
        }
        
        if (!phoneNumber.startsWith("0")) {
            return "Số điện thoại phải bắt đầu bằng số 0";
        }
        
        return null; // Valid
    }
    
    // Validate Address: Required, max 255 characters
    public static String validateAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            return "Địa chỉ không được để trống";
        }
        
        address = address.trim();
        
        if (address.length() > 255) {
            return "Địa chỉ không được vượt quá 255 ký tự";
        }
        
        return null; // Valid
    }
    
    // Validate Password: Minimum 8 characters
    public static String validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return "Mật khẩu không được để trống";
        }
        
        if (password.length() < 8) {
            return "Mật khẩu phải có ít nhất 8 ký tự";
        }
        
        return null; // Valid
    }
    
    // Validate Confirm Password: Must match password
    public static String validateConfirmPassword(String password, String confirmPassword) {
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            return "Xác nhận mật khẩu không được để trống";
        }
        
        if (!confirmPassword.equals(password)) {
            return "Xác nhận mật khẩu không khớp";
        }
        
        return null; // Valid
    }
    
    // Validate Date of Birth: Must be at least 18 years old
    public static String validateDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return null; // Optional field
        }
        
        LocalDate today = LocalDate.now();
        
        if (dateOfBirth.isAfter(today)) {
            return "Ngày sinh không được là ngày trong tương lai";
        }
        
        int age = Period.between(dateOfBirth, today).getYears();
        
        if (age < 18) {
            return "Người dùng phải đủ 18 tuổi";
        }
        
        return null; // Valid
    }
    
    // Validate Terms Acceptance
    public static String validateTermsAcceptance(boolean accepted) {
        if (!accepted) {
            return "Bạn phải đồng ý với các điều khoản dịch vụ";
        }
        
        return null; // Valid
    }
}
