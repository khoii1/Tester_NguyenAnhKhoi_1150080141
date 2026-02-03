import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test case cho User Management
 * Kiểm thử các chức năng CRUD và validation
 */
public class UserTest {

    private User user;

    @BeforeClass
    public static void setUpClass() {
        System.out.println("\n========== KHỞI TẠO DATABASE ==========");
        DatabaseManager.initDatabase();
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("\n========== ĐÓNG KẾT NỐI DATABASE ==========");
        DatabaseManager.closeConnection();
    }

    @Before
    public void setUp() {
        user = new User();
        DatabaseManager.clearAllData();
        System.out.println("\n=== Bắt đầu test case ===");
    }

    @After
    public void tearDown() {
        System.out.println("=== Kết thúc test case ===");
    }

    // ========== Validation Tests ==========

    @Test
    public void testUsername_Null() {
        System.out.println("TC01: Username null (Lỗi - Bắt buộc)");
        user.setUsername(null);
        assertFalse("Username không được null", user.validate());
    }

    @Test
    public void testUsername_Empty() {
        System.out.println("TC02: Username rỗng (Lỗi - Bắt buộc)");
        user.setUsername("");
        assertFalse("Username không được rỗng", user.validate());
    }

    @Test
    public void testUsername_Valid() {
        System.out.println("TC03: Username hợp lệ");
        user.setUsername("john_doe");
        assertTrue("Username hợp lệ", user.validate());
    }

    @Test
    public void testEmail_ValidFormat() {
        System.out.println("TC04: Email đúng định dạng");
        user.setUsername("admin");
        user.setEmail("admin@example.com");
        assertTrue("Email hợp lệ", user.validate());
    }

    @Test
    public void testEmail_InvalidFormat() {
        System.out.println("TC05: Email sai định dạng (Lỗi)");
        user.setUsername("admin");
        user.setEmail("invalid-email");
        assertFalse("Email không hợp lệ", user.validate());
    }

    @Test
    public void testEmail_Null() {
        System.out.println("TC06: Email null (Hợp lệ - Không bắt buộc)");
        user.setUsername("user123");
        user.setEmail(null);
        assertTrue("Email có thể null", user.validate());
    }

    // ========== Create Tests ==========

    @Test
    public void testCreate_ValidUser() {
        System.out.println("TC07: Tạo user với dữ liệu hợp lệ");
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setUrl("https://example.com");
        
        assertTrue("Create thành công", user.create());
        assertNotNull("ID được tạo", user.getId());
        
        User found = User.findById(user.getId());
        assertNotNull("Tìm thấy user trong database", found);
        assertEquals("Username đúng", "testuser", found.getUsername());
    }

    @Test
    public void testCreate_InvalidUser() {
        System.out.println("TC08: Tạo user với dữ liệu không hợp lệ");
        user.setUsername(""); // Empty username
        assertFalse("Create thất bại", user.create());
    }

    @Test
    public void testCreate_DuplicateUsername() {
        System.out.println("TC09: Tạo user với username trùng (Lỗi)");
        User user1 = new User("duplicate", "user1@test.com", "pass1", null);
        user1.create();
        
        User user2 = new User("duplicate", "user2@test.com", "pass2", null);
        assertFalse("Không thể tạo username trùng", user2.create());
    }

    // ========== Update Tests ==========

    @Test
    public void testUpdate_ExistingUser() {
        System.out.println("TC10: Cập nhật user đã tồn tại");
        user.setUsername("oldname");
        user.setEmail("old@test.com");
        user.create();
        
        user.setUsername("newname");
        user.setEmail("new@test.com");
        assertTrue("Update thành công", user.update());
        
        User updated = User.findById(user.getId());
        assertEquals("Username đã được cập nhật", "newname", updated.getUsername());
        assertEquals("Email đã được cập nhật", "new@test.com", updated.getEmail());
    }

    @Test
    public void testUpdate_WithoutId() {
        System.out.println("TC11: Cập nhật user không có ID (Lỗi)");
        user.setUsername("noname");
        user.setId(null);
        assertFalse("Update thất bại khi không có ID", user.update());
    }

    // ========== Delete Tests ==========

    @Test
    public void testDelete_ExistingUser() {
        System.out.println("TC12: Xóa user đã tồn tại");
        user.setUsername("todelete");
        user.create();
        
        Integer id = user.getId();
        assertTrue("Delete thành công", user.delete());
        assertNull("User đã bị xóa", User.findById(id));
    }

    @Test
    public void testDelete_WithoutId() {
        System.out.println("TC13: Xóa user không có ID (Lỗi)");
        user.setId(null);
        assertFalse("Delete thất bại khi không có ID", user.delete());
    }

    // ========== Find Tests ==========

    @Test
    public void testFindByUsername() {
        System.out.println("TC14: Tìm user theo username");
        User created = new User("findme", "find@test.com", "pass", "url");
        created.create();
        
        User found = User.findByUsername("findme");
        assertNotNull("Tìm thấy user", found);
        assertEquals("Username đúng", "findme", found.getUsername());
    }

    @Test
    public void testFindAll() {
        System.out.println("TC15: Lấy tất cả users");
        new User("user1", "u1@test.com", "p1", null).create();
        new User("user2", "u2@test.com", "p2", null).create();
        new User("user3", "u3@test.com", "p3", null).create();
        
        assertEquals("Có 3 users", 3, User.findAll().size());
    }
}
