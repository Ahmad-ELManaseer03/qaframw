@echo off
echo ====================================================
echo Fixing Allure 3 UI Crash Bug (Patching TestNG JSON)...
echo ====================================================

powershell -Command "Get-ChildItem -Path allure-results -Filter '*-result.json' | ForEach-Object { (Get-Content $_.FullName) -replace '\"statusDetails\":\{\"known\":false,\"muted\":false,\"flaky\":false\},?', '' | Set-Content $_.FullName }"

echo ====================================================
echo Generating Allure 3 Report (Fresh generation)...
echo ====================================================

if exist allure-report (
    rmdir /S /Q allure-report
)
if exist allure-results\history (
    rmdir /S /Q allure-results\history
)

call .\.allure\allure-3.4.1\node_modules\.bin\allure.cmd generate allure-results -o allure-report

echo.
echo ====================================================
echo Starting Allure 3 Local Server...
echo ====================================================
call .\.allure\allure-3.4.1\node_modules\.bin\allure.cmd open allure-report
