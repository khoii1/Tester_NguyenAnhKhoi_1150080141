-- =============================================
-- Script to setup SQL Server for CustomerDB
-- Run this script in SQL Server Management Studio
-- =============================================

USE master;
GO

-- 1. Enable SQL Server Authentication (if not already enabled)
-- This needs to be done in SQL Server Configuration Manager
-- Server Properties > Security > SQL Server and Windows Authentication mode

-- 2. Create database if not exists
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'CustomerDB')
BEGIN
    CREATE DATABASE CustomerDB;
    PRINT 'Database CustomerDB created successfully';
END
ELSE
BEGIN
    PRINT 'Database CustomerDB already exists';
END
GO

-- 3. Create login if not exists
USE master;
GO

IF NOT EXISTS (SELECT name FROM sys.server_principals WHERE name = 'testuser')
BEGIN
    CREATE LOGIN testuser WITH PASSWORD = '123456';
    PRINT 'Login testuser created successfully';
END
ELSE
BEGIN
    PRINT 'Login testuser already exists';
END
GO

-- 4. Switch to CustomerDB and create user
USE CustomerDB;
GO

IF NOT EXISTS (SELECT name FROM sys.database_principals WHERE name = 'testuser')
BEGIN
    CREATE USER testuser FOR LOGIN testuser;
    PRINT 'User testuser created successfully';
END
ELSE
BEGIN
    PRINT 'User testuser already exists';
END
GO

-- 5. Grant permissions
ALTER ROLE db_owner ADD MEMBER testuser;
GO

PRINT 'Permissions granted successfully';
PRINT '';
PRINT '===============================================';
PRINT 'Setup completed successfully!';
PRINT 'You can now connect with:';
PRINT '  Server: DESKTOP-RJK03JE\SQL01';
PRINT '  Database: CustomerDB';
PRINT '  Username: testuser';
PRINT '  Password: 123456';
PRINT '===============================================';
GO
