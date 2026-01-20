-- =============================================
-- Organization Management System - Database Script
-- =============================================

-- Create Database
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'OrganizationDB')
BEGIN
    CREATE DATABASE OrganizationDB;
END
GO

USE OrganizationDB;
GO

-- Create ORGANIZATION Table
IF OBJECT_ID('ORGANIZATION', 'U') IS NOT NULL
    DROP TABLE ORGANIZATION;
GO

CREATE TABLE ORGANIZATION (
    OrgID INT IDENTITY(1,1) PRIMARY KEY,
    OrgName VARCHAR(255) NOT NULL,
    Address VARCHAR(255),
    Phone VARCHAR(20),
    Email VARCHAR(100),
    CreatedDate DATETIME NOT NULL DEFAULT GETDATE(),
    
    -- Unique constraint with case-insensitive comparison
    CONSTRAINT UQ_OrgName UNIQUE (OrgName)
);
GO

-- Create case-insensitive collation index for OrgName
CREATE UNIQUE INDEX IX_OrgName_CaseInsensitive 
ON ORGANIZATION(OrgName) 
WHERE OrgName IS NOT NULL;
GO

-- Add check constraints
ALTER TABLE ORGANIZATION
ADD CONSTRAINT CHK_OrgName_Length CHECK (LEN(OrgName) >= 3 AND LEN(OrgName) <= 255);
GO

ALTER TABLE ORGANIZATION
ADD CONSTRAINT CHK_Phone_Length CHECK (Phone IS NULL OR (LEN(Phone) >= 9 AND LEN(Phone) <= 12));
GO

-- Sample data for testing (optional)
-- INSERT INTO ORGANIZATION (OrgName, Address, Phone, Email)
-- VALUES ('HCMC University', '227 Nguyen Van Cu, District 5', '0283836346', 'contact@hcmunre.edu.vn');
-- GO

-- Verify table structure
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    CHARACTER_MAXIMUM_LENGTH,
    IS_NULLABLE,
    COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'ORGANIZATION'
ORDER BY ORDINAL_POSITION;
GO

PRINT 'Database and ORGANIZATION table created successfully!';
