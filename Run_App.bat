@echo off
TITLE Travel Agency Application Launcher

REM This batch file launches the PowerShell setup and run script
REM which handles dependencies, compilation, and execution.

echo ===================================================
echo Launching Travel and Tourism Agency Management System
echo ===================================================

REM Check if PowerShell is available
where powershell >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo Error: PowerShell is not found in your PATH.
    echo Please install PowerShell or check your system configuration.
    pause
    exit /b 1
)

REM Execute the PowerShell script
REM -ExecutionPolicy Bypass allows the script to run even if scripts are disabled on the system
PowerShell -NoProfile -ExecutionPolicy Bypass -File "%~dp0setup_and_run.ps1"

REM Check exit code
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo The application exited with an error code: %ERRORLEVEL%
    pause
)
