import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class Bai1PowerUtilTest {
    
    private static final double DELTA = 0.00001;
    
    @Test
    public void kiemTraLuyThuaVoiSoMuBang0() {
        // Kiểm tra n = 0, kết quả phải là 1
        assertEquals(1.0, Bai1PowerUtil.power(5, 0), DELTA);
        assertEquals(1.0, Bai1PowerUtil.power(-3, 0), DELTA);
        assertEquals(1.0, Bai1PowerUtil.power(0, 0), DELTA);
    }
    
    @Test
    public void kiemTraLuyThuaVoiSoMuDuong() {
        // Kiểm tra n > 0
        assertEquals(8.0, Bai1PowerUtil.power(2, 3), DELTA);
        assertEquals(25.0, Bai1PowerUtil.power(5, 2), DELTA);
        assertEquals(1000.0, Bai1PowerUtil.power(10, 3), DELTA);
    }
    
    @Test
    public void kiemTraLuyThuaVoiSoMuAm() {
        // Kiểm tra n < 0
        assertEquals(0.125, Bai1PowerUtil.power(2, -3), DELTA);
        assertEquals(0.25, Bai1PowerUtil.power(2, -2), DELTA);
        assertEquals(0.01, Bai1PowerUtil.power(10, -2), DELTA);
    }
    
    @Test
    public void kiemTraLuyThuaVoiCoSoBang0() {
        // Kiểm tra x = 0 với n dương
        assertEquals(0.0, Bai1PowerUtil.power(0, 5), DELTA);
        assertEquals(0.0, Bai1PowerUtil.power(0, 1), DELTA);
    }
    
    @Test
    public void kiemTraLuyThuaVoiCoSoAm() {
        // Kiểm tra x âm
        assertEquals(-8.0, Bai1PowerUtil.power(-2, 3), DELTA);
        assertEquals(16.0, Bai1PowerUtil.power(-2, 4), DELTA);
        assertEquals(-0.125, Bai1PowerUtil.power(-2, -3), DELTA);
    }
    
    @Test
    public void kiemTraLuyThuaVoiCoSoThuc() {
        // Kiểm tra với số thực
        assertEquals(6.25, Bai1PowerUtil.power(2.5, 2), DELTA);
        assertEquals(27.0, Bai1PowerUtil.power(3.0, 3), DELTA);
        assertEquals(0.01, Bai1PowerUtil.power(0.1, 2), DELTA);
    }
    
    @Test
    public void kiemTraLuyThuaCacTruongHopDacBiet() {
        // Kiểm tra các trường hợp đặc biệt
        assertEquals(1.0, Bai1PowerUtil.power(1, 100), DELTA);
        assertEquals(1.0, Bai1PowerUtil.power(1, -100), DELTA);
        assertEquals(-1.0, Bai1PowerUtil.power(-1, 1), DELTA);
        assertEquals(1.0, Bai1PowerUtil.power(-1, 2), DELTA);
    }
}
