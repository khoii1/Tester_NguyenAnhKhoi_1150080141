/**
 * Bài 4: Lớp biểu diễn hình chữ nhật được xác định bởi 2 điểm:
 * điểm trên bên trái và điểm dưới bên phải
 */
public class HinhChuNhat {
    private Diem topLeft;     // Điểm trên bên trái
    private Diem bottomRight; // Điểm dưới bên phải
    
    /**
     * Constructor
     * @param topLeft điểm trên bên trái
     * @param bottomRight điểm dưới bên phải
     */
    public HinhChuNhat(Diem topLeft, Diem bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }
    
    /**
     * Lấy điểm trên bên trái
     * @return điểm trên bên trái
     */
    public Diem getTopLeft() {
        return topLeft;
    }
    
    /**
     * Lấy điểm dưới bên phải
     * @return điểm dưới bên phải
     */
    public Diem getBottomRight() {
        return bottomRight;
    }
    
    /**
     * Tính diện tích hình chữ nhật
     * @return diện tích
     */
    public double tinhDienTich() {
        double width = Math.abs(bottomRight.getX() - topLeft.getX());
        double height = Math.abs(topLeft.getY() - bottomRight.getY());
        return width * height;
    }
    
    /**
     * Kiểm tra hai hình chữ nhật có giao nhau hay không
     * @param other hình chữ nhật khác
     * @return true nếu hai hình giao nhau, false nếu không
     */
    public boolean kiemTraGiaoNhau(HinhChuNhat other) {
        // Tìm tọa độ min/max cho cả 2 hình
        double thisLeft = Math.min(this.topLeft.getX(), this.bottomRight.getX());
        double thisRight = Math.max(this.topLeft.getX(), this.bottomRight.getX());
        double thisTop = Math.max(this.topLeft.getY(), this.bottomRight.getY());
        double thisBottom = Math.min(this.topLeft.getY(), this.bottomRight.getY());
        
        double otherLeft = Math.min(other.topLeft.getX(), other.bottomRight.getX());
        double otherRight = Math.max(other.topLeft.getX(), other.bottomRight.getX());
        double otherTop = Math.max(other.topLeft.getY(), other.bottomRight.getY());
        double otherBottom = Math.min(other.topLeft.getY(), other.bottomRight.getY());
        
        // Kiểm tra điều kiện KHÔNG giao nhau
        // Hình 1 nằm hoàn toàn bên trái hình 2
        if (thisRight < otherLeft) return false;
        
        // Hình 1 nằm hoàn toàn bên phải hình 2
        if (thisLeft > otherRight) return false;
        
        // Hình 1 nằm hoàn toàn phía dưới hình 2
        if (thisTop < otherBottom) return false;
        
        // Hình 1 nằm hoàn toàn phía trên hình 2
        if (thisBottom > otherTop) return false;
        
        // Nếu không thỏa mãn điều kiện nào ở trên thì hai hình giao nhau
        return true;
    }
    
    @Override
    public String toString() {
        return "HinhChuNhat[topLeft=" + topLeft + ", bottomRight=" + bottomRight + "]";
    }
}
