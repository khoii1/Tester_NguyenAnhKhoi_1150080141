@echo off
echo ========================================
echo   Organization Management System
echo   Compile and Run Script
echo ========================================
echo.

REM Set project directory
cd /d "%~dp0"

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Java is not installed or not in PATH!
    echo Please install JDK 17 or higher
    pause
    exit /b 1
)

echo [INFO] Compiling Java files...
echo.

REM Create output directory
if not exist "bin" mkdir bin

REM Compile all Java files
javac -d bin -cp "src\main\java" src\main\java\com\hcmunre\organization\model\*.java
if errorlevel 1 goto :error

javac -d bin -cp "bin;src\main\java" src\main\java\com\hcmunre\organization\util\*.java
if errorlevel 1 goto :error

javac -d bin -cp "bin;src\main\java" src\main\java\com\hcmunre\organization\repository\*.java
if errorlevel 1 goto :error

javac -d bin -cp "bin;src\main\java" src\main\java\com\hcmunre\organization\service\*.java
if errorlevel 1 goto :error

echo [SUCCESS] Compilation completed!
echo.
echo To run the application with JavaFX, please use Maven:
echo   mvn clean javafx:run
echo.
echo Or use your IDE to run OrganizationApp.java
echo.
pause
exit /b 0

:error
echo [ERROR] Compilation failed!
echo Please check the error messages above
pause
exit /b 1
