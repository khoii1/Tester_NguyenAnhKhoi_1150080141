package dtm;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TinhPhiShipTest - Basis Path Testing cho ham tinhPhiShip
 *
 * Cyclomatic Complexity (CC) = 8  =>  8 basis paths
 *
 * CC = 7 predicates (D1..D7) + 1 = 8
 * Verified: CC = E - N + 2P = 21 - 15 + 2*1 = 8
 *
 * Basis Paths:
 *   BP1: D1=T                            -> throw exception
 *   BP2: D1=F, D2=T, D3=T, D7=T         -> noi_thanh nang, member
 *   BP3: D1=F, D2=T, D3=F, D7=F         -> noi_thanh nhe, khong member
 *   BP4: D1=F, D2=F, D4=T, D5=T, D7=F  -> ngoai_thanh nang, khong member
 *   BP5: D1=F, D2=F, D4=T, D5=F, D7=T  -> ngoai_thanh nhe, member
 *   BP6: D1=F, D2=F, D4=F, D6=T, D7=F  -> tinh khac nang, khong member
 *   BP7: D1=F, D2=F, D4=F, D6=F, D7=F  -> tinh khac nhe, khong member
 *   BP8: D1=F, D2=F, D4=F, D6=F, D7=T  -> tinh khac nhe, member
 */
public class TinhPhiShipTest {

    // BP1: D1=T -> throw IllegalArgumentException
    @Test(expectedExceptions = IllegalArgumentException.class,
          description = "BP1: trongLuong <= 0 -> throw exception")
    public void testTrongLuongKhongHopLe() {
        TinhPhiShip.tinhPhiShip(-1, "noi_thanh", false);
    }

    // BP2: D1=F, D2=T, D3=T, D7=T
    // phi = 15000 + (8-5)*2000 = 21000; * 0.9 = 18900
    @Test(description = "BP2: noi_thanh, trongLuong=8 (>5), laMember=true -> 18900")
    public void testNoiThanhNangMember() {
        double actual = TinhPhiShip.tinhPhiShip(8, "noi_thanh", true);
        Assert.assertEquals(actual, 18900.0, 0.01,
            "Sai ket qua phi ship cho truong hop noi_thanh, trongLuong=8, laMember=true");
    }

    // BP3: D1=F, D2=T, D3=F, D7=F
    // phi = 15000 (trongLuong=3 <= 5, D3=F); no discount
    @Test(description = "BP3: noi_thanh, trongLuong=3 (<=5), laMember=false -> 15000")
    public void testNoiThanhNhe() {
        double actual = TinhPhiShip.tinhPhiShip(3, "noi_thanh", false);
        Assert.assertEquals(actual, 15000.0, 0.01,
            "Sai ket qua phi ship cho truong hop noi_thanh, trongLuong=3, laMember=false");
    }

    // BP4: D1=F, D2=F, D4=T, D5=T, D7=F
    // phi = 25000 + (5-3)*3000 = 31000; no discount
    @Test(description = "BP4: ngoai_thanh, trongLuong=5 (>3), laMember=false -> 31000")
    public void testNgoaiThanhNang() {
        double actual = TinhPhiShip.tinhPhiShip(5, "ngoai_thanh", false);
        Assert.assertEquals(actual, 31000.0, 0.01,
            "Sai ket qua phi ship cho truong hop ngoai_thanh, trongLuong=5, laMember=false");
    }

    // BP5: D1=F, D2=F, D4=T, D5=F, D7=T
    // phi = 25000 (trongLuong=2 <= 3, D5=F); * 0.9 = 22500
    @Test(description = "BP5: ngoai_thanh, trongLuong=2 (<=3), laMember=true -> 22500")
    public void testNgoaiThanhNheMember() {
        double actual = TinhPhiShip.tinhPhiShip(2, "ngoai_thanh", true);
        Assert.assertEquals(actual, 22500.0, 0.01,
            "Sai ket qua phi ship cho truong hop ngoai_thanh, trongLuong=2, laMember=true");
    }

    // BP6: D1=F, D2=F, D4=F, D6=T, D7=F
    // phi = 50000 + (5-2)*5000 = 65000; no discount
    @Test(description = "BP6: tinh_khac, trongLuong=5 (>2), laMember=false -> 65000")
    public void testTinhKhacNang() {
        double actual = TinhPhiShip.tinhPhiShip(5, "tinh_khac", false);
        Assert.assertEquals(actual, 65000.0, 0.01,
            "Sai ket qua phi ship cho truong hop tinh_khac, trongLuong=5, laMember=false");
    }

    // BP7: D1=F, D2=F, D4=F, D6=F, D7=F
    // phi = 50000 (trongLuong=1 <= 2, D6=F); no discount
    @Test(description = "BP7: tinh_khac, trongLuong=1 (<=2), laMember=false -> 50000")
    public void testTinhKhacNhe() {
        double actual = TinhPhiShip.tinhPhiShip(1, "tinh_khac", false);
        Assert.assertEquals(actual, 50000.0, 0.01,
            "Sai ket qua phi ship cho truong hop tinh_khac, trongLuong=1, laMember=false");
    }

    // BP8: D1=F, D2=F, D4=F, D6=F, D7=T
    // phi = 50000 (trongLuong=1 <= 2, D6=F); * 0.9 = 45000
    @Test(description = "BP8: tinh_khac, trongLuong=1 (<=2), laMember=true -> 45000")
    public void testTinhKhacNheMember() {
        double actual = TinhPhiShip.tinhPhiShip(1, "tinh_khac", true);
        Assert.assertEquals(actual, 45000.0, 0.01,
            "Sai ket qua phi ship cho truong hop tinh_khac, trongLuong=1, laMember=true");
    }
}
