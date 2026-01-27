import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Payment Calculator Test Cases
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
        // Lower bound
        assertEquals(50, calculator.calculatePayment(PaymentCalculator.Gender.CHILD, 0));
    }
    
    @Test
    public void testChild_EquivalencePartition_Age10() {
        // Valid child age
        assertEquals(50, calculator.calculatePayment(PaymentCalculator.Gender.CHILD, 10));
    }
    
    @Test
    public void testChild_BoundaryValue_Age17() {
        // Upper bound
        assertEquals(50, calculator.calculatePayment(PaymentCalculator.Gender.CHILD, 17));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testChild_InvalidAge_Negative() {
        // Negative age
        calculator.calculatePayment(PaymentCalculator.Gender.CHILD, -1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testChild_InvalidAge_18() {
        // Invalid for Child
        calculator.calculatePayment(PaymentCalculator.Gender.CHILD, 18);
    }
    
    // ========== MALE TESTS ==========
    
    // Male 18-35 (100 euro)
    @Test
    public void testMale_BoundaryValue_Age18() {
        // Lower bound 18-35
        assertEquals(100, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 18));
    }
    
    @Test
    public void testMale_EquivalencePartition_Age25() {
        // Valid 18-35
        assertEquals(100, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 25));
    }
    
    @Test
    public void testMale_BoundaryValue_Age35() {
        // Upper bound 18-35
        assertEquals(100, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 35));
    }
    
    // Male 36-50 (120 euro)
    @Test
    public void testMale_BoundaryValue_Age36() {
        // Lower bound 36-50
        assertEquals(120, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 36));
    }
    
    @Test
    public void testMale_EquivalencePartition_Age40() {
        // Valid 36-50
        assertEquals(120, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 40));
    }
    
    @Test
    public void testMale_BoundaryValue_Age50() {
        // Upper bound 36-50
        assertEquals(120, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 50));
    }
    
    // Male 51-145 (140 euro)
    @Test
    public void testMale_BoundaryValue_Age51() {
        // Lower bound 51-145
        assertEquals(140, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 51));
    }
    
    @Test
    public void testMale_EquivalencePartition_Age100() {
        // Valid 51-145
        assertEquals(140, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 100));
    }
    
    @Test
    public void testMale_BoundaryValue_Age145() {
        // Upper bound 51-145
        assertEquals(140, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 145));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testMale_InvalidAge_17() {
        // Invalid age
        calculator.calculatePayment(PaymentCalculator.Gender.MALE, 17);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testMale_InvalidAge_146() {
        // Exceeds maximum
        calculator.calculatePayment(PaymentCalculator.Gender.MALE, 146);
    }
    
    // ========== FEMALE TESTS ==========
    
    // Female 18-35 (80 euro)
    @Test
    public void testFemale_BoundaryValue_Age18() {
        // Lower bound 18-35
        assertEquals(80, calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 18));
    }
    
    @Test
    public void testFemale_EquivalencePartition_Age27() {
        // Valid 18-35
        assertEquals(80, calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 27));
    }
    
    @Test
    public void testFemale_BoundaryValue_Age35() {
        // Upper bound 18-35
        assertEquals(80, calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 35));
    }
    
    // Female 36-50 (110 euro)
    @Test
    public void testFemale_BoundaryValue_Age36() {
        // Lower bound 36-50
        assertEquals(110, calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 36));
    }
    
    @Test
    public void testFemale_EquivalencePartition_Age42() {
        // Valid 36-50
        assertEquals(110, calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 42));
    }
    
    @Test
    public void testFemale_BoundaryValue_Age50() {
        // Upper bound 36-50
        assertEquals(110, calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 50));
    }
    
    // Female 51-145 (140 euro)
    @Test
    public void testFemale_BoundaryValue_Age51() {
        // Lower bound 51-145
        assertEquals(140, calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 51));
    }
    
    @Test
    public void testFemale_EquivalencePartition_Age80() {
        // Valid 51-145
        assertEquals(140, calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 80));
    }
    
    @Test
    public void testFemale_BoundaryValue_Age145() {
        // Upper bound 51-145
        assertEquals(140, calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 145));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testFemale_InvalidAge_17() {
        // Invalid age
        calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 17);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testFemale_InvalidAge_146() {
        // Exceeds maximum
        calculator.calculatePayment(PaymentCalculator.Gender.FEMALE, 146);
    }
    
    // ========== NULL AND INVALID GENDER TESTS ==========
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGender_Null() {
        // Null gender
        calculator.calculatePayment((PaymentCalculator.Gender) null, 25);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGender_String() {
        // Invalid string
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
    
    // ========== FAILING TESTS ==========
    
    // BUG: Senior discount not implemented
    @Test
    public void testSeniorDiscount_Male_Age70() {
        // Expected: 140 - 10% = 126
        assertEquals(126, calculator.calculatePayment(PaymentCalculator.Gender.MALE, 70));
    }
}
