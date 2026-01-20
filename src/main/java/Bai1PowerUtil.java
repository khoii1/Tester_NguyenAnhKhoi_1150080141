public class Bai1PowerUtil {
    
    /**
     * Tính lũy thừa x^n bằng đệ quy
     * @param x cơ số
     * @param n số mũ
     * @return kết quả x^n
     */
    public static double power(double x, int n) {
        // Trường hợp n = 0
        if (n == 0) {
            return 1;
        }
        
        // Trường hợp n > 0
        if (n > 0) {
            return power(x, n - 1) * x;
        }
        
        // Trường hợp n < 0
        return power(x, n + 1) / x;
    }
}
