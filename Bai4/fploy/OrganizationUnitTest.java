import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test case cho màn hình Add Organization Unit
 * Kiểm thử các trường hợp quan trọng:
 * - Form có kết nối với cơ sở dữ liệu SQLite
 * - Validation cho trường bắt buộc
 * - Các thao tác CRUD với database
 */
public class OrganizationUnitTest {

    private OrganizationUnit orgUnit;

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
        orgUnit = new OrganizationUnit();
        DatabaseManager.clearAllData();
        System.out.println("\n=== Bắt đầu test case ===");
    }

    @After
    public void tearDown() {
        System.out.println("=== Kết thúc test case ===");
    }

    // ========== Kiểm thử trường Name (bắt buộc) ==========

    @Test
    public void testNameIsNull() {
        System.out.println("TC01: Name = null (Lỗi - Name bắt buộc)");
        orgUnit.setName(null);
        assertFalse("Name không được null", orgUnit.validate());
    }

    @Test
    public void testNameIsEmpty() {
        System.out.println("TC02: Name = \"\" (Lỗi - Name không được rỗng)");
        orgUnit.setName("");
        assertFalse("Name không được rỗng", orgUnit.validate());
    }

    @Test
    public void testNameIsValid() {
        System.out.println("TC03: Name hợp lệ");
        orgUnit.setName("IT Department");
        assertTrue("Name hợp lệ", orgUnit.validate());
    }

    // ========== Kiểm thử chức năng Database ==========

    @Test
    public void testSaveWithValidData() {
        System.out.println("TC04: Save dữ liệu hợp lệ vào SQLite Database");
        orgUnit.setUnitId("OU001");
        orgUnit.setName("Customer Service");
        orgUnit.setDescription("Customer support");
        
        assertTrue("Save thành công", orgUnit.save());
        
        OrganizationUnit loaded = OrganizationUnit.findById("OU001");
        assertNotNull("Dữ liệu đã được lưu", loaded);
        assertEquals("Name đúng", "Customer Service", loaded.getName());
    }

    @Test
    public void testSaveWithInvalidData() {
        System.out.println("TC05: Save với dữ liệu không hợp lệ (Name rỗng)");
        orgUnit.setName("");
        assertFalse("Save thất bại", orgUnit.save());
        assertEquals("Không có dữ liệu trong database", 0, DatabaseManager.countRecords());
    }
    
    @Test
    public void testLoadFromDatabase() {
        System.out.println("TC06: Tải dữ liệu từ SQLite Database");
        orgUnit.setUnitId("OU100");
        orgUnit.setName("IT Department");
        orgUnit.setDescription("Information Technology");
        orgUnit.save();
        
        OrganizationUnit loaded = OrganizationUnit.findById("OU100");
        assertNotNull("Tìm thấy dữ liệu", loaded);
        assertEquals("UnitId đúng", "OU100", loaded.getUnitId());
        assertEquals("Name đúng", "IT Department", loaded.getName());
    }
    
    @Test
    public void testDeleteFromDatabase() {
        System.out.println("TC07: Xóa dữ liệu từ SQLite Database");
        orgUnit.setUnitId("OU200");
        orgUnit.setName("HR Department");
        orgUnit.save();
        
        assertTrue("Xóa thành công", orgUnit.delete());
        assertNull("Dữ liệu đã bị xóa", OrganizationUnit.findById("OU200"));
    }
    
    @Test
    public void testAutoGenerateUnitId() {
        System.out.println("TC08: Tự động tạo Unit Id");
        orgUnit.setName("Finance Department");
        
        assertTrue("Save thành công", orgUnit.save());
        assertNotNull("UnitId được tạo tự động", orgUnit.getUnitId());
        assertTrue("UnitId bắt đầu bằng OU", orgUnit.getUnitId().startsWith("OU"));
    }
    
    @Test
    public void testUpdateExistingRecord() {
        System.out.println("TC09: Cập nhật record đã tồn tại");
        orgUnit.setUnitId("OU300");
        orgUnit.setName("Original Name");
        orgUnit.save();
        
        orgUnit.setName("Updated Name");
        orgUnit.setDescription("Updated Description");
        assertTrue("Update thành công", orgUnit.save());
        
        OrganizationUnit loaded = OrganizationUnit.findById("OU300");
        assertEquals("Name đã được cập nhật", "Updated Name", loaded.getName());
    }

    // ========== Kiểm thử trường hợp biên ==========

    @Test
    public void testNameWithSpecialCharacters() {
        System.out.println("TC10: Name có ký tự đặc biệt");
        orgUnit.setName("IT & Tech Department");
        assertTrue("Name chấp nhận ký tự đặc biệt", orgUnit.validate());
    }
}
