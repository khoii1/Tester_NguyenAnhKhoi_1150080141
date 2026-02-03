import java.sql.*;

public class JobTitle {
    private Integer id;
    private String jobTitle;
    private String description;
    private byte[] jobSpecification;
    private String specificationFilename;
    private Integer specificationSize; // in KB
    private String note;

    public JobTitle() {
    }

    public JobTitle(String jobTitle, String description, byte[] jobSpecification, 
                    String specificationFilename, Integer specificationSize, String note) {
        this.jobTitle = jobTitle;
        this.description = description;
        this.jobSpecification = jobSpecification;
        this.specificationFilename = specificationFilename;
        this.specificationSize = specificationSize;
        this.note = note;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getJobSpecification() {
        return jobSpecification;
    }

    public void setJobSpecification(byte[] jobSpecification) {
        this.jobSpecification = jobSpecification;
    }

    public String getSpecificationFilename() {
        return specificationFilename;
    }

    public void setSpecificationFilename(String specificationFilename) {
        this.specificationFilename = specificationFilename;
    }

    public Integer getSpecificationSize() {
        return specificationSize;
    }

    public void setSpecificationSize(Integer specificationSize) {
        this.specificationSize = specificationSize;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Validates the job title data before saving
     * @return true if valid, false otherwise
     */
    public boolean validate() {
        // Job Title is required
        if (jobTitle == null || jobTitle.trim().isEmpty()) {
            return false;
        }
        
        // Job Title max 100 characters
        if (jobTitle.length() > 100) {
            return false;
        }
        
        // Description max 400 characters
        if (description != null && description.length() > 400) {
            return false;
        }
        
        // Job Specification max 1024 KB
        if (specificationSize != null && specificationSize > 1024) {
            return false;
        }
        
        // Note max 400 characters
        if (note != null && note.length() > 400) {
            return false;
        }
        
        return true;
    }

    /**
     * Saves the job title to SQLite database
     * @return true if saved successfully, false otherwise
     */
    public boolean save() {
        if (!validate()) {
            return false;
        }
        
        String sql = "INSERT INTO job_titles (job_title, description, job_specification, " +
                     "specification_filename, specification_size, note) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, jobTitle);
            pstmt.setString(2, description);
            pstmt.setBytes(3, jobSpecification);
            pstmt.setString(4, specificationFilename);
            pstmt.setObject(5, specificationSize);
            pstmt.setString(6, note);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.id = generatedKeys.getInt(1);
                    }
                }
                System.out.println("Saved to database: " + this.toString());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error saving to database: " + e.getMessage());
            return false;
        }
        return false;
    }
    
    /**
     * Tải job title từ database theo id
     * @param id ID của job title cần tải
     * @return JobTitle object hoặc null nếu không tìm thấy
     */
    public static JobTitle findById(Integer id) {
        String sql = "SELECT * FROM job_titles WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                JobTitle jobTitle = new JobTitle();
                jobTitle.setId(rs.getInt("id"));
                jobTitle.setJobTitle(rs.getString("job_title"));
                jobTitle.setDescription(rs.getString("description"));
                jobTitle.setJobSpecification(rs.getBytes("job_specification"));
                jobTitle.setSpecificationFilename(rs.getString("specification_filename"));
                jobTitle.setSpecificationSize((Integer) rs.getObject("specification_size"));
                jobTitle.setNote(rs.getString("note"));
                return jobTitle;
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding record: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Xóa job title từ database
     * @return true nếu xóa thành công
     */
    public boolean delete() {
        if (id == null) {
            return false;
        }
        
        String sql = "DELETE FROM job_titles WHERE id = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted from database: ID " + id);
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting from database: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String toString() {
        return "JobTitle{" +
                "id=" + id +
                ", jobTitle='" + jobTitle + '\'' +
                ", description='" + description + '\'' +
                ", specificationFilename='" + specificationFilename + '\'' +
                ", specificationSize=" + specificationSize +
                ", note='" + note + '\'' +
                '}';
    }
}
