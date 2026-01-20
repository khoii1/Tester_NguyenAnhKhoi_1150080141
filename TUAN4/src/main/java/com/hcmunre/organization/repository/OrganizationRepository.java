package com.hcmunre.organization.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hcmunre.organization.model.Organization;
import com.hcmunre.organization.util.DatabaseConnection;

/**
 * Repository class for Organization data access
 * Handles all database operations for Organization entity
 */
public class OrganizationRepository {

    /**
     * Save a new organization to database
     * @param organization Organization to save
     * @return Saved organization with generated ID
     * @throws SQLException if database operation fails
     */
    public Organization save(Organization organization) throws SQLException {
        String sql = "INSERT INTO ORGANIZATION (OrgName, Address, Phone, Email) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, organization.getOrgName());
            pstmt.setString(2, organization.getAddress());
            pstmt.setString(3, organization.getPhone());
            pstmt.setString(4, organization.getEmail());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating organization failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    organization.setOrgId(generatedKeys.getInt(1));
                    // Get the created date from database
                    organization.setCreatedDate(findById(organization.getOrgId())
                            .map(Organization::getCreatedDate)
                            .orElse(LocalDateTime.now()));
                } else {
                    throw new SQLException("Creating organization failed, no ID obtained.");
                }
            }
            
            return organization;
        }
    }

    /**
     * Find organization by ID
     * @param id Organization ID
     * @return Optional containing the organization if found
     * @throws SQLException if database operation fails
     */
    public Optional<Organization> findById(Integer id) throws SQLException {
        String sql = "SELECT OrgID, OrgName, Address, Phone, Email, CreatedDate FROM ORGANIZATION WHERE OrgID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToOrganization(rs));
                }
            }
        }
        
        return Optional.empty();
    }

    /**
     * Find organization by name (case-insensitive)
     * @param orgName Organization name
     * @return Optional containing the organization if found
     * @throws SQLException if database operation fails
     */
    public Optional<Organization> findByName(String orgName) throws SQLException {
        String sql = "SELECT OrgID, OrgName, Address, Phone, Email, CreatedDate FROM ORGANIZATION WHERE LOWER(OrgName) = LOWER(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, orgName);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToOrganization(rs));
                }
            }
        }
        
        return Optional.empty();
    }

    /**
     * Check if organization name exists (case-insensitive)
     * @param orgName Organization name
     * @return true if name exists
     * @throws SQLException if database operation fails
     */
    public boolean existsByName(String orgName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ORGANIZATION WHERE LOWER(OrgName) = LOWER(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, orgName);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        
        return false;
    }

    /**
     * Find all organizations
     * @return List of all organizations
     * @throws SQLException if database operation fails
     */
    public List<Organization> findAll() throws SQLException {
        String sql = "SELECT OrgID, OrgName, Address, Phone, Email, CreatedDate FROM ORGANIZATION ORDER BY CreatedDate DESC";
        List<Organization> organizations = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                organizations.add(mapResultSetToOrganization(rs));
            }
        }
        
        return organizations;
    }

    /**
     * Update an existing organization
     * @param organization Organization to update
     * @return Updated organization
     * @throws SQLException if database operation fails
     */
    public Organization update(Organization organization) throws SQLException {
        String sql = "UPDATE ORGANIZATION SET OrgName = ?, Address = ?, Phone = ?, Email = ? WHERE OrgID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, organization.getOrgName());
            pstmt.setString(2, organization.getAddress());
            pstmt.setString(3, organization.getPhone());
            pstmt.setString(4, organization.getEmail());
            pstmt.setInt(5, organization.getOrgId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Updating organization failed, no rows affected.");
            }
            
            return organization;
        }
    }

    /**
     * Delete organization by ID
     * @param id Organization ID
     * @return true if deleted successfully
     * @throws SQLException if database operation fails
     */
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM ORGANIZATION WHERE OrgID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
        }
    }

    /**
     * Map ResultSet to Organization object
     * @param rs ResultSet from query
     * @return Organization object
     * @throws SQLException if mapping fails
     */
    private Organization mapResultSetToOrganization(ResultSet rs) throws SQLException {
        Organization org = new Organization();
        org.setOrgId(rs.getInt("OrgID"));
        org.setOrgName(rs.getString("OrgName"));
        org.setAddress(rs.getString("Address"));
        org.setPhone(rs.getString("Phone"));
        org.setEmail(rs.getString("Email"));
        
        Timestamp timestamp = rs.getTimestamp("CreatedDate");
        if (timestamp != null) {
            org.setCreatedDate(timestamp.toLocalDateTime());
        }
        
        return org;
    }
}
