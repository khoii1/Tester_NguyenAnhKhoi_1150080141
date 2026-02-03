import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DatabaseManager - Quản lý kết nối và thao tác với SQLite database
 */
public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:jobtitle.db";
    private static Connection connection;

    /**
     * Khởi tạo database và tạo bảng job_titles nếu chưa có
     */
    public static void initDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS job_titles (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    job_title TEXT NOT NULL,
                    description TEXT,
                    job_specification BLOB,
                    specification_filename TEXT,
                    specification_size INTEGER,
                    note TEXT
                )
                """;
            
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(createTableSQL);
                System.out.println("Database initialized successfully");
            }
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    /**
     * Lấy connection đến database
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
            }
        } catch (SQLException e) {
            System.err.println("Error getting connection: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Đóng kết nối database
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    /**
     * Xóa tất cả dữ liệu trong bảng (dùng cho testing)
     */
    public static void clearAllData() {
        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute("DELETE FROM job_titles");
            System.out.println("All data cleared from database");
        } catch (SQLException e) {
            System.err.println("Error clearing data: " + e.getMessage());
        }
    }

    /**
     * Đếm số record trong bảng
     */
    public static int countRecords() {
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM job_titles")) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error counting records: " + e.getMessage());
        }
        return 0;
    }
}
