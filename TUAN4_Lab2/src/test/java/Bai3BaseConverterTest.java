import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class Bai3BaseConverterTest {
    
    @Test
    public void kiemTraChuyenSangNhiPhan() throws ArgumentException {
        // Test chuyển sang cơ số 2 (binary)
        assertEquals("1010", Bai3BaseConverter.convert(10, 2));
        assertEquals("11111111", Bai3BaseConverter.convert(255, 2));
        assertEquals("1", Bai3BaseConverter.convert(1, 2));
    }
    
    @Test
    public void kiemTraChuyenSangBatPhan() throws ArgumentException {
        // Test chuyển sang cơ số 8 (octal)
        assertEquals("12", Bai3BaseConverter.convert(10, 8));
        assertEquals("377", Bai3BaseConverter.convert(255, 8));
        assertEquals("100", Bai3BaseConverter.convert(64, 8));
    }
    
    @Test
    public void kiemTraChuyenSangThapPhan() throws ArgumentException {
        // Test chuyển sang cơ số 10 (decimal) - không đổi
        assertEquals("10", Bai3BaseConverter.convert(10, 10));
        assertEquals("255", Bai3BaseConverter.convert(255, 10));
        assertEquals("1000", Bai3BaseConverter.convert(1000, 10));
    }
    
    @Test
    public void kiemTraChuyenSangThapLucPhan() throws ArgumentException {
        // Test chuyển sang cơ số 16 (hexadecimal)
        assertEquals("A", Bai3BaseConverter.convert(10, 16));
        assertEquals("FF", Bai3BaseConverter.convert(255, 16));
        assertEquals("1F4", Bai3BaseConverter.convert(500, 16));
        assertEquals("BEEF", Bai3BaseConverter.convert(48879, 16));
    }
    
    @Test
    public void kiemTraChuyenSo0() throws ArgumentException {
        // Test số 0 với các cơ số khác nhau
        assertEquals("0", Bai3BaseConverter.convert(0, 2));
        assertEquals("0", Bai3BaseConverter.convert(0, 8));
        assertEquals("0", Bai3BaseConverter.convert(0, 10));
        assertEquals("0", Bai3BaseConverter.convert(0, 16));
    }
    
    @Test
    public void kiemTraChuyenSangCoSo3() throws ArgumentException {
        // Test chuyển sang cơ số 3
        assertEquals("100", Bai3BaseConverter.convert(9, 3));
        assertEquals("1120", Bai3BaseConverter.convert(42, 3));
    }
    
    @Test
    public void kiemTraChuyenSangCoSo5() throws ArgumentException {
        // Test chuyển sang cơ số 5
        assertEquals("20", Bai3BaseConverter.convert(10, 5));
        assertEquals("2010", Bai3BaseConverter.convert(255, 5));
    }
    
    @Test
    public void kiemTraChuyenSangCoSo12() throws ArgumentException {
        // Test chuyển sang cơ số 12
        assertEquals("A", Bai3BaseConverter.convert(10, 12));
        assertEquals("B", Bai3BaseConverter.convert(11, 12));
        assertEquals("193", Bai3BaseConverter.convert(255, 12));
    }
    
    @Test
    public void kiemTraChuyenSoLon() throws ArgumentException {
        // Test số lớn
        assertEquals("F4240", Bai3BaseConverter.convert(1000000, 16));
        assertEquals("11110100001001000000", Bai3BaseConverter.convert(1000000, 2));
    }
    
    @Test
    public void kiemTraCoSoQuaNho() {
        // Test base < 2 phải throw ArgumentException
        ArgumentException exception = assertThrows(
            ArgumentException.class,
            () -> Bai3BaseConverter.convert(10, 1)
        );
        assertEquals("Base must be between 2 and 16", exception.getMessage());
        
        exception = assertThrows(
            ArgumentException.class,
            () -> Bai3BaseConverter.convert(10, 0)
        );
        assertEquals("Base must be between 2 and 16", exception.getMessage());
    }
    
    @Test
    public void kiemTraCoSoQuaLon() {
        // Test base > 16 phải throw ArgumentException
        ArgumentException exception = assertThrows(
            ArgumentException.class,
            () -> Bai3BaseConverter.convert(10, 17)
        );
        assertEquals("Base must be between 2 and 16", exception.getMessage());
        
        exception = assertThrows(
            ArgumentException.class,
            () -> Bai3BaseConverter.convert(10, 20)
        );
        assertEquals("Base must be between 2 and 16", exception.getMessage());
    }
    
    @Test
    public void kiemTraSoAm() {
        // Test số âm phải throw ArgumentException
        ArgumentException exception = assertThrows(
            ArgumentException.class,
            () -> Bai3BaseConverter.convert(-10, 10)
        );
        assertEquals("Number must be non-negative", exception.getMessage());
    }
    
    @Test
    public void kiemTraTatCaCoSoHopLe() throws ArgumentException {
        // Test tất cả các base hợp lệ từ 2 đến 16
        int number = 100;
        
        // Chỉ kiểm tra không throw exception
        assertDoesNotThrow(() -> {
            for (int base = 2; base <= 16; base++) {
                String result = Bai3BaseConverter.convert(number, base);
                assertNotNull(result);
                assertFalse(result.isEmpty());
            }
        });
    }
    
    @Test
    public void kiemTraTruongHopDacBietCoSo16() throws ArgumentException {
        // Test các trường hợp đặc biệt với hexadecimal
        assertEquals("10", Bai3BaseConverter.convert(16, 16));
        assertEquals("100", Bai3BaseConverter.convert(256, 16));
        assertEquals("FFFF", Bai3BaseConverter.convert(65535, 16));
    }
}
