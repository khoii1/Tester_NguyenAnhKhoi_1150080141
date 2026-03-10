package dtm;

public class TinhTienNuoc {

    public static double tinhTienNuoc(int soM3, String loaiKhachHang) {
        if (soM3 <= 0) return 0;
        double don_gia;
        if (loaiKhachHang.equals("ho_ngheo")) {
            don_gia = 5000;
        } else if (loaiKhachHang.equals("dan_cu")) {
            if (soM3 <= 10) {
                don_gia = 7500;
            } else if (soM3 <= 20) {
                don_gia = 9900;
            } else {
                don_gia = 11400;
            }
        } else {
            don_gia = 22000;
        }
        return soM3 * don_gia;
    }
}
