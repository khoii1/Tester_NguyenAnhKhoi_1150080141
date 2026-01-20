import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class Bai2PolynomialEvaluatorTest {
    
    @Test
    public void kiemTraDaThucHopLe() throws ArgumentException {
        // Test đa thức: 2 + 3x + 4x^2 tại x = 2
        // Kết quả: 2 + 3*2 + 4*4 = 2 + 6 + 16 = 24
        int[] coefficients = {2, 3, 4};
        int result = Bai2PolynomialEvaluator.evaluate(2, coefficients, 2);
        assertEquals(24, result);
    }
    
    @Test
    public void kiemTraDaThucBac0() throws ArgumentException {
        // Test đa thức bậc 0: chỉ có hằng số 5
        int[] coefficients = {5};
        int result = Bai2PolynomialEvaluator.evaluate(0, coefficients, 10);
        assertEquals(5, result);
    }
    
    @Test
    public void kiemTraDaThucBac1() throws ArgumentException {
        // Test đa thức bậc 1: 3 + 2x tại x = 4
        // Kết quả: 3 + 2*4 = 11
        int[] coefficients = {3, 2};
        int result = Bai2PolynomialEvaluator.evaluate(1, coefficients, 4);
        assertEquals(11, result);
    }
    
    @Test
    public void kiemTraDaThucVoiXBang0() throws ArgumentException {
        // Test tại x = 0, kết quả chỉ là a_0
        int[] coefficients = {7, 3, 5};
        int result = Bai2PolynomialEvaluator.evaluate(2, coefficients, 0);
        assertEquals(7, result);
    }
    
    @Test
    public void kiemTraDaThucVoiXAm() throws ArgumentException {
        // Test đa thức: 1 + 2x + 3x^2 tại x = -1
        // Kết quả: 1 + 2*(-1) + 3*1 = 1 - 2 + 3 = 2
        int[] coefficients = {1, 2, 3};
        int result = Bai2PolynomialEvaluator.evaluate(2, coefficients, -1);
        assertEquals(2, result);
    }
    
    @Test
    public void kiemTraDaThucBacCao() throws ArgumentException {
        // Test đa thức bậc 4: 1 + 1x + 1x^2 + 1x^3 + 1x^4 tại x = 2
        // Kết quả: 1 + 2 + 4 + 8 + 16 = 31
        int[] coefficients = {1, 1, 1, 1, 1};
        int result = Bai2PolynomialEvaluator.evaluate(4, coefficients, 2);
        assertEquals(31, result);
    }
    
    @Test
    public void kiemTraNAmNemRaException() {
        // Kiểm tra n < 0 phải throw ArgumentException
        int[] coefficients = {1, 2, 3};
        
        ArgumentException exception = assertThrows(
            ArgumentException.class,
            () -> Bai2PolynomialEvaluator.evaluate(-1, coefficients, 5)
        );
        
        assertEquals("Invalid Data", exception.getMessage());
    }
    
    @Test
    public void kiemTraHesoKhongDuNemRaException() {
        // Kiểm tra số lượng coefficients < n+1 phải throw ArgumentException
        int[] coefficients = {1, 2}; // Chỉ có 2 hệ số
        
        ArgumentException exception = assertThrows(
            ArgumentException.class,
            () -> Bai2PolynomialEvaluator.evaluate(3, coefficients, 5) // Cần 4 hệ số
        );
        
        assertEquals("Invalid Data", exception.getMessage());
    }
    
    @Test
    public void kiemTraHesoThuaNemRaException() {
        // Kiểm tra số lượng coefficients > n+1 phải throw ArgumentException
        int[] coefficients = {1, 2, 3, 4, 5}; // Có 5 hệ số
        
        ArgumentException exception = assertThrows(
            ArgumentException.class,
            () -> Bai2PolynomialEvaluator.evaluate(2, coefficients, 5) // Chỉ cần 3 hệ số
        );
        
        assertEquals("Invalid Data", exception.getMessage());
    }
    
    @Test
    public void kiemTraHesoNullNemRaException() {
        // Kiểm tra coefficients = null phải throw ArgumentException
        ArgumentException exception = assertThrows(
            ArgumentException.class,
            () -> Bai2PolynomialEvaluator.evaluate(2, null, 5)
        );
        
        assertEquals("Invalid Data", exception.getMessage());
    }
}
