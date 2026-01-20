/**
 * Lớp biểu diễn một điểm trong không gian 2 chiều
 */
public class Diem {
    private double x; // Hoành độ
    private double y; // Tung độ
    
    /**
     * Constructor
     * @param x hoành độ
     * @param y tung độ
     */
    public Diem(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Lấy hoành độ
     * @return hoành độ x
     */
    public double getX() {
        return x;
    }
    
    /**
     * Lấy tung độ
     * @return tung độ y
     */
    public double getY() {
        return y;
    }
    
    /**
     * Thiết lập hoành độ
     * @param x hoành độ mới
     */
    public void setX(double x) {
        this.x = x;
    }
    
    /**
     * Thiết lập tung độ
     * @param y tung độ mới
     */
    public void setY(double y) {
        this.y = y;
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Diem other = (Diem) obj;
        return Double.compare(other.x, x) == 0 && Double.compare(other.y, y) == 0;
    }
}
