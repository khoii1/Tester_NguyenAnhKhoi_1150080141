import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for PaymentCalculator using:
 * - Equivalence Partitioning (Phân vùng tương đương)
 * - Boundary Value Analysis (Giá trị biên)
 * - Decision Table Testing (Bảng quyết định)
 */
public class PaymentCalculatorTest {
    
    private PaymentCalculator calculator;
    
    @Before
    public void setUp() {
        calculator = new PaymentCalculator();
    }
    
    // ========== CHILD TESTS ==========
    
    @Test
    public void testChild_BoundaryValue_Age0() {
        // Boundary: Lower bound for Child
        assertEquals(50, calculator.calculatePayment(PaymentCalculator.Gender.CHILD, 0));
    }
    
    @Test
    public void testChild_EquivalencePartition_Age10() {
        // Equivalence Partition: Valid child age
        assertEquals(50, calculator.calculatePayment(PaymentCalculator.Gender.CHILD, 10));
    }
    
    @Test
    public void testChild_BoundaryValue_Age17() {
        // Boundary: Upper bound for Child
        assertEquals(50, calculator.calculatePayment(PaymentCalculator.Gender.CHILD, 17));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testChild_InvalidAge_Negative() {
        // Invalid: Negative age
        calculator.calculatePayment(PaymentCalculator.Gender.CHILD, -1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testChild_InvalidAge_18() {
        // Boundary: Age 18 is not valid for Child
        calculator.calculatePayment(PaymentCalculator.Gender.CHILD, 18);
    }
    
    // ========== MALE TESTS ==========
    
    // Male 18-35 (100 euro)
    @Test
    public void testMale_BoundaryValue_Age18() {
        // Boundary: Lower bound for Male 18-35
        assertEquals(100, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 18));
    }
    
    @Test
    public void testMale_EquivalencePartition_Age25() {
        // Equivalence Partition: Valid Male 18-35
        assertEquals(100, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 25));
    }
    
    @Test
    public void testMale_BoundaryValue_Age35() {
        // Boundary: Upper bound for Male 18-35
        assertEquals(100, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 35));
    }
    
    // Male 36-50 (120 euro)
    @Test
    public void testMale_BoundaryValue_Age36() {
        // Boundary: Lower bound for Male 36-50
        assertEquals(120, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 36));
    }
    
    @Test
    public void testMale_EquivalencePartition_Age40() {
        // Equivalence Partition: Valid Male 36-50
        assertEquals(120, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 40));
    }
    
    @Test
    public void testMale_BoundaryValue_Age50() {
        // Boundary: Upper bound for Male 36-50
        assertEquals(120, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 50));
    }
    
    // Male 51-145 (140 euro)
    @Test
    public void testMale_BoundaryValue_Age51() {
        // Boundary: Lower bound for Male 51-145
        assertEquals(140, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 51));
    }
    
    @Test
    public void testMale_EquivalencePartition_Age100() {
        // Equivalence Partition: Valid Male 51-145
        assertEquals(140, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 100));
    }
    
    @Test
    public void testMale_BoundaryValue_Age145() {
        // Boundary: Upper bound for Male 51-145
        assertEquals(140, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 145));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testMale_InvalidAge_17() {
        // Invalid: Age 17 is not valid for Male
        calculator.calculatePayment(PaymentCalculator.Gender.MALE, 17);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testMale_InvalidAge_146() {
        // Invalid: Age exceeds maximum
        calculator.calculatePayment(PaymentCalculator.Gender.MALE, 146);
    }
    
    // ========== FEMALE TESTS ==========
    
    // Female 18-35 (80 euro)
    @Test
    public void testFemale_BoundaryValue_Age18() {
        // Boundary: Lower bound for Female 18-35
        assertEquals(80, calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 18));
    }
    
    @Test
    public void testFemale_EquivalencePartition_Age27() {
        // Equivalence Partition: Valid Female 18-35
        assertEquals(80, calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 27));
    }
    
    @Test
    public void testFemale_BoundaryValue_Age35() {
        // Boundary: Upper bound for Female 18-35
        assertEquals(80, calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 35));
    }
    
    // Female 36-50 (110 euro)
    @Test
    public void testFemale_BoundaryValue_Age36() {
        // Boundary: Lower bound for Female 36-50
        assertEquals(110, calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 36));
    }
    
    @Test
    public void testFemale_EquivalencePartition_Age42() {
        // Equivalence Partition: Valid Female 36-50
        assertEquals(110, calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 42));
    }
    
    @Test
    public void testFemale_BoundaryValue_Age50() {
        // Boundary: Upper bound for Female 36-50
        assertEquals(110, calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 50));
    }
    
    // Female 51-145 (140 euro)
    @Test
    public void testFemale_BoundaryValue_Age51() {
        // Boundary: Lower bound for Female 51-145
        assertEquals(140, calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 51));
    }
    
    @Test
    public void testFemale_EquivalencePartition_Age80() {
        // Equivalence Partition: Valid Female 51-145
        assertEquals(140, calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 80));
    }
    
    @Test
    public void testFemale_BoundaryValue_Age145() {
        // Boundary: Upper bound for Female 51-145
        assertEquals(140, calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 145));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testFemale_InvalidAge_17() {
        // Invalid: Age 17 is not valid for Female
        calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 17);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testFemale_InvalidAge_146() {
        // Invalid: Age exceeds maximum
        calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 146);
    }
    
    // ========== NULL AND INVALID GENDER TESTS ==========
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGender_Null() {
        // Invalid: Null gender
        calculator.calculatePayment((PaymentCalculator.Gender) null, 25);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGender_String() {
        // Invalid: Invalid gender string
        calculator.calculatePayment("INVALID", 25);
    }
    
    // ========== STRING PARAMETER TESTS ==========
    
    @Test
    public void testStringParameter_Male() {
        assertEquals(100, calculator.calculatePayment("MALE", 20));
        assertEquals(100, calculator.calculatePayment("male", 20));
    }
    
    @Test
    public void testStringParameter_Female() {
        assertEquals(80, calculator.calculatePayment("FEMALE", 20));
        assertEquals(80, calculator.calculatePayment("female", 20));
    }
    
    @Test
    public void testStringParameter_Child() {
        assertEquals(50, calculator.calculatePayment("CHILD", 10));
        assertEquals(50, calculator.calculatePayment("child", 10));
    }
}
