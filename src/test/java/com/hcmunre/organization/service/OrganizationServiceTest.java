package com.hcmunre.organization.service;

import com.hcmunre.organization.model.Organization;
import com.hcmunre.organization.repository.OrganizationRepository;
import com.hcmunre.organization.service.OrganizationService.ValidationException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for OrganizationService
 * Contains minimum 15 test cases covering validation, business logic, and edge cases
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrganizationServiceTest {

    private OrganizationService service;
    
    @Mock
    private OrganizationRepository mockRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new OrganizationService(mockRepository);
    }

    // Test Case 1: Valid organization data
    @Test
    @Order(1)
    @DisplayName("TC01: Save organization with valid data successfully")
    void testSaveOrganization_ValidData_Success() throws ValidationException, SQLException {
        // Arrange
        Organization org = new Organization("Valid Organization Name", "123 Main St", "0123456789", "valid@example.com");
        Organization savedOrg = new Organization(1, "Valid Organization Name", "123 Main St", "0123456789", "valid@example.com", LocalDateTime.now());
        
        when(mockRepository.existsByName(anyString())).thenReturn(false);
        when(mockRepository.save(any(Organization.class))).thenReturn(savedOrg);
        
        // Act
        Organization result = service.saveOrganization(org);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.getOrgId());
        verify(mockRepository).existsByName("Valid Organization Name");
        verify(mockRepository).save(org);
    }

    // Test Case 2: Organization name is null
    @Test
    @Order(2)
    @DisplayName("TC02: Validate organization name - null should throw exception")
    void testValidateOrganizationName_Null_ThrowsException() {
        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            service.validateOrganizationName(null);
        });
        
        assertEquals("Organization Name is required", exception.getMessage());
    }

    // Test Case 3: Organization name is empty
    @Test
    @Order(3)
    @DisplayName("TC03: Validate organization name - empty should throw exception")
    void testValidateOrganizationName_Empty_ThrowsException() {
        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            service.validateOrganizationName("");
        });
        
        assertEquals("Organization Name is required", exception.getMessage());
    }

    // Test Case 4: Organization name is whitespace only
    @Test
    @Order(4)
    @DisplayName("TC04: Validate organization name - whitespace only should throw exception")
    void testValidateOrganizationName_WhitespaceOnly_ThrowsException() {
        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            service.validateOrganizationName("   ");
        });
        
        assertEquals("Organization Name is required", exception.getMessage());
    }

    // Test Case 5: Organization name length < 3 (boundary value)
    @Test
    @Order(5)
    @DisplayName("TC05: Validate organization name - length < 3 should throw exception")
    void testValidateOrganizationName_TooShort_ThrowsException() {
        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            service.validateOrganizationName("AB");
        });
        
        assertEquals("Organization Name must be at least 3 characters", exception.getMessage());
    }

    // Test Case 6: Organization name length = 3 (boundary value - valid)
    @Test
    @Order(6)
    @DisplayName("TC06: Validate organization name - length = 3 should be valid")
    void testValidateOrganizationName_ExactlyThreeCharacters_Valid() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            service.validateOrganizationName("ABC");
        });
    }

    // Test Case 7: Organization name length = 255 (boundary value - valid)
    @Test
    @Order(7)
    @DisplayName("TC07: Validate organization name - length = 255 should be valid")
    void testValidateOrganizationName_Exactly255Characters_Valid() {
        // Arrange
        String name = "A".repeat(255);
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            service.validateOrganizationName(name);
        });
    }

    // Test Case 8: Organization name length > 255 (boundary value)
    @Test
    @Order(8)
    @DisplayName("TC08: Validate organization name - length > 255 should throw exception")
    void testValidateOrganizationName_TooLong_ThrowsException() {
        // Arrange
        String name = "A".repeat(256);
        
        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            service.validateOrganizationName(name);
        });
        
        assertEquals("Organization Name must not exceed 255 characters", exception.getMessage());
    }

    // Test Case 9: Duplicate organization name
    @Test
    @Order(9)
    @DisplayName("TC09: Save organization - duplicate name should throw exception")
    void testSaveOrganization_DuplicateName_ThrowsException() throws SQLException {
        // Arrange
        Organization org = new Organization("Duplicate Org", "Address", "0123456789", "test@example.com");
        when(mockRepository.existsByName("Duplicate Org")).thenReturn(true);
        
        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            service.saveOrganization(org);
        });
        
        assertEquals("Organization Name already exists", exception.getMessage());
        verify(mockRepository).existsByName("Duplicate Org");
        verify(mockRepository, never()).save(any());
    }

    // Test Case 10: Email validation - invalid format
    @ParameterizedTest
    @Order(10)
    @ValueSource(strings = {"invalid", "invalid@", "@example.com", "invalid@.com", "invalid.com"})
    @DisplayName("TC10: Validate email - invalid format should throw exception")
    void testValidateEmail_InvalidFormat_ThrowsException(String invalidEmail) {
        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            service.validateEmail(invalidEmail);
        });
        
        assertEquals("Email format is invalid", exception.getMessage());
    }

    // Test Case 11: Email validation - valid format
    @ParameterizedTest
    @Order(11)
    @ValueSource(strings = {"test@example.com", "user.name@example.co.uk", "user+tag@example.com"})
    @DisplayName("TC11: Validate email - valid format should pass")
    void testValidateEmail_ValidFormat_Success(String validEmail) {
        // Act & Assert
        assertDoesNotThrow(() -> {
            service.validateEmail(validEmail);
        });
    }

    // Test Case 12: Phone validation - contains non-digit characters
    @Test
    @Order(12)
    @DisplayName("TC12: Validate phone - non-digit characters should throw exception")
    void testValidatePhone_NonDigit_ThrowsException() {
        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            service.validatePhone("012-345-6789");
        });
        
        assertEquals("Phone number must contain only digits", exception.getMessage());
    }

    // Test Case 13: Phone validation - length < 9 (boundary value)
    @Test
    @Order(13)
    @DisplayName("TC13: Validate phone - length < 9 should throw exception")
    void testValidatePhone_TooShort_ThrowsException() {
        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            service.validatePhone("12345678");
        });
        
        assertEquals("Phone number must be at least 9 digits", exception.getMessage());
    }

    // Test Case 14: Phone validation - length = 9 (boundary value - valid)
    @Test
    @Order(14)
    @DisplayName("TC14: Validate phone - length = 9 should be valid")
    void testValidatePhone_ExactlyNineDigits_Valid() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            service.validatePhone("123456789");
        });
    }

    // Test Case 15: Phone validation - length = 12 (boundary value - valid)
    @Test
    @Order(15)
    @DisplayName("TC15: Validate phone - length = 12 should be valid")
    void testValidatePhone_Exactly12Digits_Valid() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            service.validatePhone("123456789012");
        });
    }

    // Test Case 16: Phone validation - length > 12 (boundary value)
    @Test
    @Order(16)
    @DisplayName("TC16: Validate phone - length > 12 should throw exception")
    void testValidatePhone_TooLong_ThrowsException() {
        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            service.validatePhone("1234567890123");
        });
        
        assertEquals("Phone number must not exceed 12 digits", exception.getMessage());
    }

    // Test Case 17: Optional fields - null/empty values are allowed
    @Test
    @Order(17)
    @DisplayName("TC17: Save organization - optional fields can be null/empty")
    void testSaveOrganization_OptionalFieldsNullOrEmpty_Success() throws ValidationException, SQLException {
        // Arrange
        Organization org = new Organization("Required Name Only", null, null, null);
        Organization savedOrg = new Organization(1, "Required Name Only", null, null, null, LocalDateTime.now());
        
        when(mockRepository.existsByName(anyString())).thenReturn(false);
        when(mockRepository.save(any(Organization.class))).thenReturn(savedOrg);
        
        // Act
        Organization result = service.saveOrganization(org);
        
        // Assert
        assertNotNull(result);
        assertNull(result.getAddress());
        assertNull(result.getPhone());
        assertNull(result.getEmail());
    }

    // Test Case 18: Check organization name exists - returns true
    @Test
    @Order(18)
    @DisplayName("TC18: Check if organization name exists - should return true")
    void testIsOrganizationNameExists_True() throws SQLException {
        // Arrange
        when(mockRepository.existsByName("Existing Org")).thenReturn(true);
        
        // Act
        boolean exists = service.isOrganizationNameExists("Existing Org");
        
        // Assert
        assertTrue(exists);
        verify(mockRepository).existsByName("Existing Org");
    }

    // Test Case 19: Find organization by ID
    @Test
    @Order(19)
    @DisplayName("TC19: Find organization by ID - should return organization")
    void testFindOrganizationById_Found() throws SQLException {
        // Arrange
        Organization org = new Organization(1, "Test Org", "Address", "0123456789", "test@example.com", LocalDateTime.now());
        when(mockRepository.findById(1)).thenReturn(Optional.of(org));
        
        // Act
        Optional<Organization> result = service.findOrganizationById(1);
        
        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test Org", result.get().getOrgName());
        verify(mockRepository).findById(1);
    }

    // Test Case 20: Complete validation workflow
    @Test
    @Order(20)
    @DisplayName("TC20: Complete workflow - validate all fields together")
    void testValidateOrganization_AllFieldsValid_Success() throws ValidationException {
        // Arrange
        Organization org = new Organization("Valid Organization", "123 Street", "0123456789", "test@example.com");
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            service.validateOrganization(org);
        });
    }
}
