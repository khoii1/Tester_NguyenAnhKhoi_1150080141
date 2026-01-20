import java.util.ArrayList;
import java.util.List;

/**
 * Bài 5: Trung tâm gia sư quản lý danh sách học viên
 */
public class TrungTamGiaSu {
    private List<HocVien> danhSachHocVien;
    
    /**
     * Constructor
     */
    public TrungTamGiaSu() {
        this.danhSachHocVien = new ArrayList<>();
    }
    
    /**
     * Thêm học viên vào danh sách
     * @param hocVien học viên cần thêm
     */
    public void themHocVien(HocVien hocVien) {
        if (hocVien != null) {
            danhSachHocVien.add(hocVien);
        }
    }
    
    /**
     * Lấy danh sách tất cả học viên
     * @return danh sách học viên
     */
    public List<HocVien> getDanhSachHocVien() {
        return new ArrayList<>(danhSachHocVien);
    }
    
    /**
     * Tìm học viên theo mã số
     * @param maSo mã số cần tìm
     * @return học viên nếu tìm thấy, null nếu không
     */
    public HocVien timHocVienTheoMaSo(String maSo) {
        for (HocVien hv : danhSachHocVien) {
            if (hv.getMaSo().equals(maSo)) {
                return hv;
            }
        }
        return null;
    }
    
    /**
     * Lấy danh sách học viên có thể nhận học bổng
     * Điều kiện: Điểm TB >= 8.0 và không có môn nào < 5.0
     * @return danh sách học viên đủ điều kiện
     */
    public List<HocVien> layDanhSachNhanHocBong() {
        List<HocVien> result = new ArrayList<>();
        
        for (HocVien hv : danhSachHocVien) {
            if (hv.duDieuKienNhanHocBong()) {
                result.add(hv);
            }
        }
        
        return result;
    }
    
    /**
     * Đếm số lượng học viên có thể nhận học bổng
     * @return số lượng học viên đủ điều kiện
     */
    public int demSoHocVienNhanHocBong() {
        return layDanhSachNhanHocBong().size();
    }
    
    /**
     * Xóa tất cả học viên
     */
    public void xoaTatCa() {
        danhSachHocVien.clear();
    }
    
    /**
     * Lấy số lượng học viên trong danh sách
     * @return số lượng học viên
     */
    public int getSoLuongHocVien() {
        return danhSachHocVien.size();
    }
}
