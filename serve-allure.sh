#!/bin/bash
# =====================================================================
# This script relies entirely on the Maven Allure plugin.
# There is no more manual downloading of Allure CLI or hardcoded paths 
# to .allure/allure-3.4.1. Maven handles everything.
# =====================================================================

echo "===================================================="
echo "Running Tests..."
echo "===================================================="
mvn clean test

# =====================================================================
# OPTIONAL FIX: If the generated report crashes with 
# "Cannot read properties of undefined (reading 'message')" when 
# clicking a test case, it's due to empty statusDetails objects in JSON.
# Run this script with environment variable FIX_ALLURE_BUG=1 to patch it.
# Example: FIX_ALLURE_BUG=1 ./serve-allure.sh
# =====================================================================
if [ "$FIX_ALLURE_BUG" = "1" ]; then
    echo "===================================================="
    echo "Fixing Allure UI Crash Bug (Patching TestNG JSON)..."
    echo "===================================================="
    # Compatible with both GNU sed and macOS/BSD sed
    find allure-results -name "*-result.json" -type f -exec sed -E -i.bak 's/"statusDetails":\{"known":false,"muted":false,"flaky":false\},?//g' {} +
    find allure-results -name "*.bak" -type f -delete
fi

echo "===================================================="
echo "Generating Allure Report via Maven..."
echo "===================================================="
mvn allure:report

echo "===================================================="
echo "Starting Allure Local Server via Maven..."
echo "===================================================="
mvn allure:serve
