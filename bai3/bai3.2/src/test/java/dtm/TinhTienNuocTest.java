package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TinhTienNuocTest - Branch Coverage cho ham tinhTienNuoc(int soM3, String loaiKhachHang)
 *
 * 5 dieu kien can phu ca True/False:
 *   DC1: soM3 <= 0
 *   DC2: loaiKhachHang.equals("ho_ngheo")
 *   DC3: loaiKhachHang.equals("dan_cu")
 *   DC4: soM3 <= 10
 *   DC5: soM3 <= 20
 *
 * Tong nhanh can phu: 10 nhanh (5 dieu kien x 2)
 */
public class TinhTienNuocTest {

    // TC1: DC1 = True -> return 0
    @Test
    public void testSoM3KhongHopLe() {
        double actual = TinhTienNuoc.tinhTienNuoc(0, "dan_cu");
        Assert.assertEquals(actual, 0.0, 0.001,
            "Sai ket qua tinh tien nuoc cho truong hop soM3 = 0 (khong hop le)");
    }

    // TC2: DC1 = False, DC2 = True -> don_gia = 5000
    @Test
    public void testHoNgheo() {
        double actual = TinhTienNuoc.tinhTienNuoc(5, "ho_ngheo");
        Assert.assertEquals(actual, 25000.0, 0.001,
            "Sai ket qua tinh tien nuoc cho truong hop ho ngheo, soM3 = 5");
    }

    // TC3: DC1 = False, DC2 = False, DC3 = True, DC4 = True -> don_gia = 7500
    @Test
    public void testDanCuBac1() {
        double actual = TinhTienNuoc.tinhTienNuoc(8, "dan_cu");
        Assert.assertEquals(actual, 60000.0, 0.001,
            "Sai ket qua tinh tien nuoc cho truong hop dan cu bac 1, soM3 = 8 (<= 10)");
    }

    // TC4: DC1 = False, DC2 = False, DC3 = True, DC4 = False, DC5 = True -> don_gia = 9900
    @Test
    public void testDanCuBac2() {
        double actual = TinhTienNuoc.tinhTienNuoc(15, "dan_cu");
        Assert.assertEquals(actual, 148500.0, 0.001,
            "Sai ket qua tinh tien nuoc cho truong hop dan cu bac 2, soM3 = 15 (<= 20)");
    }

    // TC5: DC1 = False, DC2 = False, DC3 = True, DC4 = False, DC5 = False -> don_gia = 11400
    @Test
    public void testDanCuBac3() {
        double actual = TinhTienNuoc.tinhTienNuoc(25, "dan_cu");
        Assert.assertEquals(actual, 285000.0, 0.001,
            "Sai ket qua tinh tien nuoc cho truong hop dan cu bac 3, soM3 = 25 (> 20)");
    }

    // TC6: DC1 = False, DC2 = False, DC3 = False -> don_gia = 22000 (kinh doanh)
    @Test
    public void testKinhDoanh() {
        double actual = TinhTienNuoc.tinhTienNuoc(10, "kinh_doanh");
        Assert.assertEquals(actual, 220000.0, 0.001,
            "Sai ket qua tinh tien nuoc cho truong hop kinh doanh, soM3 = 10");
    }
}
