package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String SERVER = "DESKTOP-RJK03JE\\SQL01";
    private static final String DATABASE = "CustomerDB";
    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "123456";
    
    private static final String URL = "jdbc:sqlserver://" + SERVER + 
                                     ";databaseName=" + DATABASE + 
                                     ";user=" + USERNAME + 
                                     ";password=" + PASSWORD +
                                     ";encrypt=false;trustServerCertificate=true";
    
    private static final String MASTER_URL = "jdbc:sqlserver://" + SERVER + 
                                            ";databaseName=master" + 
                                            ";user=" + USERNAME + 
                                            ";password=" + PASSWORD +
                                            ";encrypt=false;trustServerCertificate=true";
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQL Server JDBC Driver not found", e);
        }
    }
    
    public static Connection getMasterConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(MASTER_URL);
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQL Server JDBC Driver not found", e);
        }
    }
    
    // Method to initialize database and table
    public static void initializeDatabase() {
        // First, create database if not exists
        try (Connection conn = getMasterConnection();
             Statement stmt = conn.createStatement()) {
            
            String createDbSQL = "IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = '" + DATABASE + "') " +
                                "CREATE DATABASE " + DATABASE;
            stmt.execute(createDbSQL);
            System.out.println("Database checked/created successfully");
            
        } catch (SQLException e) {
            System.err.println("Error creating database: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Then create table
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Create table if not exists
            String createTableSQL = "IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Customers') " +
                    "CREATE TABLE Customers (" +
                    "customer_id VARCHAR(10) PRIMARY KEY, " +
                    "full_name NVARCHAR(50) NOT NULL, " +
                    "email VARCHAR(100) NOT NULL UNIQUE, " +
                    "phone_number VARCHAR(12) NOT NULL, " +
                    "address NVARCHAR(255) NOT NULL, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "date_of_birth DATE, " +
                    "gender NVARCHAR(10)" +
                    ")";
            
            stmt.execute(createTableSQL);
            System.out.println("Table initialized successfully");
            
        } catch (SQLException e) {
            System.err.println("Error initializing table: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Test connection
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            return false;
        }
    }
}
