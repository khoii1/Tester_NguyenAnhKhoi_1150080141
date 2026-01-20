package com.hcmunre.organization.repository;

import com.hcmunre.organization.model.Organization;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for OrganizationRepository
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrganizationRepositoryTest {

    private OrganizationRepository repository;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private Statement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        repository = new OrganizationRepository();
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockStatement = mock(Statement.class);
        mockResultSet = mock(ResultSet.class);
    }

    @Test
    @Order(1)
    @DisplayName("Test save organization successfully")
    void testSaveOrganization_Success() throws SQLException {
        // Arrange
        Organization org = new Organization("Test Organization", "Test Address", "0123456789", "test@example.com");
        
        try (MockedStatic<com.hcmunre.organization.util.DatabaseConnection> mockedDbConnection = 
                mockStatic(com.hcmunre.organization.util.DatabaseConnection.class)) {
            
            mockedDbConnection.when(() -> com.hcmunre.organization.util.DatabaseConnection.getConnection())
                    .thenReturn(mockConnection);
            
            when(mockConnection.prepareStatement(any(String.class), eq(Statement.RETURN_GENERATED_KEYS)))
                    .thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);
            when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true, true, false);
            when(mockResultSet.getInt(1)).thenReturn(1);
            
            // Mock for findById call
            when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.getInt("OrgID")).thenReturn(1);
            when(mockResultSet.getString("OrgName")).thenReturn("Test Organization");
            when(mockResultSet.getString("Address")).thenReturn("Test Address");
            when(mockResultSet.getString("Phone")).thenReturn("0123456789");
            when(mockResultSet.getString("Email")).thenReturn("test@example.com");
            when(mockResultSet.getTimestamp("CreatedDate")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            
            // Act
            Organization savedOrg = repository.save(org);
            
            // Assert
            assertNotNull(savedOrg);
            assertEquals(1, savedOrg.getOrgId());
            verify(mockPreparedStatement).setString(1, "Test Organization");
            verify(mockPreparedStatement).executeUpdate();
        }
    }

    @Test
    @Order(2)
    @DisplayName("Test find organization by ID - exists")
    void testFindById_Exists() throws SQLException {
        // Arrange
        try (MockedStatic<com.hcmunre.organization.util.DatabaseConnection> mockedDbConnection = 
                mockStatic(com.hcmunre.organization.util.DatabaseConnection.class)) {
            
            mockedDbConnection.when(() -> com.hcmunre.organization.util.DatabaseConnection.getConnection())
                    .thenReturn(mockConnection);
            
            when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true, false);
            when(mockResultSet.getInt("OrgID")).thenReturn(1);
            when(mockResultSet.getString("OrgName")).thenReturn("Test Organization");
            when(mockResultSet.getString("Address")).thenReturn("Test Address");
            when(mockResultSet.getString("Phone")).thenReturn("0123456789");
            when(mockResultSet.getString("Email")).thenReturn("test@example.com");
            when(mockResultSet.getTimestamp("CreatedDate")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            
            // Act
            Optional<Organization> result = repository.findById(1);
            
            // Assert
            assertTrue(result.isPresent());
            assertEquals(1, result.get().getOrgId());
            assertEquals("Test Organization", result.get().getOrgName());
        }
    }

    @Test
    @Order(3)
    @DisplayName("Test find organization by ID - not found")
    void testFindById_NotFound() throws SQLException {
        // Arrange
        try (MockedStatic<com.hcmunre.organization.util.DatabaseConnection> mockedDbConnection = 
                mockStatic(com.hcmunre.organization.util.DatabaseConnection.class)) {
            
            mockedDbConnection.when(() -> com.hcmunre.organization.util.DatabaseConnection.getConnection())
                    .thenReturn(mockConnection);
            
            when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false);
            
            // Act
            Optional<Organization> result = repository.findById(999);
            
            // Assert
            assertFalse(result.isPresent());
        }
    }

    @Test
    @Order(4)
    @DisplayName("Test find organization by name - exists")
    void testFindByName_Exists() throws SQLException {
        // Arrange
        try (MockedStatic<com.hcmunre.organization.util.DatabaseConnection> mockedDbConnection = 
                mockStatic(com.hcmunre.organization.util.DatabaseConnection.class)) {
            
            mockedDbConnection.when(() -> com.hcmunre.organization.util.DatabaseConnection.getConnection())
                    .thenReturn(mockConnection);
            
            when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true, false);
            when(mockResultSet.getInt("OrgID")).thenReturn(1);
            when(mockResultSet.getString("OrgName")).thenReturn("Test Organization");
            when(mockResultSet.getString("Address")).thenReturn("Test Address");
            when(mockResultSet.getString("Phone")).thenReturn("0123456789");
            when(mockResultSet.getString("Email")).thenReturn("test@example.com");
            when(mockResultSet.getTimestamp("CreatedDate")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            
            // Act
            Optional<Organization> result = repository.findByName("Test Organization");
            
            // Assert
            assertTrue(result.isPresent());
            assertEquals("Test Organization", result.get().getOrgName());
        }
    }

    @Test
    @Order(5)
    @DisplayName("Test exists by name - true")
    void testExistsByName_True() throws SQLException {
        // Arrange
        try (MockedStatic<com.hcmunre.organization.util.DatabaseConnection> mockedDbConnection = 
                mockStatic(com.hcmunre.organization.util.DatabaseConnection.class)) {
            
            mockedDbConnection.when(() -> com.hcmunre.organization.util.DatabaseConnection.getConnection())
                    .thenReturn(mockConnection);
            
            when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt(1)).thenReturn(1);
            
            // Act
            boolean exists = repository.existsByName("Test Organization");
            
            // Assert
            assertTrue(exists);
        }
    }

    @Test
    @Order(6)
    @DisplayName("Test exists by name - false")
    void testExistsByName_False() throws SQLException {
        // Arrange
        try (MockedStatic<com.hcmunre.organization.util.DatabaseConnection> mockedDbConnection = 
                mockStatic(com.hcmunre.organization.util.DatabaseConnection.class)) {
            
            mockedDbConnection.when(() -> com.hcmunre.organization.util.DatabaseConnection.getConnection())
                    .thenReturn(mockConnection);
            
            when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt(1)).thenReturn(0);
            
            // Act
            boolean exists = repository.existsByName("Non Existent Organization");
            
            // Assert
            assertFalse(exists);
        }
    }

    @Test
    @Order(7)
    @DisplayName("Test find all organizations")
    void testFindAll() throws SQLException {
        // Arrange
        try (MockedStatic<com.hcmunre.organization.util.DatabaseConnection> mockedDbConnection = 
                mockStatic(com.hcmunre.organization.util.DatabaseConnection.class)) {
            
            mockedDbConnection.when(() -> com.hcmunre.organization.util.DatabaseConnection.getConnection())
                    .thenReturn(mockConnection);
            
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(any(String.class))).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true, true, false);
            when(mockResultSet.getInt("OrgID")).thenReturn(1, 2);
            when(mockResultSet.getString("OrgName")).thenReturn("Org1", "Org2");
            when(mockResultSet.getString("Address")).thenReturn("Address1", "Address2");
            when(mockResultSet.getString("Phone")).thenReturn("0123456789", "0987654321");
            when(mockResultSet.getString("Email")).thenReturn("org1@test.com", "org2@test.com");
            when(mockResultSet.getTimestamp("CreatedDate")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            
            // Act
            List<Organization> organizations = repository.findAll();
            
            // Assert
            assertNotNull(organizations);
            assertEquals(2, organizations.size());
        }
    }
}
