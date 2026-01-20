/**
 * Bài 3: Chuyển đổi số nguyên dương từ cơ số 10 sang cơ số k (2 ≤ k ≤ 16)
 */
public class Bai3BaseConverter {
    
    /**
     * Chuyển đổi số nguyên dương từ cơ số 10 sang cơ số k
     * @param number số nguyên dương cần chuyển đổi (>= 0)
     * @param base cơ số đích (2 ≤ k ≤ 16)
     * @return chuỗi biểu diễn số trong cơ số k
     * @throws ArgumentException nếu base < 2 hoặc base > 16, hoặc number < 0
     */
    public static String convert(int number, int base) throws ArgumentException {
        // Validate base
        if (base < 2 || base > 16) {
            throw new ArgumentException("Base must be between 2 and 16");
        }
        
        // Validate number
        if (number < 0) {
            throw new ArgumentException("Number must be non-negative");
        }
        
        // Trường hợp đặc biệt: số 0
        if (number == 0) {
            return "0";
        }
        
        // Bảng ký tự cho các chữ số từ 0-15
        char[] digits = "0123456789ABCDEF".toCharArray();
        
        // Chuyển đổi
        StringBuilder result = new StringBuilder();
        int temp = number;
        
        while (temp > 0) {
            int remainder = temp % base;
            result.insert(0, digits[remainder]);
            temp = temp / base;
        }
        
        return result.toString();
    }
}
