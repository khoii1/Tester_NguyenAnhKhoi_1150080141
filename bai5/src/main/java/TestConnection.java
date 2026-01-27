import dao.DatabaseConnection;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("Testing SQL Server connection...");
        System.out.println("Server: DESKTOP-RJK03JE\\SQL01");
        System.out.println("Database: CustomerDB");
        System.out.println("Username: testuser");
        System.out.println("--------------------------------");
        
        if (DatabaseConnection.testConnection()) {
            System.out.println("Connection successful!");
            DatabaseConnection.initializeDatabase();
        } else {
            System.out.println("Connection failed!");
            System.out.println("\nPlease check:");
            System.out.println("1. SQL Server is running");
            System.out.println("2. SQL Server Authentication is enabled");
            System.out.println("3. User 'testuser' exists with password '123456'");
            System.out.println("4. Database 'CustomerDB' exists or user has CREATE DATABASE permission");
            System.out.println("5. TCP/IP protocol is enabled in SQL Server Configuration Manager");
        }
    }
}
