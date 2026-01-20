/**
 * Lớp biểu diễn thông tin của một học viên
 */
public class HocVien {
    private String maSo;
    private String hoTen;
    private String queQuan;
    private double diemMon1;
    private double diemMon2;
    private double diemMon3;
    
    /**
     * Constructor
     * @param maSo mã số học viên
     * @param hoTen họ tên học viên
     * @param queQuan quê quán
     * @param diemMon1 điểm môn học chính thứ 1
     * @param diemMon2 điểm môn học chính thứ 2
     * @param diemMon3 điểm môn học chính thứ 3
     */
    public HocVien(String maSo, String hoTen, String queQuan, 
                   double diemMon1, double diemMon2, double diemMon3) {
        this.maSo = maSo;
        this.hoTen = hoTen;
        this.queQuan = queQuan;
        this.diemMon1 = diemMon1;
        this.diemMon2 = diemMon2;
        this.diemMon3 = diemMon3;
    }
    
    // Getters
    public String getMaSo() {
        return maSo;
    }
    
    public String getHoTen() {
        return hoTen;
    }
    
    public String getQueQuan() {
        return queQuan;
    }
    
    public double getDiemMon1() {
        return diemMon1;
    }
    
    public double getDiemMon2() {
        return diemMon2;
    }
    
    public double getDiemMon3() {
        return diemMon3;
    }
    
    /**
     * Tính điểm trung bình ba môn học chính
     * @return điểm trung bình
     */
    public double tinhDiemTrungBinh() {
        return (diemMon1 + diemMon2 + diemMon3) / 3.0;
    }
    
    /**
     * Kiểm tra học viên có đủ điều kiện nhận học bổng hay không
     * Điều kiện: Điểm TB >= 8.0 và không có môn nào < 5.0
     * @return true nếu đủ điều kiện, false nếu không
     */
    public boolean duDieuKienNhanHocBong() {
        // Kiểm tra điểm trung bình >= 8.0
        if (tinhDiemTrungBinh() < 8.0) {
            return false;
        }
        
        // Kiểm tra không có môn nào < 5.0
        if (diemMon1 < 5.0 || diemMon2 < 5.0 || diemMon3 < 5.0) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public String toString() {
        return String.format("HocVien[maSo=%s, hoTen=%s, queQuan=%s, diem=(%.1f, %.1f, %.1f), DTB=%.2f]",
                maSo, hoTen, queQuan, diemMon1, diemMon2, diemMon3, tinhDiemTrungBinh());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HocVien other = (HocVien) obj;
        return maSo != null && maSo.equals(other.maSo);
    }
}
