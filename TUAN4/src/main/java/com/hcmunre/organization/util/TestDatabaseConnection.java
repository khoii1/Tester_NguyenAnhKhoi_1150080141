package com.hcmunre.organization.util;

/**
 * Test class to verify database connection
 * Run this before running the main application
 */
public class TestDatabaseConnection {
    
    public static void main(String[] args) {
        System.out.println("======================================");
        System.out.println("  Database Connection Test");
        System.out.println("======================================");
        System.out.println();
        
        System.out.println("Testing database connection...");
        
        boolean connected = DatabaseConnection.testConnection();
        
        if (connected) {
            System.out.println("✓ SUCCESS: Database connection established!");
            System.out.println();
            System.out.println("Your database is configured correctly.");
            System.out.println("You can now run the application.");
        } else {
            System.out.println("✗ FAILED: Could not connect to database!");
            System.out.println();
            System.out.println("Please check:");
            System.out.println("1. SQL Server is running");
            System.out.println("2. Database 'OrganizationDB' exists");
            System.out.println("3. Credentials in database.properties are correct");
            System.out.println("4. SQL Server accepts TCP/IP connections on port 1433");
            System.exit(1);
        }
        
        System.out.println();
        System.out.println("======================================");
    }
}
