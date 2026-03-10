package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * XepLoaiTest - Branch Coverage cho ham xepLoai(int diemTB, boolean coThiLai)
 *
 * 5 dieu kien can phu ca True/False:
 *   DC1: diemTB < 0 || diemTB > 10
 *   DC2: diemTB >= 8.5
 *   DC3: diemTB >= 7.0
 *   DC4: diemTB >= 5.5
 *   DC5: coThiLai
 */
public class XepLoaiTest {

    // TC1: DC1 = True  →  "Diem khong hop le"
    @Test
    public void testDiemKhongHopLe() {
        String actual = XepLoai.xepLoai(-1, false);
        Assert.assertEquals(actual, "Diem khong hop le",
            "Sai ket qua xep loai cho truong hop diem am (< 0)");
    }

    // TC2: DC1 = False, DC2 = True  →  "Gioi"
    @Test
    public void testXepLoaiGioi() {
        String actual = XepLoai.xepLoai(9, false);
        Assert.assertEquals(actual, "Gioi",
            "Sai ket qua xep loai cho truong hop diem = 9 (>= 8.5)");
    }

    // TC3: DC1 = False, DC2 = False, DC3 = True  →  "Kha"
    @Test
    public void testXepLoaiKha() {
        String actual = XepLoai.xepLoai(7, false);
        Assert.assertEquals(actual, "Kha",
            "Sai ket qua xep loai cho truong hop diem = 7 (>= 7.0)");
    }

    // TC4: DC1 = False, DC2 = False, DC3 = False, DC4 = True  →  "Trung Binh"
    @Test
    public void testXepLoaiTrungBinh() {
        String actual = XepLoai.xepLoai(6, false);
        Assert.assertEquals(actual, "Trung Binh",
            "Sai ket qua xep loai cho truong hop diem = 6 (>= 5.5)");
    }

    // TC5: DC1 = False, DC2 = False, DC3 = False, DC4 = False, DC5 = True  →  "Thi lai"
    @Test
    public void testThiLai() {
        String actual = XepLoai.xepLoai(4, true);
        Assert.assertEquals(actual, "Thi lai",
            "Sai ket qua xep loai cho truong hop diem = 4, coThiLai = true");
    }

    // TC6: DC1 = False, DC2 = False, DC3 = False, DC4 = False, DC5 = False  →  "Yeu - Hoc lai"
    @Test
    public void testYeuHocLai() {
        String actual = XepLoai.xepLoai(4, false);
        Assert.assertEquals(actual, "Yeu - Hoc lai",
            "Sai ket qua xep loai cho truong hop diem = 4, coThiLai = false");
    }
}
