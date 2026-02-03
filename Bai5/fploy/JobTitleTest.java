import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test case cho màn hình Add Job Title
 * Bao phủ boundary value analysis theo bảng yêu cầu
 */
public class JobTitleTest {

    private JobTitle jobTitle;

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
        jobTitle = new JobTitle();
        DatabaseManager.clearAllData();
        System.out.println("\n=== Bắt đầu test case ===");
    }

    @After
    public void tearDown() {
        System.out.println("=== Kết thúc test case ===");
    }

    // ========== Partition 1: Job Title (Required) ==========

    @Test
    public void testJobTitle_Empty() {
        System.out.println("TC01: Job Title rỗng (Lỗi)");
        jobTitle.setJobTitle("");
        assertFalse("Job Title không được rỗng", jobTitle.validate());
    }

    @Test
    public void testJobTitle_Valid() {
        System.out.println("TC02: Job Title hợp lệ (1-100 chars)");
        jobTitle.setJobTitle("Software Engineer");
        assertTrue("Job Title hợp lệ", jobTitle.validate());
    }

    @Test
    public void testJobTitle_Exactly100Chars() {
        System.out.println("TC03: Job Title đúng 100 chars (Biên trên hợp lệ)");
        String title = "A".repeat(100);
        jobTitle.setJobTitle(title);
        assertTrue("Job Title 100 chars hợp lệ", jobTitle.validate());
    }

    @Test
    public void testJobTitle_Over100Chars() {
        System.out.println("TC04: Job Title > 100 chars (Lỗi)");
        String title = "A".repeat(101);
        jobTitle.setJobTitle(title);
        assertFalse("Job Title vượt quá 100 chars", jobTitle.validate());
    }

    // ========== Partition 2: Description (Optional) ==========

    @Test
    public void testDescription_Empty() {
        System.out.println("TC05: Description rỗng (Hợp lệ)");
        jobTitle.setJobTitle("Manager");
        jobTitle.setDescription("");
        assertTrue("Description có thể rỗng", jobTitle.validate());
    }

    @Test
    public void testDescription_Null() {
        System.out.println("TC06: Description null (Hợp lệ)");
        jobTitle.setJobTitle("Manager");
        jobTitle.setDescription(null);
        assertTrue("Description có thể null", jobTitle.validate());
    }

    @Test
    public void testDescription_Valid() {
        System.out.println("TC07: Description hợp lệ (1-400 chars)");
        jobTitle.setJobTitle("Developer");
        jobTitle.setDescription("Responsible for software development");
        assertTrue("Description hợp lệ", jobTitle.validate());
    }

    @Test
    public void testDescription_Exactly400Chars() {
        System.out.println("TC08: Description đúng 400 chars (Biên trên hợp lệ)");
        jobTitle.setJobTitle("Analyst");
        String desc = "A".repeat(400);
        jobTitle.setDescription(desc);
        assertTrue("Description 400 chars hợp lệ", jobTitle.validate());
    }

    @Test
    public void testDescription_Over400Chars() {
        System.out.println("TC09: Description > 400 chars (Lỗi)");
        jobTitle.setJobTitle("Designer");
        String desc = "A".repeat(401);
        jobTitle.setDescription(desc);
        assertFalse("Description vượt quá 400 chars", jobTitle.validate());
    }

    // ========== Partition 3: Job Specification File ==========

    @Test
    public void testJobSpec_NoFile() {
        System.out.println("TC10: Không có file specification (Hợp lệ)");
        jobTitle.setJobTitle("HR Manager");
        jobTitle.setJobSpecification(null);
        jobTitle.setSpecificationSize(null);
        assertTrue("Không bắt buộc file", jobTitle.validate());
    }

    @Test
    public void testJobSpec_ValidFile() {
        System.out.println("TC11: File hợp lệ (< 1024 KB)");
        jobTitle.setJobTitle("Project Manager");
        jobTitle.setJobSpecification(new byte[1000]);
        jobTitle.setSpecificationFilename("spec.pdf");
        jobTitle.setSpecificationSize(500); // 500 KB
        assertTrue("File 500KB hợp lệ", jobTitle.validate());
    }

    @Test
    public void testJobSpec_Exactly1024KB() {
        System.out.println("TC12: File đúng 1024 KB (Biên trên hợp lệ)");
        jobTitle.setJobTitle("Team Lead");
        jobTitle.setSpecificationSize(1024);
        assertTrue("File 1024KB hợp lệ", jobTitle.validate());
    }

    @Test
    public void testJobSpec_Over1024KB() {
        System.out.println("TC13: File > 1024 KB (Lỗi)");
        jobTitle.setJobTitle("Director");
        jobTitle.setSpecificationSize(1025);
        assertFalse("File vượt quá 1024KB", jobTitle.validate());
    }

    // ========== Partition 4: Note (Optional) ==========

    @Test
    public void testNote_Empty() {
        System.out.println("TC14: Note rỗng (Hợp lệ)");
        jobTitle.setJobTitle("Consultant");
        jobTitle.setNote("");
        assertTrue("Note có thể rỗng", jobTitle.validate());
    }

    @Test
    public void testNote_Valid() {
        System.out.println("TC15: Note hợp lệ (1-400 chars)");
        jobTitle.setJobTitle("Engineer");
        jobTitle.setNote("Important position");
        assertTrue("Note hợp lệ", jobTitle.validate());
    }

    @Test
    public void testNote_Exactly400Chars() {
        System.out.println("TC16: Note đúng 400 chars (Biên trên hợp lệ)");
        jobTitle.setJobTitle("Architect");
        String note = "A".repeat(400);
        jobTitle.setNote(note);
        assertTrue("Note 400 chars hợp lệ", jobTitle.validate());
    }

    @Test
    public void testNote_Over400Chars() {
        System.out.println("TC17: Note > 400 chars (Lỗi)");
        jobTitle.setJobTitle("Specialist");
        String note = "A".repeat(401);
        jobTitle.setNote(note);
        assertFalse("Note vượt quá 400 chars", jobTitle.validate());
    }

    // ========== Database Operations ==========

    @Test
    public void testSave_ValidData() {
        System.out.println("TC18: Lưu dữ liệu hợp lệ vào database");
        jobTitle.setJobTitle("QA Engineer");
        jobTitle.setDescription("Quality Assurance");
        jobTitle.setNote("Urgent hiring");
        
        assertTrue("Save thành công", jobTitle.save());
        assertNotNull("ID được tạo", jobTitle.getId());
        
        JobTitle loaded = JobTitle.findById(jobTitle.getId());
        assertNotNull("Tìm thấy dữ liệu", loaded);
        assertEquals("Job Title đúng", "QA Engineer", loaded.getJobTitle());
    }

    @Test
    public void testSave_InvalidData() {
        System.out.println("TC19: Lưu dữ liệu không hợp lệ");
        jobTitle.setJobTitle(""); // Empty job title
        assertFalse("Save thất bại", jobTitle.save());
        assertEquals("Không có dữ liệu trong database", 0, DatabaseManager.countRecords());
    }

    @Test
    public void testDelete_FromDatabase() {
        System.out.println("TC20: Xóa dữ liệu từ database");
        jobTitle.setJobTitle("Temporary Position");
        jobTitle.save();
        
        Integer id = jobTitle.getId();
        assertTrue("Xóa thành công", jobTitle.delete());
        assertNull("Dữ liệu đã bị xóa", JobTitle.findById(id));
    }
}
