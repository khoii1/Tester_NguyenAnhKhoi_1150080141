import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class Bai4HinhChuNhatTest {
    
    private static final double DELTA = 0.00001;
    
    @Test
    public void kiemTraDiemConstructorVaGetters() {
        // Test constructor và getters của lớp Diem
        Diem diem = new Diem(3.5, 4.5);
        assertEquals(3.5, diem.getX(), DELTA);
        assertEquals(4.5, diem.getY(), DELTA);
    }
    
    @Test
    public void kiemTraDiemSetters() {
        // Test setters của lớp Diem
        Diem diem = new Diem(1, 2);
        diem.setX(5.5);
        diem.setY(6.5);
        assertEquals(5.5, diem.getX(), DELTA);
        assertEquals(6.5, diem.getY(), DELTA);
    }
    
    @Test
    public void kiemTraHinhChuNhatConstructor() {
        // Test constructor của HinhChuNhat
        Diem topLeft = new Diem(0, 5);
        Diem bottomRight = new Diem(4, 0);
        HinhChuNhat hcn = new HinhChuNhat(topLeft, bottomRight);
        
        assertNotNull(hcn.getTopLeft());
        assertNotNull(hcn.getBottomRight());
        assertEquals(0, hcn.getTopLeft().getX(), DELTA);
        assertEquals(5, hcn.getTopLeft().getY(), DELTA);
    }
    
    @Test
    public void kiemTraTinhDienTichCoBan() {
        // Test tính diện tích cơ bản
        Diem topLeft = new Diem(0, 5);
        Diem bottomRight = new Diem(4, 0);
        HinhChuNhat hcn = new HinhChuNhat(topLeft, bottomRight);
        
        // Diện tích = 4 * 5 = 20
        assertEquals(20.0, hcn.tinhDienTich(), DELTA);
    }
    
    @Test
    public void kiemTraTinhDienTichHinhVuong() {
        // Test tính diện tích hình vuông
        Diem topLeft = new Diem(1, 6);
        Diem bottomRight = new Diem(6, 1);
        HinhChuNhat hcn = new HinhChuNhat(topLeft, bottomRight);
        
        // Diện tích = 5 * 5 = 25
        assertEquals(25.0, hcn.tinhDienTich(), DELTA);
    }
    
    @Test
    public void kiemTraTinhDienTichVoiToaDoAm() {
        // Test với tọa độ âm
        Diem topLeft = new Diem(-5, 3);
        Diem bottomRight = new Diem(-2, -1);
        HinhChuNhat hcn = new HinhChuNhat(topLeft, bottomRight);
        
        // Diện tích = 3 * 4 = 12
        assertEquals(12.0, hcn.tinhDienTich(), DELTA);
    }
    
    @Test
    public void kiemTraTinhDienTichVoiSoThuc() {
        // Test với số thực
        Diem topLeft = new Diem(0.5, 5.5);
        Diem bottomRight = new Diem(3.5, 2.5);
        HinhChuNhat hcn = new HinhChuNhat(topLeft, bottomRight);
        
        // Diện tích = 3.0 * 3.0 = 9.0
        assertEquals(9.0, hcn.tinhDienTich(), DELTA);
    }
    
    @Test
    public void kiemTraHaiHinhGiaoNhau() {
        // Test hai hình chữ nhật giao nhau
        HinhChuNhat hcn1 = new HinhChuNhat(new Diem(0, 5), new Diem(5, 0));
        HinhChuNhat hcn2 = new HinhChuNhat(new Diem(3, 3), new Diem(7, -1));
        
        assertTrue(hcn1.kiemTraGiaoNhau(hcn2));
        assertTrue(hcn2.kiemTraGiaoNhau(hcn1)); // Kiểm tra tính đối xứng
    }
    
    @Test
    public void kiemTraHaiHinhKhongGiaoNhauBenTrai() {
        // Test hai hình không giao nhau (hình 1 nằm bên trái hình 2)
        HinhChuNhat hcn1 = new HinhChuNhat(new Diem(0, 5), new Diem(2, 0));
        HinhChuNhat hcn2 = new HinhChuNhat(new Diem(3, 5), new Diem(6, 0));
        
        assertFalse(hcn1.kiemTraGiaoNhau(hcn2));
        assertFalse(hcn2.kiemTraGiaoNhau(hcn1));
    }
    
    @Test
    public void kiemTraHaiHinhKhongGiaoNhauPhiaTren() {
        // Test hai hình không giao nhau (hình 1 nằm phía trên hình 2)
        HinhChuNhat hcn1 = new HinhChuNhat(new Diem(0, 10), new Diem(5, 6));
        HinhChuNhat hcn2 = new HinhChuNhat(new Diem(0, 5), new Diem(5, 0));
        
        assertFalse(hcn1.kiemTraGiaoNhau(hcn2));
        assertFalse(hcn2.kiemTraGiaoNhau(hcn1));
    }
    
    @Test
    public void kiemTraHaiHinhChamCanh() {
        // Test hai hình chạm cạnh nhau (có thể coi là giao nhau hoặc không)
        HinhChuNhat hcn1 = new HinhChuNhat(new Diem(0, 5), new Diem(3, 0));
        HinhChuNhat hcn2 = new HinhChuNhat(new Diem(3, 5), new Diem(6, 0));
        
        // Trong implementation này, chạm cạnh được coi là giao nhau
        assertTrue(hcn1.kiemTraGiaoNhau(hcn2));
    }
    
    @Test
    public void kiemTraMotHinhNamTrongHinhKia() {
        // Test một hình nằm hoàn toàn bên trong hình kia
        HinhChuNhat hcn1 = new HinhChuNhat(new Diem(0, 10), new Diem(10, 0));
        HinhChuNhat hcn2 = new HinhChuNhat(new Diem(2, 8), new Diem(8, 2));
        
        assertTrue(hcn1.kiemTraGiaoNhau(hcn2));
        assertTrue(hcn2.kiemTraGiaoNhau(hcn1));
    }
    
    @Test
    public void kiemTraHaiHinhGiaoNhauMotPhan() {
        // Test hai hình giao nhau một phần
        HinhChuNhat hcn1 = new HinhChuNhat(new Diem(0, 6), new Diem(4, 2));
        HinhChuNhat hcn2 = new HinhChuNhat(new Diem(2, 8), new Diem(6, 4));
        
        assertTrue(hcn1.kiemTraGiaoNhau(hcn2));
    }
    
    @Test
    public void kiemTraCungMotHinhChuNhat() {
        // Test cùng một hình chữ nhật
        HinhChuNhat hcn = new HinhChuNhat(new Diem(0, 5), new Diem(5, 0));
        
        assertTrue(hcn.kiemTraGiaoNhau(hcn));
    }
    
    @Test
    public void kiemTraGiaoNhauVoiToaDoAm() {
        // Test với tọa độ âm
        HinhChuNhat hcn1 = new HinhChuNhat(new Diem(-5, 2), new Diem(-1, -2));
        HinhChuNhat hcn2 = new HinhChuNhat(new Diem(-3, 1), new Diem(1, -3));
        
        assertTrue(hcn1.kiemTraGiaoNhau(hcn2));
    }
    
    @Test
    public void kiemTraHaiHinhGocCheoKhongGiao() {
        // Test hai hình ở góc chéo nhau không giao
        HinhChuNhat hcn1 = new HinhChuNhat(new Diem(0, 5), new Diem(3, 2));
        HinhChuNhat hcn2 = new HinhChuNhat(new Diem(4, 1), new Diem(7, -2));
        
        assertFalse(hcn1.kiemTraGiaoNhau(hcn2));
    }
}
