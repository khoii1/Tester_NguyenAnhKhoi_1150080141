@echo off
echo ============================================
echo Setting up SQL Server Database for bai5
echo ============================================
echo.

sqlcmd -S DESKTOP-RJK03JE\SQL01 -E -i setup-database.sql

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ============================================
    echo Database setup completed successfully!
    echo ============================================
) else (
    echo.
    echo ============================================
    echo ERROR: Database setup failed!
    echo Please run this script as Administrator
    echo or run setup-database.sql in SSMS
    echo ============================================
)

pause
