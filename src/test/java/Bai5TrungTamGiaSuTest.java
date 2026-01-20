import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Bai5TrungTamGiaSuTest {
    
    private static final double DELTA = 0.00001;
    private TrungTamGiaSu trungTam;
    
    @BeforeEach
    public void setUp() {
        trungTam = new TrungTamGiaSu();
    }
    
    @Test
    public void kiemTraHocVienConstructor() {
        // Test constructor của HocVien
        HocVien hv = new HocVien("HV001", "Nguyen Van A", "Ha Noi", 8.5, 9.0, 8.0);
        
        assertEquals("HV001", hv.getMaSo());
        assertEquals("Nguyen Van A", hv.getHoTen());
        assertEquals("Ha Noi", hv.getQueQuan());
        assertEquals(8.5, hv.getDiemMon1(), DELTA);
        assertEquals(9.0, hv.getDiemMon2(), DELTA);
        assertEquals(8.0, hv.getDiemMon3(), DELTA);
    }
    
    @Test
    public void kiemTraTinhDiemTrungBinh() {
        // Test tính điểm trung bình
        HocVien hv = new HocVien("HV001", "Nguyen Van A", "Ha Noi", 8.0, 9.0, 9.0);
        
        // DTB = (8.0 + 9.0 + 9.0) / 3 = 8.666...
        assertEquals(8.666666, hv.tinhDiemTrungBinh(), 0.0001);
    }
    
    @Test
    public void kiemTraDuDieuKienNhanHocBongHopLe() {
        // Test học viên đủ điều kiện: DTB >= 8.0 và tất cả môn >= 5.0
        HocVien hv1 = new HocVien("HV001", "Nguyen Van A", "Ha Noi", 8.0, 9.0, 9.0);
        assertTrue(hv1.duDieuKienNhanHocBong());
        
        HocVien hv2 = new HocVien("HV002", "Le Thi B", "Da Nang", 8.5, 8.5, 8.5);
        assertTrue(hv2.duDieuKienNhanHocBong());
        
        // Trường hợp có môn 5.0 nhưng DTB >= 8.0
        HocVien hv3 = new HocVien("HV003", "Tran Van C", "HCM", 10.0, 10.0, 5.0);
        assertTrue(hv3.duDieuKienNhanHocBong());
    }
    
    @Test
    public void kiemTraDuDieuKienNhanHocBongDiemTBThap() {
        // Test học viên không đủ điều kiện: DTB < 8.0
        HocVien hv1 = new HocVien("HV001", "Nguyen Van A", "Ha Noi", 7.0, 7.5, 7.8);
        assertFalse(hv1.duDieuKienNhanHocBong());
        
        HocVien hv2 = new HocVien("HV002", "Le Thi B", "Da Nang", 6.0, 7.0, 8.0);
        assertFalse(hv2.duDieuKienNhanHocBong());
    }
    
    @Test
    public void kiemTraDuDieuKienNhanHocBongCoMonDuoi5() {
        // Test học viên không đủ điều kiện: có môn < 5.0
        HocVien hv1 = new HocVien("HV001", "Nguyen Van A", "Ha Noi", 10.0, 10.0, 4.0);
        assertFalse(hv1.duDieuKienNhanHocBong());
        
        HocVien hv2 = new HocVien("HV002", "Le Thi B", "Da Nang", 4.5, 9.0, 9.0);
        assertFalse(hv2.duDieuKienNhanHocBong());
        
        HocVien hv3 = new HocVien("HV003", "Tran Van C", "HCM", 9.0, 3.0, 10.0);
        assertFalse(hv3.duDieuKienNhanHocBong());
    }
    
    @Test
    public void kiemTraDuDieuKienNhanHocBongTruongHopBien() {
        // Test các trường hợp biên
        // DTB đúng 8.0
        HocVien hv1 = new HocVien("HV001", "Test 1", "Ha Noi", 8.0, 8.0, 8.0);
        assertTrue(hv1.duDieuKienNhanHocBong());
        
        // DTB gần 8.0 nhưng nhỏ hơn
        HocVien hv2 = new HocVien("HV002", "Test 2", "Ha Noi", 7.9, 8.0, 8.0);
        assertFalse(hv2.duDieuKienNhanHocBong());
        
        // Có môn đúng 5.0
        HocVien hv3 = new HocVien("HV003", "Test 3", "Ha Noi", 10.0, 9.0, 5.0);
        assertTrue(hv3.duDieuKienNhanHocBong());
    }
    
    @Test
    public void kiemTraThemHocVien() {
        // Test thêm học viên vào danh sách
        assertEquals(0, trungTam.getSoLuongHocVien());
        
        HocVien hv1 = new HocVien("HV001", "Nguyen Van A", "Ha Noi", 8.5, 9.0, 8.0);
        trungTam.themHocVien(hv1);
        
        assertEquals(1, trungTam.getSoLuongHocVien());
        
        HocVien hv2 = new HocVien("HV002", "Le Thi B", "Da Nang", 7.0, 6.0, 8.0);
        trungTam.themHocVien(hv2);
        
        assertEquals(2, trungTam.getSoLuongHocVien());
    }
    
    @Test
    public void kiemTraThemHocVienNull() {
        // Test thêm null không làm thay đổi danh sách
        trungTam.themHocVien(null);
        assertEquals(0, trungTam.getSoLuongHocVien());
    }
    
    @Test
    public void kiemTraTimHocVienTheoMaSo() {
        // Test tìm học viên theo mã số
        HocVien hv1 = new HocVien("HV001", "Nguyen Van A", "Ha Noi", 8.5, 9.0, 8.0);
        HocVien hv2 = new HocVien("HV002", "Le Thi B", "Da Nang", 7.0, 6.0, 8.0);
        
        trungTam.themHocVien(hv1);
        trungTam.themHocVien(hv2);
        
        HocVien found = trungTam.timHocVienTheoMaSo("HV001");
        assertNotNull(found);
        assertEquals("Nguyen Van A", found.getHoTen());
        
        HocVien notFound = trungTam.timHocVienTheoMaSo("HV999");
        assertNull(notFound);
    }
    
    @Test
    public void kiemTraLayDanhSachNhanHocBongRong() {
        // Test danh sách rỗng
        List<HocVien> result = trungTam.layDanhSachNhanHocBong();
        assertNotNull(result);
        assertEquals(0, result.size());
    }
    
    @Test
    public void kiemTraLayDanhSachNhanHocBongKhongCoDuDieuKien() {
        // Test không có học viên nào đủ điều kiện
        trungTam.themHocVien(new HocVien("HV001", "Test 1", "HN", 7.0, 7.0, 7.0));
        trungTam.themHocVien(new HocVien("HV002", "Test 2", "HN", 10.0, 10.0, 4.0));
        trungTam.themHocVien(new HocVien("HV003", "Test 3", "HN", 6.0, 6.5, 7.0));
        
        List<HocVien> result = trungTam.layDanhSachNhanHocBong();
        assertEquals(0, result.size());
    }
    
    @Test
    public void kiemTraLayDanhSachNhanHocBongMotSoDuDieuKien() {
        // Test có một số học viên đủ điều kiện
        HocVien hv1 = new HocVien("HV001", "Nguyen Van A", "Ha Noi", 8.5, 9.0, 8.0);
        HocVien hv2 = new HocVien("HV002", "Le Thi B", "Da Nang", 7.0, 6.0, 8.0);
        HocVien hv3 = new HocVien("HV003", "Tran Van C", "HCM", 9.0, 9.5, 8.5);
        HocVien hv4 = new HocVien("HV004", "Pham Thi D", "Hue", 10.0, 10.0, 4.0);
        
        trungTam.themHocVien(hv1);
        trungTam.themHocVien(hv2);
        trungTam.themHocVien(hv3);
        trungTam.themHocVien(hv4);
        
        List<HocVien> result = trungTam.layDanhSachNhanHocBong();
        
        assertEquals(2, result.size());
        assertTrue(result.contains(hv1));
        assertTrue(result.contains(hv3));
        assertFalse(result.contains(hv2));
        assertFalse(result.contains(hv4));
    }
    
    @Test
    public void kiemTraLayDanhSachNhanHocBongTatCaDuDieuKien() {
        // Test tất cả học viên đều đủ điều kiện
        trungTam.themHocVien(new HocVien("HV001", "Test 1", "HN", 8.5, 9.0, 8.0));
        trungTam.themHocVien(new HocVien("HV002", "Test 2", "HN", 9.0, 9.5, 8.5));
        trungTam.themHocVien(new HocVien("HV003", "Test 3", "HN", 10.0, 10.0, 10.0));
        
        List<HocVien> result = trungTam.layDanhSachNhanHocBong();
        assertEquals(3, result.size());
    }
    
    @Test
    public void kiemTraDemSoHocVienNhanHocBong() {
        // Test đếm số học viên nhận học bổng
        trungTam.themHocVien(new HocVien("HV001", "Test 1", "HN", 8.5, 9.0, 8.0));
        trungTam.themHocVien(new HocVien("HV002", "Test 2", "HN", 7.0, 6.0, 8.0));
        trungTam.themHocVien(new HocVien("HV003", "Test 3", "HN", 9.0, 9.5, 8.5));
        
        assertEquals(2, trungTam.demSoHocVienNhanHocBong());
    }
    
    @Test
    public void kiemTraXoaTatCa() {
        // Test xóa tất cả học viên
        trungTam.themHocVien(new HocVien("HV001", "Test 1", "HN", 8.5, 9.0, 8.0));
        trungTam.themHocVien(new HocVien("HV002", "Test 2", "HN", 7.0, 6.0, 8.0));
        
        assertEquals(2, trungTam.getSoLuongHocVien());
        
        trungTam.xoaTatCa();
        
        assertEquals(0, trungTam.getSoLuongHocVien());
        assertEquals(0, trungTam.layDanhSachNhanHocBong().size());
    }
    
    @Test
    public void kiemTraLayDanhSachHocVien() {
        // Test lấy danh sách học viên
        HocVien hv1 = new HocVien("HV001", "Test 1", "HN", 8.5, 9.0, 8.0);
        HocVien hv2 = new HocVien("HV002", "Test 2", "HN", 7.0, 6.0, 8.0);
        
        trungTam.themHocVien(hv1);
        trungTam.themHocVien(hv2);
        
        List<HocVien> danhSach = trungTam.getDanhSachHocVien();
        
        assertEquals(2, danhSach.size());
        assertTrue(danhSach.contains(hv1));
        assertTrue(danhSach.contains(hv2));
    }
}
