package dao;

import model.Customer;
import java.sql.*;
import java.time.LocalDate;

public class CustomerDAO {
    
    public boolean isCustomerIdExists(String customerId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Customers WHERE customer_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        
        return false;
    }
    
    public boolean isEmailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Customers WHERE email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        
        return false;
    }
    
    public boolean insertCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customers (customer_id, full_name, email, phone_number, " +
                    "address, password, date_of_birth, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customer.getCustomerId());
            pstmt.setString(2, customer.getFullName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPhoneNumber());
            pstmt.setString(5, customer.getAddress());
            pstmt.setString(6, customer.getPassword());
            
            if (customer.getDateOfBirth() != null) {
                pstmt.setDate(7, Date.valueOf(customer.getDateOfBirth()));
            } else {
                pstmt.setNull(7, Types.DATE);
            }
            
            pstmt.setString(8, customer.getGender());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    public Customer getCustomerById(String customerId) throws SQLException {
        String sql = "SELECT * FROM Customers WHERE customer_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getString("customer_id"));
                customer.setFullName(rs.getString("full_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setAddress(rs.getString("address"));
                customer.setPassword(rs.getString("password"));
                
                Date dob = rs.getDate("date_of_birth");
                if (dob != null) {
                    customer.setDateOfBirth(dob.toLocalDate());
                }
                
                customer.setGender(rs.getString("gender"));
                return customer;
            }
        }
        
        return null;
    }
}
