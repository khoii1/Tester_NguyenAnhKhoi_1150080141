/**
 * Bài 2: Tính giá trị đa thức tại một giá trị x
 * Đa thức: a_0 + a_1*x + a_2*x^2 + ... + a_n*x^n
 */
public class Bai2PolynomialEvaluator {
    
    /**
     * Tính giá trị đa thức tại x
     * @param n bậc của đa thức (phải >= 0)
     * @param coefficients mảng hệ số [a_0, a_1, ..., a_n] với độ dài n+1
     * @param x giá trị biến nguyên
     * @return giá trị đa thức tại x
     * @throws ArgumentException nếu n < 0 hoặc số lượng coefficients != n+1
     */
    public static int evaluate(int n, int[] coefficients, int x) throws ArgumentException {
        // Kiểm tra n phải >= 0
        if (n < 0) {
            throw new ArgumentException("Invalid Data");
        }
        
        // Kiểm tra số lượng hệ số phải đúng n+1
        if (coefficients == null || coefficients.length != n + 1) {
            throw new ArgumentException("Invalid Data");
        }
        
        // Tính giá trị đa thức: a_0 + a_1*x + a_2*x^2 + ... + a_n*x^n
        int result = 0;
        int powerOfX = 1; // x^i, bắt đầu từ x^0 = 1
        
        for (int i = 0; i <= n; i++) {
            result += coefficients[i] * powerOfX;
            powerOfX *= x; // Tính x^(i+1)
        }
        
        return result;
    }
}
