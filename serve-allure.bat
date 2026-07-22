@echo off
:: =====================================================================
:: This script relies entirely on the Maven Allure plugin.
:: There is no more manual downloading of Allure CLI or hardcoded paths 
:: to .allure/allure-3.4.1. Maven handles everything.
:: =====================================================================

echo ====================================================
echo Running Tests...
echo ====================================================
call mvn clean test

:: =====================================================================
:: OPTIONAL FIX: If the generated report crashes with 
:: "Cannot read properties of undefined (reading 'message')" when 
:: clicking a test case, it's due to empty statusDetails objects in JSON.
:: Run this script with environment variable FIX_ALLURE_BUG=1 to patch it.
:: Example: set FIX_ALLURE_BUG=1 ^&^& serve-allure.bat
:: =====================================================================
if "%FIX_ALLURE_BUG%"=="1" (
    echo ====================================================
    echo Fixing Allure UI Crash Bug (Patching TestNG JSON)...
    echo ====================================================
    powershell -Command "Get-ChildItem -Path allure-results -Filter '*-result.json' | ForEach-Object { (Get-Content $_.FullName) -replace '\"statusDetails\":\{\"known\":false,\"muted\":false,\"flaky\":false\},?', '' | Set-Content $_.FullName }"
)

echo ====================================================
echo Generating Allure Report via Maven...
echo ====================================================
call mvn allure:report

echo ====================================================
echo Starting Allure Local Server via Maven...
echo ====================================================
call mvn allure:serve
