import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class OrganizationUnit {
    private String unitId;
    private String name;
    private String description;

    public OrganizationUnit() {
    }

    public OrganizationUnit(String unitId, String name, String description) {
        this.unitId = unitId;
        this.name = name;
        this.description = description;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Validates the organization unit data before saving
     * @return true if valid, false otherwise
     */
    public boolean validate() {
        // Name is required
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Saves the organization unit to SQLite database
     * @return true if saved successfully, false otherwise
     */
    public boolean save() {
        if (!validate()) {
            return false;
        }
        
        // Tự động tạo unitId nếu chưa có
        if (unitId == null || unitId.trim().isEmpty()) {
            unitId = "OU" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
        
        String sql = "INSERT OR REPLACE INTO organization_units (unit_id, name, description) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, unitId);
            pstmt.setString(2, name);
            pstmt.setString(3, description);
            
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Saved to database: " + this.toString());
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saving to database: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Tải organization unit từ database theo unitId
     * @param unitId ID của organization unit cần tải
     * @return OrganizationUnit object hoặc null nếu không tìm thấy
     */
    public static OrganizationUnit findById(String unitId) {
        String sql = "SELECT * FROM organization_units WHERE unit_id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, unitId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new OrganizationUnit(
                    rs.getString("unit_id"),
                    rs.getString("name"),
                    rs.getString("description")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding record: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Xóa organization unit từ database
     * @return true nếu xóa thành công
     */
    public boolean delete() {
        if (unitId == null) {
            return false;
        }
        
        String sql = "DELETE FROM organization_units WHERE unit_id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, unitId);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted from database: " + unitId);
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting from database: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String toString() {
        return "OrganizationUnit{" +
                "unitId='" + unitId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
