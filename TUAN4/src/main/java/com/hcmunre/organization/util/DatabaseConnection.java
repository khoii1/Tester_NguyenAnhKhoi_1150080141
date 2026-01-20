package com.hcmunre.organization.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database connection utility class
 * Manages SQL Server database connections
 */
public class DatabaseConnection {
    private static String url;
    private static String username;
    private static String password;
    
    static {
        loadDatabaseConfig();
    }

    /**
     * Load database configuration from properties file
     */
    private static void loadDatabaseConfig() {
        Properties props = new Properties();
        try (InputStream input = DatabaseConnection.class
                .getClassLoader()
                .getResourceAsStream("database.properties")) {
            if (input == null) {
                // Default configuration
                url = "jdbc:sqlserver://localhost:1433;databaseName=OrganizationDB;encrypt=true;trustServerCertificate=true";
                username = "sa";
                password = "YourStrong@Password";
                System.out.println("Using default database configuration");
                return;
            }
            props.load(input);
            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }

    /**
     * Get database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQL Server JDBC Driver not found", e);
        }
    }

    /**
     * Close database connection
     * @param connection Connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Test database connection
     * @return true if connection is successful
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }
}
