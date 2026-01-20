package com.hcmunre.organization.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.hcmunre.organization.model.Organization;
import com.hcmunre.organization.repository.OrganizationRepository;

/**
 * Service class for Organization business logic
 * Handles validation and business rules
 */
public class OrganizationService {
    private final OrganizationRepository repository;
    
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    // Phone validation pattern (only digits)
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]+$");

    public OrganizationService() {
        this.repository = new OrganizationRepository();
    }

    public OrganizationService(OrganizationRepository repository) {
        this.repository = repository;
    }

    /**
     * Validate and save organization
     * @param organization Organization to save
     * @return Saved organization
     * @throws ValidationException if validation fails
     * @throws SQLException if database operation fails
     */
    public Organization saveOrganization(Organization organization) throws ValidationException, SQLException {
        // Validate all fields
        validateOrganization(organization);
        
        // Check for duplicate name
        if (repository.existsByName(organization.getOrgName())) {
            throw new ValidationException("Organization Name already exists");
        }
        
        // Save to database
        return repository.save(organization);
    }

    /**
     * Validate organization data
     * @param organization Organization to validate
     * @throws ValidationException if validation fails
     */
    public void validateOrganization(Organization organization) throws ValidationException {
        validateOrganizationName(organization.getOrgName());
        validateEmail(organization.getEmail());
        validatePhone(organization.getPhone());
    }

    /**
     * Validate organization name
     * @param orgName Organization name
     * @throws ValidationException if validation fails
     */
    public void validateOrganizationName(String orgName) throws ValidationException {
        if (orgName == null || orgName.trim().isEmpty()) {
            throw new ValidationException("Organization Name is required");
        }
        
        String trimmedName = orgName.trim();
        if (trimmedName.length() < 3) {
            throw new ValidationException("Organization Name must be at least 3 characters");
        }
        
        if (trimmedName.length() > 255) {
            throw new ValidationException("Organization Name must not exceed 255 characters");
        }
    }

    /**
     * Validate email format
     * @param email Email address
     * @throws ValidationException if validation fails
     */
    public void validateEmail(String email) throws ValidationException {
        if (email != null && !email.trim().isEmpty()) {
            if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
                throw new ValidationException("Email format is invalid");
            }
        }
    }

    /**
     * Validate phone number
     * @param phone Phone number
     * @throws ValidationException if validation fails
     */
    public void validatePhone(String phone) throws ValidationException {
        if (phone != null && !phone.trim().isEmpty()) {
            String trimmedPhone = phone.trim();
            
            if (!PHONE_PATTERN.matcher(trimmedPhone).matches()) {
                throw new ValidationException("Phone number must contain only digits");
            }
            
            if (trimmedPhone.length() < 9) {
                throw new ValidationException("Phone number must be at least 9 digits");
            }
            
            if (trimmedPhone.length() > 12) {
                throw new ValidationException("Phone number must not exceed 12 digits");
            }
        }
    }

    /**
     * Check if organization name already exists
     * @param orgName Organization name
     * @return true if exists
     * @throws SQLException if database operation fails
     */
    public boolean isOrganizationNameExists(String orgName) throws SQLException {
        return repository.existsByName(orgName);
    }

    /**
     * Find organization by ID
     * @param id Organization ID
     * @return Optional containing organization if found
     * @throws SQLException if database operation fails
     */
    public Optional<Organization> findOrganizationById(Integer id) throws SQLException {
        return repository.findById(id);
    }

    /**
     * Find organization by name
     * @param orgName Organization name
     * @return Optional containing organization if found
     * @throws SQLException if database operation fails
     */
    public Optional<Organization> findOrganizationByName(String orgName) throws SQLException {
        return repository.findByName(orgName);
    }

    /**
     * Get all organizations
     * @return List of all organizations
     * @throws SQLException if database operation fails
     */
    public List<Organization> getAllOrganizations() throws SQLException {
        return repository.findAll();
    }

    /**
     * Update organization
     * @param organization Organization to update
     * @return Updated organization
     * @throws ValidationException if validation fails
     * @throws SQLException if database operation fails
     */
    public Organization updateOrganization(Organization organization) throws ValidationException, SQLException {
        if (organization.getOrgId() == null) {
            throw new ValidationException("Organization ID is required for update");
        }
        
        validateOrganization(organization);
        
        // Check if name is changed and if new name already exists
        Optional<Organization> existing = repository.findById(organization.getOrgId());
        if (existing.isPresent()) {
            if (!existing.get().getOrgName().equalsIgnoreCase(organization.getOrgName())) {
                if (repository.existsByName(organization.getOrgName())) {
                    throw new ValidationException("Organization Name already exists");
                }
            }
        }
        
        return repository.update(organization);
    }

    /**
     * Delete organization by ID
     * @param id Organization ID
     * @return true if deleted successfully
     * @throws SQLException if database operation fails
     */
    public boolean deleteOrganization(Integer id) throws SQLException {
        return repository.deleteById(id);
    }

    /**
     * Custom exception for validation errors
     */
    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}
